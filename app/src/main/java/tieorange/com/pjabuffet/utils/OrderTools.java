package tieorange.com.pjabuffet.utils;

import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import tieorange.com.pjabuffet.MyApplication;
import tieorange.com.pjabuffet.pojo.Cart;
import tieorange.com.pjabuffet.pojo.OrderSection;
import tieorange.com.pjabuffet.pojo.api.Order;
import tieorange.com.pjabuffet.pojo.api.Product;

/**
 * Created by stud on 17/11/2016.
 */

public class OrderTools {
    private static final String TAG = OrderTools.class.getSimpleName();

    @NonNull
    public static Order getCurrentOrder() {
        final Cart productsCart = MyApplication.sProductsInCart;
        Order order = new Order();
        order.clientName = MyApplication.sUser.getUid();
        order.productsCart = productsCart;
        order.status = Order.STATE_ORDERED;
        order.initTimeStamp();
        order.user = Tools.getCurrentUser(); // TODO: 1/5/17 auth
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
            Product product;
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

    public static String getDateStringFormatted(Order order) {
        Long createdAt = order.getCreatedAtLong();
        if (createdAt == null) {
            return "Unknown date";
        }
        return new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY).format(new Date(createdAt));
    }

    public static Query getCurrentUserOrders() {
        final String userUID = Tools.getUserUID();
        return MyApplication.sReferenceOrders.orderByChild(Constants.ORDER_CLIENT_NAME)
                .equalTo(userUID);
    }

    @NonNull
    public static List<Order> processOrdersDataSnapshot(DataSnapshot dataSnapshot) {
        List<Order> ordersList = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            final Order order = snapshot.getValue(Order.class);
            order.key = snapshot.getKey();
            order.productsCart.convertProductsFromFirebase();
            ordersList.add(order);
        }
        return ordersList;
    }

    public static List<Order> getCurrentOrders(List<Order> ordersList) {
        List<OrderSection> sectionsList = new ArrayList<>();
        List<Order> current = new ArrayList<>();

        for (Order order : ordersList) {
            if (!order.isStatusReady()) {
                current.add(order);
            }
        }
        return current;
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
