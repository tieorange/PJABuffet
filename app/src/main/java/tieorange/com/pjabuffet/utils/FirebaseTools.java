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

/**
 * Created by tieorange on 27/10/2016.
 */
public class FirebaseTools {
  public static void pushProducts(List<ProductSheet> productSheetList) {
    DatabaseReference productsReference = MyApplication.sFirebaseDatabase.getReference(Constants.PRODUCTS);

    for (ProductSheet productSheet : productSheetList) {
      Product product = Product.createProduct(productSheet);
      if (product != null) {
        productsReference.push().setValue(product);
      }
    }
  }

  public static void pushOrder(Order order, final Context context) {
    MyApplication.sFirebaseDatabase.getReference(Constants.ORDERS).push().setValue(order, new DatabaseReference.CompletionListener() {
      @Override public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
        Toast.makeText(context, "Ordered", Toast.LENGTH_SHORT).show();
      }
    });
  }
}
