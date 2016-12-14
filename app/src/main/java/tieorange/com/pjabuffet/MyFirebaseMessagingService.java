package tieorange.com.pjabuffet;

import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by tieorange on 14/12/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

  public static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

  @Override public void onMessageReceived(RemoteMessage remoteMessage) {
    Log.d(TAG, "onMessageReceived() called with: remoteMessage = [" + remoteMessage + "]");

    // Check data payload
    if (remoteMessage.getData().size() > 0) {
      Log.d(TAG, "onMessageReceived: " + remoteMessage.getData());
    }

    // Check notification payload
    if (remoteMessage.getNotification() != null) {
      Log.d(TAG, "onMessageReceived: " + remoteMessage.getNotification().getBody());
    }

    showNotification(remoteMessage);
  }

  private void showNotification(RemoteMessage message) {

  }
}
