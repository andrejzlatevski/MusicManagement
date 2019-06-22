package andrej.com.musicmanagement.main;

import andrej.com.musicmanagement.base.BaseFragment;

/**
 * This is a class that is dedicated in fix the FragmentTransaction problem limited by calling commit()
 * after onSaveInstanceState().
 */
public abstract class DeferredScreenTransaction {

    private final BaseFragment fragment;

    protected DeferredScreenTransaction(BaseFragment fragment) {
        this.fragment = fragment;
    }

    public final BaseFragment getScreen() {
        return fragment;
    }

    public abstract void commit();
}
