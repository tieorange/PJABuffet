package tieorange.com.pjabuffet;

import android.app.Application;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tieorange.com.pjabuffet.pojo.api.MyEndpointInterface;
import tieorange.com.pjabuffet.pojo.api.Product;

/**
 * Created by tieorange on 16/10/2016.
 */

public class MyApplication extends Application {
  private static final String BASE_URL = "https://sheetlabs.com/";
  public static List<Product> sProducts = new ArrayList<>();
  public static List<Product> sProductsInCart = new ArrayList<>();
  public static String sSheetsLink = "https://sheetlabs.com/TIEO/jadlopis";
  public static Retrofit sRetrofit;
  public static MyEndpointInterface sApiService;
  public static boolean sIsAddedTestProductsToCart = false;
  public static FirebaseDatabase sFirebaseDatabase;

  @Override public void onCreate() {
    super.onCreate();

    initFirebase();
    initProducts();
    initRetrofit();
  }

  private void initFirebase() {
    sFirebaseDatabase = FirebaseDatabase.getInstance();
  }

  private void initRetrofit() {
    sRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

    sApiService = sRetrofit.create(MyEndpointInterface.class);
  }

  private void initProducts() {
    sProducts = Product.getDummy(30);
  }
}
