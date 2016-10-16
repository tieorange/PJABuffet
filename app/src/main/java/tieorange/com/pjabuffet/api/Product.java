package tieorange.com.pjabuffet.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by tieorange on 15/10/2016.
 */
public class Product implements Cloneable{
  public String id;
  public String name;
  public String nameSecondary;
  public double price;
  public int cookingTime;

  public Product(String name, String nameSecondary, double price, int cookingTime, String id) {
    this.name = name;
    this.nameSecondary = nameSecondary;
    this.price = price;
    this.cookingTime = cookingTime;
    this.id = id;
  }

  public static List<Product> getDummy(int count) {
    List<Product> productList = new ArrayList<>();
    Random random = new Random();
    for (int i = 0; i < count; i++) {
      int cookingTime = random.nextInt(20);
      double price = random.nextDouble() * 20;
      productList.add(new Product("Pierogi", "Ruskie", price, cookingTime, String.valueOf(i)));
    }
    return productList;
  }

  public String getStringPrice() {
    return String.format("%.2f", price) + " zł";
  }

  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
