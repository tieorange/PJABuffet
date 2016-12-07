package tieorange.com.pjabuffet.utils;

import java.util.List;
import org.greenrobot.eventbus.EventBus;
import tieorange.com.pjabuffet.MyApplication;
import tieorange.com.pjabuffet.pojo.api.Product;
import tieorange.com.pjabuffet.pojo.api.retro.ProductSheet;
import tieorange.com.pjabuffet.pojo.events.EventProductAddedToCart;

/**
 * Created by tieorange on 16/10/2016.
 */
public class CartTools {
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
    return MyApplication.sProductsInCart.getCartTotalPrice();
  }

  private static void addTestProductsToCart(List<ProductSheet> products) {
    if (MyApplication.sIsAddedTestProductsToCart) return;

    for (int i = 0; i < 6; i++) {
      ProductSheet product = products.get(i);
      Product addedProducts = Product.createProduct(product);
      if (addedProducts != null) {
        MyApplication.sProductsInCart.add(addedProducts);
      }
      EventBus.getDefault().post(new EventProductAddedToCart());
    }
    MyApplication.sIsAddedTestProductsToCart = true;
  }

  public static void addProductToCart(Product product) {
    // TODO: 07/12/2016 change amount
    MyApplication.sProductsInCart.add(product);
  }

  public static void removeProductFromCart(Product product) {
    MyApplication.sProductsInCart.remove(product);
  }
}
