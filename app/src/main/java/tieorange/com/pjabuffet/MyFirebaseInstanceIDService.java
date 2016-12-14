package tieorange.com.pjabuffet;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by tieorange on 14/12/2016.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

  public static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

  @Override public void onTokenRefresh() {
    final String token = FirebaseInstanceId.getInstance().getToken();
    Log.d(TAG, "onTokenRefresh: " + token);
    sendRegistrationToServer(token);
  }

  private void sendRegistrationToServer(String token) {
    // TODO: 14/12/2016
  }
}
