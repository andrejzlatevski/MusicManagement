package andrej.com.musicmanagement.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import andrej.com.musicmanagement.Conductor;
import andrej.com.musicmanagement.Constants;
import andrej.com.musicmanagement.R;
import andrej.com.musicmanagement.ToolBarOwner;
import andrej.com.musicmanagement.base.BaseActivity;
import andrej.com.musicmanagement.base.BaseFragment;
import andrej.com.musicmanagement.databinding.ActivityMainBinding;


public class MainActivity extends BaseActivity implements Conductor<BaseFragment>, ToolBarOwner {


    private ActivityMainBinding mBindingObject;
    private BaseFragment mCurrFragment;
    private Queue<DeferredScreenTransaction> mDeferredScreensQueue = new ArrayDeque<>();
    private boolean isPaused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBindingObject = DataBindingUtil.setContentView(this, R.layout.activity_main);
        goTo(MainFragment.createInstance());
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPaused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPaused = false;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public void goToWithResult(BaseFragment targetFragment, Intent result, int requestCode, int resultCode) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment instanceof BaseFragment &&
                        ((BaseFragment) fragment).getScreenTag().equals(targetFragment.getScreenTag())) {
                    break;
                }
                fragmentManager.popBackStack();
            }
        }
        if (targetFragment != null) {
            targetFragment.onActivityResult(requestCode, resultCode, result);
        }
    }

    @Override
    protected void showToastMessage(int textRes) {
        mBindingObject.toastMessage.setTextColor(getResources().getColor(R.color.blueColor));
        showToastMessage(getString(textRes));
    }

    @Override
    protected void showToastMessage(int textRes, int colorRes) {
        mBindingObject.toastMessage.setTextColor(getResources().getColor(colorRes));
        showToastMessage(getString(textRes));
    }

    @Override
    protected void showToastMessage(String text) {
        mBindingObject.toastMessage.setVisibility(View.VISIBLE);
        mBindingObject.toastMessage.setText(text);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing() && !isDestroyed()) {
                    mBindingObject.toastMessage.setVisibility(View.GONE);
                    mBindingObject.toastMessage.setText("");
                }
            }
        }, 3000);
    }

    @Override
    public void goBackWithResult(BaseFragment targetFragment, Intent result, int requestCode, int resultCode) {
        if (targetFragment != null) {
            targetFragment.onActivityResult(requestCode, resultCode, result);
        }
        goBack();
    }

    @Override
    public void goTo(BaseFragment fragment) {
        if (isPaused) {
            mDeferredScreensQueue.add(new DeferredScreenTransaction(fragment) {
                @Override
                public void commit() {
                    goTo(getScreen());
                }
            });
            return;
        }
        int appearanceAnimation = fragment.getAppearanceAnimation();
        int disappearanceAnimation = fragment.getDisappearanceAnimation();
        int popAppearanceAnimation = fragment.getPopAppearanceAnimation();
        int popDisappearanceAnimation = fragment.getPopDisappearanceAnimation();
        if (mCurrFragment == null && !(fragment instanceof MainFragment)) {
            appearanceAnimation = R.anim.animation_appear_from_nothing_tiny;
        } else {
            setupToolBar();
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        boolean isRootFragment = fragment instanceof MainFragment;
        if (isRootFragment) {
            //Clean back stack if any
            getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }else {
            fragmentTransaction.setCustomAnimations(appearanceAnimation, disappearanceAnimation,
                    popAppearanceAnimation, popDisappearanceAnimation);
            fragmentTransaction.addToBackStack(fragment.getScreenTag());
        }
        fragmentTransaction.replace(R.id.container, mCurrFragment = fragment, fragment.getScreenTag());
        fragmentTransaction.commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return (true);
        }

        return (super.onOptionsItemSelected(item));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (mCurrFragment != null && mCurrFragment.handleBackPress()) {
            return;
        }
        int prevIndex = 0;
        prevIndex = getSupportFragmentManager().getBackStackEntryCount();
        super.onBackPressed();
        if (prevIndex == 0) {
            return;
        }
        mCurrFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(mBindingObject.container.getId());
        updateBackButtonState(prevIndex > 1);
    }

    @Override
    public void showBackButton() {
        mBindingObject.toolbar.setNavigationIcon(R.drawable.ic_action_back);
    }

    @Override
    public void hideToolBar() {
        mBindingObject.toolbar.setVisibility(View.GONE);
    }

    @Override
    public void setUpIcon(int icon) {
        mBindingObject.toolbar.setNavigationIcon(icon);
    }

    @Override
    public void showToolBar() {
        mBindingObject.toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBackButton() {
        mBindingObject.toolbar.setNavigationIcon(null);
    }

    @Override
    public void showTitle(String title) {
        mBindingObject.toolbarTitle.setText(title);
    }

    @Override
    public void showTitle(@StringRes int title) {
        mBindingObject.toolbarTitle.setText(title);
    }

    @Override
    protected View getRootView() {
        return getWindow().getDecorView().findViewById(R.id.main_root);
    }

    private void updateBackButtonState(boolean show) {
        if (show) {
            showBackButton();
        } else {
            //We need to store the default navigation icon provided by the side menu library for father usages
            mBindingObject.toolbar.setNavigationIcon(null);
        }
    }

    private void setupToolBar() {
        mBindingObject.appBar.setVisibility(View.VISIBLE);
        setSupportActionBar(mBindingObject.toolbar);
        getSupportActionBar().setTitle("");
    }

}
