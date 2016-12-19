package tieorange.com.pjabuffet.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import tieorange.com.pjabuffet.R;
import tieorange.com.pjabuffet.activities.Henson;
import tieorange.com.pjabuffet.pojo.PushNotificationBuffet;
import tieorange.com.pjabuffet.pojo.api.Order;

import static android.app.PendingIntent.FLAG_ONE_SHOT;
import static java.lang.Integer.parseInt;

/**
 * Created by tieorange on 15/12/2016.
 */

public class NotificationHandler {
  public static final String NOTIFICATION = "Notification";

  public static void showNotification(Context context, RemoteMessage.Notification notification) {
    getParsedJson(notification);

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

  private static PushNotificationBuffet getParsedJson(RemoteMessage.Notification notification) {
    Gson gson = new Gson();
    final String body = notification.getBody();

    return gson.fromJson(body, PushNotificationBuffet.class);
  }

  public static void showNotificationDummy(Context context) {
    // TODO: 15/12/2016  get Order object from backend
    Order order = OrderTools.getCurrentOrder();
    Intent resultIntent = Henson.with(context).gotoPaymentActivity().mOrder(order).build();
    resultIntent.setAction(NOTIFICATION);

    PendingIntent pendingIntent =
        PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    NotificationCompat.Builder mBuilder =
        new NotificationCompat.Builder(context).setSmallIcon(R.drawable.ic_notification_circle)
            .setContentTitle("Your meal is ready")
            .setContentIntent(pendingIntent)
            .setContentText("Your secret code: WT8");
    NotificationManager notificationManager =
        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

    notificationManager.notify(generateNotificationID(), mBuilder.build());
  }

  private static int generateNotificationID() {
    Date now = new Date();
    return parseInt(new SimpleDateFormat("ddHHmmss", Locale.US).format(now));
  }
}
