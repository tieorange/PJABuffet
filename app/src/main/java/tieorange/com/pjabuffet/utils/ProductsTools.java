package tieorange.com.pjabuffet.utils;

import tieorange.com.pjabuffet.MyApplication;
import tieorange.com.pjabuffet.pojo.api.Product;

/**
 * Created by tieorange on 08/12/2016.
 */

public class ProductsTools {
  public static Product getProduct(int position) {
    if (position >= 0) {
      return MyApplication.sProducts.get(position);
    } else {
      return null;
    }
  }
}
