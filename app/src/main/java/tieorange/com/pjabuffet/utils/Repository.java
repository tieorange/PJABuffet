package tieorange.com.pjabuffet.utils;

import tieorange.com.pjabuffet.MyApplication;
import tieorange.com.pjabuffet.pojo.api.Product;

/**
 * Created by tieorange on 16/10/2016.
 */
public class Repository {
  /*public static List<Product> getProductsByIds(List<String> ids) {
    List<Product> results = new ArrayList<>();
    for (String id : ids) {
      for (Product mProduct : MyApplication.sProducts) {
        if (mProduct.id.equals(id)) {
          results.add(mProduct);
        }
      }
    }

    return results;
  }*/

  public static String getCartTotalPrice() {
    int resultInt = 0;
    for (Product product : MyApplication.sProductsInCart) {
      resultInt += product.price;
    }
    double resultDouble = Product.convertIntToDoublePrice(resultInt);
    return String.format("%.2f", resultDouble);
  }
}
