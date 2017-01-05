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
import java.util.Map;

import tieorange.com.pjabuffet.R;
import tieorange.com.pjabuffet.activities.Henson;
import tieorange.com.pjabuffet.activities.LineActivity;
import tieorange.com.pjabuffet.activities.PaymentActivity;
import tieorange.com.pjabuffet.pojo.PushNotificationBuffet;
import tieorange.com.pjabuffet.pojo.api.Order;

import static android.app.PendingIntent.FLAG_ONE_SHOT;
import static java.lang.Integer.parseInt;

/**
 * Created by tieorange on 15/12/2016.
 */

public class NotificationHandler {
    private static final String NOTIFICATION = "Notification";

    public static void showNotification(Context context, RemoteMessage message) {
        Map<String, String> data = message.getData();
        System.out.println("data = " + data);

        RemoteMessage.Notification notification = message.getNotification();

        final PushNotificationBuffet pushObject = getParsedJson(message.getData());

        Order order = new Order(pushObject);
        Intent resultIntent = PaymentActivity.buildIntentClearBackStack(context, order.key);
        resultIntent.setAction(NOTIFICATION);

        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, 0, resultIntent, FLAG_ONE_SHOT);
        NotificationCompat.Builder mBuilder = getBuilder(context, pendingIntent, pushObject);
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(generateNotificationID(), mBuilder.build());
    }

    private static NotificationCompat.Builder getBuilder(Context context, PendingIntent pendingIntent,
                                                         PushNotificationBuffet pushObject) {
        return new NotificationCompat.Builder(context).setSmallIcon(R.drawable.ic_notification_circle)
                .setContentTitle(context.getString(R.string.your_order_is_ready))
                .setContentIntent(pendingIntent)
                .setContentText(context.getString(R.string.your_secret_code) + pushObject.getSecretCode());
    }

    private static PushNotificationBuffet getParsedJson(Map<String, String> dataMap) {
        Gson gson = new Gson();
        String jsonStr = MapToJsonConverter.convert(dataMap);
        return gson.fromJson(jsonStr, PushNotificationBuffet.class);
    }

    /*public static void showNotificationDummy(Context context) {
        // TODO: 15/12/2016  get Order object from backend
        Order order = OrderTools.getCurrentOrder();
        Intent resultIntent = Henson.with(context).gotoPaymentActivity().mOrderKey(order.key).build();
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
    }*/

    private static int generateNotificationID() {
        Date now = new Date();
        return parseInt(new SimpleDateFormat("ddHHmmss", Locale.US).format(now));
    }
}
