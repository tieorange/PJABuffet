package tieorange.com.pjabuffet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by tieorange on 15/10/2016.
 */
public class Product {
  public String name;
  public String nameSecondary;
  public double price;
  public int cookingTime;

  public Product(String name, String nameSecondary, double price, int cookingTime) {
    this.name = name;
    this.nameSecondary = nameSecondary;
    this.price = price;
    this.cookingTime = cookingTime;
  }

  public static List<Product> getDummy(int count) {
    List<Product> productList = new ArrayList<>();
    Random random = new Random();
    for (int i = 0; i < count; i++) {
      int cookingTime = random.nextInt(20);
      double price = random.nextDouble()*20;
      productList.add(new Product("Pierogi", "Ruskie", price, cookingTime));
    }
    return productList;
  }

  public String getStringPrice() {
    return String.format("%.2f", price) + " zł";
  }
}
