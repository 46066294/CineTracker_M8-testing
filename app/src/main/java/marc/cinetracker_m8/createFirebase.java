package marc.cinetracker_m8;

import android.app.Application;
import com.firebase.client.Firebase;


/**
 * Created by 46066294p on 24/02/16.
 */
public class createFirebase extends Application {

    private Firebase ref;

    public Firebase getRef() {
        return ref;
    }

    public void setRef(Firebase ref) {
        this.ref = ref;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        // other setup code
        ref = new Firebase("https://cinetracker.firebaseio.com");

        /*ref.authWithPassword("Mim@firebase.com", "Mim", new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
            }
            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // there was an error
            }
        });*/

    }
}
