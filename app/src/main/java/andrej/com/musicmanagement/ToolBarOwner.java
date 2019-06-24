package andrej.com.musicmanagement;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

public interface ToolBarOwner {

    void setUpIcon(@DrawableRes int icon);

    void hideToolBar();

    void showToolBar();

    void showBackButton();

    void hideBackButton();

    void showTitle(String title);

    void showTitle(@StringRes int title);
}