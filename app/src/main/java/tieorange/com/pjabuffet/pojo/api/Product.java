package tieorange.com.pjabuffet.pojo.api;

import com.google.firebase.database.Exclude;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.parceler.Parcel;
import org.parceler.ParcelConstructor;
import tieorange.com.pjabuffet.pojo.api.retro.ProductSheet;

/**
 * Created by tieorange on 15/10/2016.
 */
@Parcel public class Product implements Cloneable {
  public String name;
  public int price;
  public int cookingTime;
  public String photoUrl;
  public int amount;

  @ParcelConstructor public Product() {
    amount = 0;
  }

  public Product(String name, double price, int cookingTime) {
    this.name = name;
    this.price = convertPriceDoubleToInt(price);
    this.cookingTime = cookingTime;
  }

  private Product(ProductSheet productSheet) {
    if (productSheet.photoUrl.isEmpty() || productSheet.price == null) {
      return;
    }

    Random random = new Random();

    this.name = productSheet.name;
    this.price = convertPriceDoubleToInt(productSheet.price);
    this.cookingTime = random.nextInt(20);
    this.photoUrl = productSheet.photoUrl;
  }

  private static int convertPriceDoubleToInt(double price) {
    return (int) (price * 100);
  }

  public static List<Product> getDummy(int count) {
    List<Product> productList = new ArrayList<>();
    Random random = new Random();
    for (int i = 0; i < count; i++) {
      int cookingTime = random.nextInt(20);
      double price = random.nextDouble() * 20;
      productList.add(new Product("Pierogi", price, cookingTime));
    }
    return productList;
  }

  public static Product createProduct(ProductSheet productSheet) {
    if (productSheet == null || productSheet.photoUrl == null || productSheet.price == null) {
      return null;
    }
    return new Product(productSheet);
  }

  public static double convertIntToDoublePrice(int result) {
    return result / 100f;
  }

  @Exclude public String getStringPrice() {
    double priceDouble = price / 100f;
    return String.format("%.2f", priceDouble) + " zł";
  }

  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  public double getDoublePrice() {
    return price / 100f;
  }
}
