package tieorange.com.pjabuffet;

import android.app.Application;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tieorange.com.pjabuffet.api.MyEndpointInterface;
import tieorange.com.pjabuffet.api.Product;

/**
 * Created by tieorange on 16/10/2016.
 */

public class MyApplication extends Application {
  private static final String BASE_URL = "https://sheetlabs.com/";
  public static List<Product> mProducts = new ArrayList<>();
  public static List<Product> mProductsInCart = new ArrayList<>();
  public static String sSheetsLink = "https://sheetlabs.com/TIEO/jadlopis";
  public static Retrofit sRetrofit;
  public static MyEndpointInterface sApiService;

  @Override public void onCreate() {
    super.onCreate();

    initProducts();
    initRetrofit();
  }

  private void initRetrofit() {
    sRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

    sApiService = sRetrofit.create(MyEndpointInterface.class);
  }

  private void initProducts() {
    mProducts = Product.getDummy(30);
  }
}
