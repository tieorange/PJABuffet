package tieorange.com.pjabuffet;

import android.app.Application;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tieorange on 16/10/2016.
 */

public class MyApplication extends Application {
  public static List<Product> mProducts = new ArrayList<>();

  @Override public void onCreate() {
    super.onCreate();

    initProducts();
  }

  private void initProducts() {
    mProducts = Product.getDummy(30);
  }
}
