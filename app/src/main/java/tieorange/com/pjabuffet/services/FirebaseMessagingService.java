package tieorange.com.pjabuffet.services;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.firebase.messaging.RemoteMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import tieorange.com.pjabuffet.R;

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

    showNotification(remoteMessage.getNotification());
  }

  private void showNotification(RemoteMessage.Notification notification) {
    Context context = getBaseContext();
    NotificationCompat.Builder mBuilder =
        new NotificationCompat.Builder(context).setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(notification.getTitle())
            .setContentText(notification.getBody());
    NotificationManager mNotificationManager =
        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    mNotificationManager.notify(createID(), mBuilder.build());
  }

  public int createID() {
    Date now = new Date();
    return parseInt(new SimpleDateFormat("ddHHmmss", Locale.US).format(now));
  }
}
