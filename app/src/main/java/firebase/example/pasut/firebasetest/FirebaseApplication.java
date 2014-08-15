package firebase.example.pasut.firebasetest;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by marcelo on 8/15/14.
 */
public class FirebaseApplication extends Application {
  private final Firebase firebase = new Firebase(BuildConfig.FIREBASE_URL);

  public Firebase getDatabase() {
    return firebase;
  }
}
