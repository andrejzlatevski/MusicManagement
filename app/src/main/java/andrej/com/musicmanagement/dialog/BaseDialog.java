package andrej.com.musicmanagement.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import andrej.com.musicmanagement.R;

public abstract class BaseDialog extends DialogFragment {
    protected static final String REQUEST_CODE = "request_code";

    @Override
    public void show(FragmentManager manager, String tag) {

        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commitAllowingStateLoss();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public final void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.ThemeDialog);
    }

    protected final void composeResult(int requestCode, int resultCode) {
        composeResult(requestCode, resultCode, null);
    }

    protected final void composeResult(int requestCode, int resultCode, Intent intent) {
        if (getTargetFragment() != null) {
            if (getTargetFragment() instanceof BaseDialog.DialogListener) {
                ((BaseDialog.DialogListener) getTargetFragment()).onDialogResult(getTargetRequestCode(), resultCode, null);
            } else {
                getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, null);
            }
        } else {
            if (getActivity() instanceof DialogListener) {
                ((DialogListener) getActivity()).onDialogResult(requestCode, resultCode, intent);
            }
        }
        dismiss();
    }

    public interface DialogListener {

        void onDialogResult(int requestCode, int resultCode, Intent intent);
    }
}

