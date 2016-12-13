package tieorange.com.pjabuffet.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    //time = time * 1000;
    Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    cal.setTimeInMillis(time);

    //String date = DateFormat.format("dd-MM-yyyy", cal).toString();
    return cal.getTime();
  }
}
