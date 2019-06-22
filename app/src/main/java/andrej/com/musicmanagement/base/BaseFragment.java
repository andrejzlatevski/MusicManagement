package andrej.com.musicmanagement.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import andrej.com.musicmanagement.BackInterceptor;
import andrej.com.musicmanagement.Constants;
import andrej.com.musicmanagement.MusicManagementApplication;
import andrej.com.musicmanagement.R;
import andrej.com.musicmanagement.ScreenPresenter;
import andrej.com.musicmanagement.ScreenView;
import dagger.android.support.DaggerFragment;

public abstract class BaseFragment<T extends ScreenPresenter> extends DaggerFragment implements BackInterceptor, ScreenView<T> {

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        T presenter = providePresenter();
        if (presenter != null) {
            presenter.takeView(this);
        }
        return bindView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        T presenter = providePresenter();
        if (presenter != null) {
            presenter.dropView();
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        T presenter = providePresenter();
        if (presenter != null) {
            presenter.destroyScreen();
        }
        super.onDestroy();
    }

    @Override
    public void showToastMessage(String message) {
        if((BaseActivity) getActivity() !=null){
            ((BaseActivity) getActivity()).showToastMessage(message);
        }
    }

    @Override
    public void showToastMessage(int message) {
        if((BaseActivity)getActivity() !=null){
            ((BaseActivity) getActivity()).showToastMessage(message);
        }
    }

    @Override
    public void showMessage(int message) {
        showMessage(getString(message));
    }

    @Override
    public void showMessage(int message, int bgColor) {
        showMessage(getString(message), bgColor);
    }

    @Override
    public void showMessage(String message) {
        makeSnackBar(message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(String message, @ColorRes int bgColor) {
        makeSnackBar(message, Snackbar.LENGTH_LONG, bgColor).show();
    }

    @Override
    public void showProgressDialog(String title, String message) {
        if((BaseActivity)getActivity() !=null){
            ((BaseActivity) getActivity()).showProgressDialog(title, message);
        }
    }

    @Override
    public void showProgressDialog(@StringRes int titleRes, @StringRes int messageRes) {
        if((BaseActivity)getActivity() !=null){
            ((BaseActivity) getActivity()).showProgressDialog(titleRes, messageRes);
        }
    }

    @Override
    public void hideProgressDialog() {
        if((BaseActivity)getActivity() !=null){
            ((BaseActivity) getActivity()).hideProgressDialog();
        }
    }

    public boolean hasSharedElement(String transitionName) {
        return false;
    }

    @Nullable
    public View getSharedElement() {
        return null;
    }

    @AnimRes
    public int getAppearanceAnimation() {
        return R.anim.slide_in_right;
    }

    @AnimRes
    public int getDisappearanceAnimation() {
        return R.anim.slide_out_left;
    }

    @AnimRes
    public int getPopAppearanceAnimation() {
        return R.anim.slide_in_left;
    }

    @AnimRes
    public int getPopDisappearanceAnimation() {
        return R.anim.slide_out_right;
    }

    @Override
    public boolean handleBackPress() {
        return false;
    }

    protected void composeResult(Intent resultIntent, int resultCode) {
        Fragment targetFragment = getTargetFragment();
        if (targetFragment != null) {
            targetFragment.onActivityResult(getTargetRequestCode(), resultCode, resultIntent);
        }
    }

    protected Snackbar makeSnackBar(@StringRes int message, int duration) {
        return makeSnackBar(getString(message), duration);
    }

    protected Snackbar makeSnackBar(@NonNull String message, int duration) {
        return makeSnackBar(message, duration, Constants.EMPTY_INT_VALUE);
    }

    protected Snackbar makeSnackBar(@NonNull String message, int duration, int bgColor) {
        Snackbar snackbar = Snackbar.make(getView(), message, duration);
        View snackbarView = snackbar.getView();
        if (bgColor != Constants.EMPTY_INT_VALUE) {
            snackbarView.setBackgroundColor(ContextCompat.getColor(MusicManagementApplication.getInstance(), bgColor));
        }
        TextView textView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(5);
        return snackbar;
    }

    protected Snackbar makeRootSnackBar(@StringRes int message, int duration) {
        return makeRootSnackBar(getString(message), duration);
    }

    protected Snackbar makeRootSnackBar(@NonNull String message, int duration) {
        return makeRootSnackBar(message, duration, Constants.EMPTY_INT_VALUE);
    }

    protected Snackbar makeRootSnackBar(@NonNull String message, int duration, int bgColor) {
        return ((BaseActivity) getActivity()).makeSnackBar(message, duration, bgColor);
    }

    public abstract String getScreenTag();

    @Nullable
    protected abstract T providePresenter();

    @NonNull
    protected abstract View bindView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);
}

