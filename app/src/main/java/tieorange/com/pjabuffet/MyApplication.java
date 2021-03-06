package tieorange.com.pjabuffet;

import android.app.Application;
import android.content.SharedPreferences;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tieorange.com.pjabuffet.pojo.Cart;
import tieorange.com.pjabuffet.pojo.api.MyEndpointInterface;
import tieorange.com.pjabuffet.pojo.api.Product;
import tieorange.com.pjabuffet.pojo.api.User;
import tieorange.com.pjabuffet.utils.Constants;
import tieorange.com.pjabuffet.utils.SharedPrefTools;

import static tieorange.com.pjabuffet.utils.Tools.getUserUID;

/**
 * Created by tieorange on 16/10/2016.
 */

public class MyApplication extends Application {
  private static final String BASE_URL = "https://sheetlabs.com/";
  public static List<Product> sProducts = new ArrayList<>();
  //public static List<Product> sProductsInCart = new ArrayList<>();
  public static Cart sProductsInCart = new Cart();
  public static String sSheetsLink = "https://sheetlabs.com/TIEO/jadlopis";
  public static Retrofit sRetrofit;
  public static MyEndpointInterface sApiService;
  public static boolean sIsAddedTestProductsToCart = false;
  public static FirebaseDatabase sFirebaseDatabase;
  public static DatabaseReference sReferenceProducts;
  public static DatabaseReference sReferenceOrders;
  public static User sUser;
  public static SharedPreferences mSharedPreferences;
  public static DatabaseReference sReferenceUsers;

  @Override public void onCreate() {
    super.onCreate();

    initSharedPref();
    initFirebase();
    //    initProductsDummy();
    initRetrofit();
    initUser();
  }

  private void initSharedPref() {
    mSharedPreferences = getSharedPreferences("com.pjabuffet", MODE_PRIVATE);
  }

  private void initUser() {
    sUser = new User(SharedPrefTools.getDeviceToken(), getUserUID());
  }

  private void initFirebase() {
    sFirebaseDatabase = FirebaseDatabase.getInstance();
    sReferenceProducts = sFirebaseDatabase.getReference(Constants.PRODUCTS);
    sReferenceOrders = sFirebaseDatabase.getReference(Constants.ORDERS);
    sReferenceUsers = sFirebaseDatabase.getReference(Constants.USERS);
  }

  private void initRetrofit() {
    sRetrofit = new Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    sApiService = sRetrofit.create(MyEndpointInterface.class);
  }

  private void initProductsDummy() {
    sProducts = Product.getDummy(30);
  }
}
