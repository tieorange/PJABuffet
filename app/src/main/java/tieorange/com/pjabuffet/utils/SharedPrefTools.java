package tieorange.com.pjabuffet.utils;

import android.support.annotation.NonNull;
import tieorange.com.pjabuffet.MyApplication;

/**
 * Created by tieorange on 15/12/2016.
 */

public class SharedPrefTools {
  public static final String KEY_DEVICE_TOKEN = "deviceToken";
  public static final String KEY_DEVICE_TOKEN_STATE = "deviceTokenState";

  public static void setDeviceToken(@NonNull String token) {
    MyApplication.mSharedPreferences.edit().putString(KEY_DEVICE_TOKEN, token).apply();
  }

  public static String getDeviceToken() {
    return MyApplication.mSharedPreferences.getString(KEY_DEVICE_TOKEN, null);
  }

  public static void setDeviceTokenState(boolean state) {
    MyApplication.mSharedPreferences.edit().putBoolean(KEY_DEVICE_TOKEN_STATE, state).apply();
  }

  public static boolean getDeviceTokenState() {
    return MyApplication.mSharedPreferences.getBoolean(KEY_DEVICE_TOKEN_STATE, false);
  }
}
