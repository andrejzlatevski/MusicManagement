package andrej.com.musicmanagement;

import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

public interface ScreenView<T extends ScreenPresenter> {

    void showToastMessage(String message);

    void showToastMessage(@StringRes int message);

    /**
     * Show message using Snackbar
     *
     * @param message
     */
    void showMessage(String message);

    void showMessage(@StringRes int message);

    void showMessage(@StringRes int message, @ColorRes int bgColor);

    void showMessage(String message, @ColorRes int bgColor);

    void showProgressDialog(@StringRes int titleRes, @StringRes int messageRes);

    void showProgressDialog(String title, String message);

    void hideProgressDialog();
}
