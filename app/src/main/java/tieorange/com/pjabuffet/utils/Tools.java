package tieorange.com.pjabuffet.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import tieorange.com.pjabuffet.MyApplication;
import tieorange.com.pjabuffet.pojo.api.User;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

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

  public static Bitmap generateQrCode(String str) throws WriterException {
    int WIDTH = 300;
    BitMatrix result;
    try {
      result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, WIDTH, WIDTH, null);
    } catch (IllegalArgumentException iae) {
      // Unsupported format
      return null;
    }
    int w = result.getWidth();
    int h = result.getHeight();
    int[] pixels = new int[w * h];
    for (int y = 0; y < h; y++) {
      int offset = y * w;
      for (int x = 0; x < w; x++) {
        pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
      }
    }
    Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    bitmap.setPixels(pixels, 0, WIDTH, 0, 0, w, h);
    return bitmap;
  }
}
