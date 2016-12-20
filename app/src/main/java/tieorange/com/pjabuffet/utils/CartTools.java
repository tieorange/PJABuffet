package tieorange.com.pjabuffet.utils;

import android.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.greenrobot.eventbus.EventBus;
import tieorange.com.pjabuffet.MyApplication;
import tieorange.com.pjabuffet.fragmants.EventProductRemovedFromCart;
import tieorange.com.pjabuffet.pojo.Cart;
import tieorange.com.pjabuffet.pojo.api.Product;
import tieorange.com.pjabuffet.pojo.api.retro.ProductSheet;
import tieorange.com.pjabuffet.pojo.events.EventProductAddedToCart;

/**
 * Created by tieorange on 16/10/2016.
 */
public class CartTools {
  private static final Cart cart = MyApplication.sProductsInCart;
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

  private static void addTestProductsToCart(List<ProductSheet> products) {
    if (MyApplication.sIsAddedTestProductsToCart) return;

    for (int i = 0; i < 6; i++) {
      ProductSheet product = products.get(i);
      Product addedProducts = Product.createProduct(product);
      if (addedProducts != null) {
        add(addedProducts);
      }
      EventBus.getDefault().post(new EventProductAddedToCart());
    }
    MyApplication.sIsAddedTestProductsToCart = true;
  }

  public static void addProductToCart(Product product) {
    EventBus.getDefault().post(new EventProductAddedToCart());
    add(product);
  }

  public static void removeProductFromCart(Product product) {
    EventBus.getDefault().post(new EventProductRemovedFromCart());
    remove(product);
  }

  public static int getCurrentAmount(Product product) {
    return getAmount(product);
  }

  public static void setCurrentAmount(int amount, Product product) {
    setAmount(product, amount);
  }

  public static void add(Product product) {
    if (!cart.getProducts().containsKey(product)) {
      cart.getProducts().put(product, 1);
    } else {
      incrementAmount(product);
    }
  }

  public static void remove(Product product) {
    if (!cart.getProducts().containsKey(product)) return;

    final Integer amount = cart.getProducts().get(product);
    if (amount <= 1) { // remove from map
      cart.getProducts().remove(product);
    } else { // decrement
      decrementAmount(product);
    }
  }

  private static void incrementAmount(Product product) {
    final Integer newValue = cart.getProducts().get(product) + 1;
    cart.getProducts().put(product, newValue);
  }

  private static void decrementAmount(Product product) {
    final Integer newValue = cart.getProducts().get(product) - 1;
    cart.getProducts().put(product, newValue);
  }

  public static boolean isEmpty() {
    return cart.getProducts().isEmpty();
  }

  public static Product getProduct(int position) {
    final Set<Product> values = cart.getProducts().keySet();
    ArrayList<Product> valueList = new ArrayList<>(values);
    if (valueList.isEmpty()) return null;
    final Product product = valueList.get(position);
    return product;
  }

  public static Integer getAmount(int position) {
    ArrayList<Integer> valueList = new ArrayList<>(cart.getProducts().values());
    if (valueList.isEmpty()) return null;
    return valueList.get(position);
  }

  public static String getCartTotalPrice() {
    return getCartTotalPrice(cart);
  }

  public static String getCartTotalPrice(Cart cart) {
    int resultInt = 0;
    for (Map.Entry<Product, Integer> productEntry : cart.getProducts().entrySet()) {
      int productSum = productEntry.getKey().price * productEntry.getValue();
      resultInt += productSum;
    }
    double resultDouble = Product.convertIntToDoublePrice(resultInt);
    return String.format("%.2f", resultDouble);
  }

  public static int size() {
    return cart.getProducts().size();
  }

  public static int getSumOfTimeToWait() {
    int resultSum = 0;
    for (Map.Entry<Product, Integer> productEntry : cart.getProducts().entrySet()) {
      final int productTimeSum = productEntry.getKey().cookingTime * productEntry.getValue();
      resultSum += productTimeSum;
    }
    return resultSum;
  }

  public static Pair<Product, Integer> getEntry(int position, Cart cart) {
    if (position >= cart.getProducts().size()) return null;

    final Product product = getProduct(position);
    final Integer amount = getAmount(position);

    if (product == null || amount == null) return null;

    Pair<Product, Integer> resultPair = new Pair<>(product, amount);
    return resultPair;
  }

  public static Pair<Product, Integer> getEntry(int position) {
    return getEntry(position, cart);
  }

  public static int getAmount(Product product) {
    final Integer integer = cart.getProducts().get(product);
    if (integer == null) {
      return 0;
    } else {
      return integer;
    }
    //return integer == null ? 0 : integer;
  }

  public static void setAmount(Product product, int amount) {
    for (int i = 0; i < amount; i++) {
      add(product);
    }
  }
}
