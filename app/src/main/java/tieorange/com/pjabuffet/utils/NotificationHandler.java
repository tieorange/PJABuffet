package tieorange.com.pjabuffet.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.google.firebase.messaging.RemoteMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import tieorange.com.pjabuffet.R;
import tieorange.com.pjabuffet.activities.Henson;
import tieorange.com.pjabuffet.pojo.api.Order;

import static android.app.PendingIntent.FLAG_ONE_SHOT;
import static java.lang.Integer.parseInt;

/**
 * Created by tieorange on 15/12/2016.
 */

public class NotificationHandler {
  public static final String NOTIFICATION = "Notification";

  public static void showNotification(Context context, RemoteMessage.Notification notification) {
    // TODO: 15/12/2016  get Order object from backend
    Order order = OrderTools.getCurrentOrder();
    Intent resultIntent = Henson.with(context).gotoPaymentActivity().mOrder(order).build();
    resultIntent.setAction(NOTIFICATION);

    PendingIntent pendingIntent =
        PendingIntent.getActivity(context, 0, resultIntent, FLAG_ONE_SHOT);
    NotificationCompat.Builder mBuilder =
        new NotificationCompat.Builder(context).setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(notification.getTitle())
            .setContentIntent(pendingIntent)
            .setContentText(notification.getBody());
    NotificationManager notificationManager =
        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

    notificationManager.notify(generateNotificationID(), mBuilder.build());
  }

  private static int generateNotificationID() {
    Date now = new Date();
    return parseInt(new SimpleDateFormat("ddHHmmss", Locale.US).format(now));
  }
}
