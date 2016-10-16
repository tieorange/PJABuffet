package tieorange.com.pjabuffet;

import android.app.Application;
import java.util.ArrayList;
import java.util.List;
import tieorange.com.pjabuffet.api.Product;

/**
 * Created by tieorange on 16/10/2016.
 */

public class MyApplication extends Application {
  public static List<Product> mProducts = new ArrayList<>();
  public static List<Product> mProductsInCart = new ArrayList<>();

  @Override public void onCreate() {
    super.onCreate();

    initProducts();
  }

  private void initProducts() {
    mProducts = Product.getDummy(30);
  }
}
