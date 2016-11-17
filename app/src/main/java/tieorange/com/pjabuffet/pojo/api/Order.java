package tieorange.com.pjabuffet.pojo.api;

import com.google.firebase.database.Exclude;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tieorange on 03/11/2016.
 */

@Parcel
public class Order {
    public static final String ORDERED_ORDERS_START_WITH = "1";
    public static final String ORDERED_ORDERS_ENDS_WITH = ORDERED_ORDERS_START_WITH + "\\uf8ff";

    public static final String FINISHED_ORDERS_START_WITH = "2";

    public static final String STATE_ORDERED = "11";
    public static final String STATE_ACCEPTED = "12";
    public static final String STATE_READY = "29";
    public static final String STATE_REJECTED = "20";

    public List<Product> products = new ArrayList<>();
    public String clientName;
    public String status;
    @Exclude
    public String key;

    @Exclude
    private int position;

    @Exclude
    private int textColor;

    public Order() {
    }


    interface IStatesSwitch {
        void ordered();

        void accepted();

        void ready();

        void rejected();
    }
}
