package andrej.com.musicmanagement.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ProgressDialog extends DialogFragment {
    public static final String TAG = "ProgressDialog";

    private static final String PROVIDED_TITLE = "provided_title";
    private static final String PROVIDED_MESSAGE = "provided_message";
    private static final String PROVIDED_MODE = "provided_mode";

    public static ProgressDialog newInstance(String title, String message) {
        ProgressDialog dialogFragment = new ProgressDialog();
        Bundle bundle = new Bundle();
        bundle.putString(PROVIDED_TITLE, title);
        bundle.putString(PROVIDED_MESSAGE, message);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isModal = getArguments().getBoolean(PROVIDED_MODE, false);
        setCancelable(!isModal);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        android.app.ProgressDialog dialog = new android.app.ProgressDialog(getActivity(), getTheme());
        dialog.setTitle(getArguments().getString(PROVIDED_TITLE));
        dialog.setMessage(getArguments().getString(PROVIDED_MESSAGE));
        dialog.setIndeterminate(true);
        dialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        return dialog;
    }

    public ProgressDialog setModal(boolean value) {
        getArguments().putBoolean(PROVIDED_MODE, value);
        return this;
    }
}
