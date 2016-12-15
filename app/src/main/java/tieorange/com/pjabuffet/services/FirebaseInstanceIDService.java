package tieorange.com.pjabuffet.services;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import tieorange.com.pjabuffet.utils.Tools;

/**
 * Created by tieorange on 14/12/2016.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

  public static final String TAG = FirebaseInstanceIDService.class.getSimpleName();

  @Override public void onTokenRefresh() {
    final String token = FirebaseInstanceId.getInstance().getToken();
    Log.d(TAG, "onTokenRefresh: " + token);
    Tools.changeUserToken(token);
  }
}
