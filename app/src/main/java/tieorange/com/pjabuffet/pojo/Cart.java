package tieorange.com.pjabuffet.pojo;

import java.util.LinkedHashMap;
import java.util.Map;
import org.parceler.Parcel;
import tieorange.com.pjabuffet.pojo.api.Product;

/**
 * Created by tieorange on 07/12/2016.
 */

@Parcel public class Cart {
  private Map<Product, Integer> products = new LinkedHashMap<>();

  public Cart() {
    setProducts(new LinkedHashMap<Product, Integer>());
  }

  public Map<Product, Integer> getProducts() {
    return products;
  }

  public void setProducts(LinkedHashMap<Product, Integer> products) {
    this.products = products;
  }
}
