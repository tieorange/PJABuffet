package tieorange.com.pjabuffet.utils;

import android.content.Context;
import android.widget.Toast;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import java.util.List;
import tieorange.com.pjabuffet.MyApplication;
import tieorange.com.pjabuffet.pojo.api.Order;
import tieorange.com.pjabuffet.pojo.api.Product;
import tieorange.com.pjabuffet.pojo.api.retro.ProductSheet;
import tieorange.com.pjabuffet.utils.Interfaces.IOrderPushed;

/**
 * Created by tieorange on 27/10/2016.
 */
public class FirebaseTools {
  public static void pushProducts(List<ProductSheet> productSheetList) {
    DatabaseReference productsReference = MyApplication.sReferenceProducts;

    for (ProductSheet productSheet : productSheetList) {
      Product product = Product.createProduct(productSheet);
      if (product != null) {
        productsReference.push().setValue(product);
      }
    }
  }

  public static void pushOrder(final Order order, final Context context, final IOrderPushed iOrderPushed) {
    DatabaseReference referenceOrders = MyApplication.sReferenceOrders;

    referenceOrders.push().setValue(order, new DatabaseReference.CompletionListener() {
      @Override public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
        Toast.makeText(context, "Ordered", Toast.LENGTH_SHORT).show();
        String key = databaseReference.getKey();
        iOrderPushed.orderPushed(key);
      }
    });
  }
}
