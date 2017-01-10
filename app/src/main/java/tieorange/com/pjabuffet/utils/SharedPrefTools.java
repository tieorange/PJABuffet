package tieorange.com.pjabuffet.utils;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import tieorange.com.pjabuffet.MyApplication;
import tieorange.com.pjabuffet.pojo.Cart;

/**
 * Created by tieorange on 15/12/2016.
 */

public class SharedPrefTools {
    public static final String KEY_DEVICE_TOKEN = "deviceToken";
    public static final String KEY_DEVICE_TOKEN_STATE = "deviceTokenState";
    private static final String KEY_CART = "cart";

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

    public static void setCart(Cart cart) {
        String cartJson = cart == null ? null : new Gson().toJson(cart);
        MyApplication.mSharedPreferences.edit().putString(KEY_CART, cartJson).apply();
    }

    public static Cart getCart() {
        String cartJson = MyApplication.mSharedPreferences.getString(KEY_CART, null);
        return cartJson == null ? null : new Gson().fromJson(cartJson, Cart.class);

    }
}
