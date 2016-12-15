package tieorange.com.pjabuffet.services;

import android.util.Log;
import com.google.firebase.messaging.RemoteMessage;
import tieorange.com.pjabuffet.utils.NotificationHandler;

import static java.lang.Integer.parseInt;

/**
 * Created by tieorange on 14/12/2016.
 */

public class FirebaseMessagingService
    extends com.google.firebase.messaging.FirebaseMessagingService {

  public static final String TAG = FirebaseInstanceIDService.class.getSimpleName();

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

    NotificationHandler.showNotification(this, remoteMessage.getNotification());
  }
}
