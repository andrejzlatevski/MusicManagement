package andrej.com.musicmanagement.base;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.Snackbar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import andrej.com.musicmanagement.Constants;
import andrej.com.musicmanagement.dialog.ProgressDialog;
import andrej.com.musicmanagement.utils.UiUtil;
import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public boolean checkForPermissions(int request, String... permissionsList) {
        if (Build.VERSION.SDK_INT >= 23) {
            //Check Location service permissions and request if needed
            List<String> permissions = new ArrayList<>();
            for (String permission : permissionsList) {
                if (ActivityCompat.checkSelfPermission(this, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    permissions.add(permission);
                }
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(this, permissions.toArray(new String[permissions.size()]),
                        request);
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments == null)
            return;
        for (Fragment fragment : fragments) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
        for (Integer result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                onPermissionsDenied(requestCode);
                return;
            }
        }
        onPermissionsGranted(requestCode);
    }

    public Snackbar makeSnackBar(@StringRes int message, int duration) {
        return makeSnackBar(message, duration, Constants.EMPTY_INT_VALUE);
    }

    public Snackbar makeSnackBar(@StringRes int message, int duration, int bgColor) {
        return makeSnackBar(getString(message), duration, bgColor);
    }

    protected Snackbar makeSnackBar(@NonNull String message, int duration) {
        return makeSnackBar(message, duration, Constants.EMPTY_INT_VALUE);
    }

    protected Snackbar makeSnackBar(@NonNull String message, int duration, int bgColor) {
        Snackbar snackbar = Snackbar.make(getRootView(), message, duration);
        View snackbarView = snackbar.getView();
        if (bgColor != Constants.EMPTY_INT_VALUE) {
            snackbarView.setBackgroundColor(ContextCompat.getColor(this, bgColor));
        }
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(5);
        return snackbar;
    }

    @Override
    protected void onPause() {
        super.onPause();
        UiUtil.hideKeyboard(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void showProgressDialog(@StringRes int titleRes, @StringRes int messageRes) {
        showProgressDialog(getString(titleRes), getString(messageRes));
    }

    protected void showProgressDialog(String title, String message) {
        showProgressDialog(title, message, true);
    }

    protected void showProgressDialog(String title, String message, boolean modal) {
        ProgressDialog.newInstance(title, message)
                .setModal(modal).show(getSupportFragmentManager(), ProgressDialog.TAG);
    }

    protected void hideProgressDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            Fragment prev = fragmentManager.findFragmentByTag(ProgressDialog.TAG);
            if (prev != null) {
                ProgressDialog df = (ProgressDialog) prev;
                df.dismissAllowingStateLoss();
            }
        }
    }

    protected void onPermissionsGranted(int request) {

    }

    protected void onPermissionsDenied(int request) {

    }

    protected abstract void showToastMessage(@StringRes int textRes);

    protected abstract void showToastMessage(@StringRes int textRes, @ColorRes int colorRes);

    protected abstract void showToastMessage(String text);

    protected abstract View getRootView();
}