package tieorange.com.pjabuffet;

/**
 * Created by tieorange on 16/10/2016.
 */
public class EventProductAddedToCart {
  public Product product;

  public EventProductAddedToCart(Product product) {
    this.product = product;
  }
}
