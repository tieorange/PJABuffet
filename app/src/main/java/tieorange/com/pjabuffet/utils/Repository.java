package tieorange.com.pjabuffet.utils;

import java.util.ArrayList;
import java.util.List;
import tieorange.com.pjabuffet.MyApplication;
import tieorange.com.pjabuffet.api.Product;

/**
 * Created by tieorange on 16/10/2016.
 */
public class Repository {
  public static List<Product> getProductsByIds(List<String> ids) {
    List<Product> results = new ArrayList<>();
    for (String id : ids) {
      for (Product mProduct : MyApplication.mProducts) {
        if (mProduct.id.equals(id)) {
          results.add(mProduct);
        }
      }
    }

    return results;
  }

  public static double getCartTotalPrice() {
    double result = 0;
    for (Product product : MyApplication.sProductsInCart) {
      result += product.price;
    }
    return result;
  }
}
