package tieorange.com.pjabuffet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tieorange on 15/10/2016.
 */
public class Product {
  public String name;
  public String nameSecondary;
  public float price;
  public int cookingTime;

  public Product(String name, String nameSecondary, float price, int cookingTime) {
    this.name = name;
    this.nameSecondary = nameSecondary;
    this.price = price;
    this.cookingTime = cookingTime;
  }

  public static List<Product> getDummy(int count) {
    List<Product> productList = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      productList.add(new Product("Pierogi", "Ruskie", 4.5f, 10));
    }
    return productList;
  }
}
