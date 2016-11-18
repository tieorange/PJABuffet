package tieorange.com.pjabuffet.utils;

import android.support.annotation.NonNull;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import java.util.List;
import java.util.UUID;
import tieorange.com.pjabuffet.MyApplication;
import tieorange.com.pjabuffet.pojo.api.Order;
import tieorange.com.pjabuffet.pojo.api.Product;

/**
 * Created by stud on 17/11/2016.
 */

public class OrderTools {
  @NonNull public static Order getCurrentOrder() {
    List<Product> productsInCart = MyApplication.sProductsInCart;
    Order order = new Order();
    order.clientName = MyApplication.sUser.name;
    order.products = productsInCart;
    order.status = Order.STATE_ORDERED;
    return order;
  }

 /* public static String getRandomCode() {
    String uuid = UUID.randomUUID().toString();
    uuid = uuid.substring(0, 3);
    return uuid.toUpperCase();
  }

  public static void setSecretCodeToFirebase(Order order,
      final ISecretCodeSetCompleted iSecretCodeSetCompleted) {
    order.secretCode = getRandomCode();


    MyApplication.sReferenceOrders.child(order.key)
        .setValue(order, new DatabaseReference.CompletionListener() {
          @Override
          public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
            iSecretCodeSetCompleted.onComplete(databaseError, databaseReference);
          }
        });
  }

  public interface ISecretCodeSetCompleted {
    void onComplete(DatabaseError databaseError, DatabaseReference databaseReference);
  }*/
}
