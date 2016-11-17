package tieorange.com.pjabuffet.utils;

import android.support.annotation.NonNull;

import java.util.List;

import tieorange.com.pjabuffet.MyApplication;
import tieorange.com.pjabuffet.pojo.api.Order;
import tieorange.com.pjabuffet.pojo.api.Product;

/**
 * Created by stud on 17/11/2016.
 */

public class OrderTools {
    @NonNull
    public static Order getCurrentOrder() {
        List<Product> productsInCart = MyApplication.sProductsInCart;
        Order order = new Order();
        order.clientName = MyApplication.sUser.name;
        order.products = productsInCart;
        order.status = Order.STATE_ORDERED;
        return order;
    }
}
