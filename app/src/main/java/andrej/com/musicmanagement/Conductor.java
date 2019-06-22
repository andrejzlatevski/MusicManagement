package andrej.com.musicmanagement;

import android.content.Intent;

public interface Conductor<T> {

    void goTo(T t);

    void goToWithResult(T t, Intent result, int requestCode, int resultCode);

    void goBack();

    void goBackWithResult(T t, Intent result, int requestCode, int resultCode);
}
