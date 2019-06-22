package andrej.com.musicmanagement.base;

import andrej.com.musicmanagement.ScreenPresenter;
import andrej.com.musicmanagement.ScreenView;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseScreenPresenter<T extends ScreenView> implements ScreenPresenter<T> {

    private T mView;
    protected final CompositeDisposable viewSubscriptions = new CompositeDisposable();
    protected final CompositeDisposable screenSubscriptions = new CompositeDisposable();

    @Override
    public final void takeView(T view) {
        mView = view;
        onViewAttached();
    }

    @Override
    public final void dropView() {
        mView = null;
        viewSubscriptions.clear();
        onViewDetached();
    }

    @Override
    public void destroyScreen() {
        screenSubscriptions.clear();
        onScreenDestroyed();
    }

    protected final T getView() {
        return mView;
    }

    protected final boolean isViewAttached() {
        return mView != null;
    }

    protected abstract void onViewDetached();

    protected abstract void onViewAttached();

    protected abstract void onScreenDestroyed();
}