package tieorange.com.pjabuffet.utils;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import tieorange.com.pjabuffet.MyApplication;
import tieorange.com.pjabuffet.pojo.api.User;

/**
 * Created by tieorange on 16/10/2016.
 */

public class Tools {
  public static void setViewVisibility(View view, int visibility) {
    if (view == null) return;
    view.setVisibility(visibility);
  }

  public static int dpToPx(Context context, int dp) {
    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    return px;
  }

  public static int pxToDp(Context context, int px) {
    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    return dp;
  }

  public static Date getDate(long time) {
    Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    cal.setTimeInMillis(time);
    return cal.getTime();
  }

  public static void changeUserToken(String token) {
    FirebaseTools.pushUserToken(token);
    SharedPrefTools.setDeviceToken(token);
    MyApplication.sUser.setToken(token);
  }

  public static User getCurrentUser() {
    return MyApplication.sUser;
  }

  public static String getUserUID() {
    return Build.MODEL;
  }
}
