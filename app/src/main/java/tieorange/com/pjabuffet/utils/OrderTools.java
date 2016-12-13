package tieorange.com.pjabuffet.utils;

import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import tieorange.com.pjabuffet.MyApplication;
import tieorange.com.pjabuffet.pojo.Cart;
import tieorange.com.pjabuffet.pojo.api.Order;
import tieorange.com.pjabuffet.pojo.api.Product;

/**
 * Created by stud on 17/11/2016.
 */

public class OrderTools {
  private static final String TAG = OrderTools.class.getSimpleName();

  @NonNull public static Order getCurrentOrder() {
    final Cart productsCart = MyApplication.sProductsInCart;
    Order order = new Order();
    order.clientName = MyApplication.sUser.name;
    order.productsCart = productsCart;
    order.status = Order.STATE_ORDERED;
    order.initTimeStamp();
    return order;
  }

  // TODO: 10/12/2016 Remove
  public static Order getDummyOthersOrder(boolean isUsersOrder, int productsAmount) {
    Order order = new Order();
    order.status = Order.STATE_ORDERED;

    if (isUsersOrder) {
      order.clientName = Build.MODEL;
    } else {
      order.clientName = "Not current user";
    }

    for (int i = 0; i < productsAmount; i++) {
      Product product = null;
      try {
        product = (Product) MyApplication.sProducts.get(i).clone();
        product.cookingTime = 5;
      } catch (CloneNotSupportedException e) {
        Log.d(OrderTools.TAG, "getDummyOthersOrder() called " + e.getMessage());
      }
      //CartTools.addProductToCart(product);
    }
    order.initTimeStamp();
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
