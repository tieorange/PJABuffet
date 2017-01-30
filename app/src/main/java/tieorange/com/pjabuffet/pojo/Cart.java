package tieorange.com.pjabuffet.pojo;

import android.os.Parcelable;
import com.google.firebase.database.Exclude;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.parceler.Parcel;
import tieorange.com.pjabuffet.pojo.api.Product;

/**
 * Created by tieorange on 07/12/2016.
 */

@Parcel public class Cart implements Parcelable {
  public static final Parcelable.Creator<Cart> CREATOR = new Parcelable.Creator<Cart>() {
    @Override public Cart createFromParcel(android.os.Parcel source) {
      return new Cart(source);
    }

    @Override public Cart[] newArray(int size) {
      return new Cart[size];
    }
  };
  @Exclude private Map<Product, Integer> products = new LinkedHashMap<>();
  private List<Product> productsFirebase = new ArrayList<>();

  public Cart() {
    setProducts(new LinkedHashMap<Product, Integer>());
  }

  protected Cart(android.os.Parcel in) {
    int productsSize = in.readInt();
    this.products = new HashMap<Product, Integer>(productsSize);
    for (int i = 0; i < productsSize; i++) {
      Product key = in.readParcelable(Product.class.getClassLoader());
      Integer value = (Integer) in.readValue(Integer.class.getClassLoader());
      this.products.put(key, value);
    }
    this.productsFirebase = new ArrayList<Product>();
    in.readList(this.productsFirebase, Product.class.getClassLoader());
  }

  @Exclude public void convertProductsToFirebase() {
    for (Map.Entry<Product, Integer> productEntry : products.entrySet()) {
      final Product product = productEntry.getKey();
      product.amount = productEntry.getValue();
      productsFirebase.add(product);
    }
  }

  @Exclude public void convertProductsFromFirebase() {
    for (Product product : productsFirebase) {
      products.put(product, product.amount);
    }
  }

  @Exclude public Map<Product, Integer> getProducts() {
    return products;
  }

  @Exclude public void setProducts(LinkedHashMap<Product, Integer> products) {
    this.products = products;
  }

  public List<Product> getProductsFirebase() {
    return productsFirebase;
  }

  public void setProductsFirebase(List<Product> productsFirebase) {
    this.productsFirebase = productsFirebase;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(android.os.Parcel dest, int flags) {
    dest.writeInt(this.products.size());
    for (Map.Entry<Product, Integer> entry : this.products.entrySet()) {
      dest.writeParcelable(entry.getKey(), flags);
      dest.writeValue(entry.getValue());
    }
    dest.writeList(this.productsFirebase);
  }
}
