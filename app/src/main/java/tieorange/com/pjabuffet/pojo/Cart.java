package tieorange.com.pjabuffet.pojo;

import android.util.Pair;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.parceler.Parcel;
import tieorange.com.pjabuffet.pojo.api.Product;

/**
 * Created by tieorange on 07/12/2016.
 */

@Parcel public class Cart {
  private Map<Product, Integer> products = new LinkedHashMap<>();

  public Cart() {
    products = new LinkedHashMap<>();
  }

  public void add(Product product) {
    if (!products.containsKey(product)) {
      products.put(product, 1);
    } else {
      incrementAmount(product);
    }
  }

  public void remove(Product product) {
    if (!products.containsKey(product)) return;

    final Integer amount = products.get(product);
    if (amount <= 1) { // remove from map
      products.remove(product);
    } else { // decrement
      decrementAmount(product);
    }
  }

  private void incrementAmount(Product product) {
    final Integer newValue = products.get(product) + 1;
    products.put(product, newValue);
  }

  private void decrementAmount(Product product) {
    final Integer newValue = products.get(product) - 1;
    products.put(product, newValue);
  }

  public Map<Product, Integer> getProducts() {
    return products;
  }

  public void setProducts(Map<Product, Integer> products) {
    this.products = products;
  }

  public boolean isEmpty() {
    return products.isEmpty();
  }

  public Product getProduct(int position) {
    final Set<Product> values = products.keySet();
    ArrayList<Product> valueList = new ArrayList<>(values);
    if (valueList.isEmpty()) return null;
    final Product product = valueList.get(position);
    return product;
  }

  public Integer getAmount(int position) {
    ArrayList<Integer> valueList = new ArrayList<>(products.values());
    if (valueList.isEmpty()) return null;
    return valueList.get(position);
  }

  public String getCartTotalPrice() {
    int resultInt = 0;
    for (Map.Entry<Product, Integer> productEntry : products.entrySet()) {
      int productSum = productEntry.getKey().price * productEntry.getValue();
      resultInt += productSum;
    }
    double resultDouble = Product.convertIntToDoublePrice(resultInt);
    return String.format("%.2f", resultDouble);
  }

  public int size() {
    return products.size();
  }

  public int getSumOfTimeToWait() {
    int resultSum = 0;
    for (Map.Entry<Product, Integer> productEntry : products.entrySet()) {
      final int productTimeSum = productEntry.getKey().cookingTime * productEntry.getValue();
      resultSum += productTimeSum;
    }
    return resultSum;
  }

  public Pair<Product, Integer> getEntry(int position) {
    // 1            1
    if (position >= products.size()) return null;

    final Product product = getProduct(position);
    final Integer amount = getAmount(position);

    if (product == null || amount == null) return null;

    Pair<Product, Integer> resultPair = new Pair<>(product, amount);
    return resultPair;
  }

  public int getAmount(Product product) {
    final Integer integer = products.get(product);
    if (integer == null) {
      return 0;
    } else {
      return integer;
    }
    //return integer == null ? 0 : integer;
  }

  public void setAmount(Product product, int amount) {
    for (int i = 0; i < amount; i++) {
      add(product);
    }
  }


}
