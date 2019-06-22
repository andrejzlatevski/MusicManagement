package andrej.com.musicmanagement;

public interface ScreenPresenter<T extends ScreenView> {
    /**
     * Binds presenter with a view when resumed. The Presenter will perform initialization here.
     *
     * @param view the view associated with this presenter
     */
    void takeView(T view);

    /**
     * Drops the reference to the view when destroyed
     */
    void dropView();

    /**
     * Release all screen resources
     */
    void destroyScreen();
}
