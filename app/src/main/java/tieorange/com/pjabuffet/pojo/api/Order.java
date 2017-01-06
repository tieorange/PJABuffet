package tieorange.com.pjabuffet.pojo.api;

import android.os.Build;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;
import org.parceler.Transient;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import tieorange.com.pjabuffet.pojo.Cart;
import tieorange.com.pjabuffet.pojo.PushNotificationBuffet;
import tieorange.com.pjabuffet.utils.CartTools;

/**
 * Created by tieorange on 03/11/2016.
 */

@Parcel
public class Order {
    public static final String STATE_ORDERED = "39";
    public static final String STATE_ACCEPTED = "38";
    public static final String STATE_READY = "29";
    public static final String STATE_REJECTED = "20";

    public static final String ORDERED_ORDERS_START_WITH = STATE_ORDERED.substring(0, 1);
    public static final String ORDERED_ORDERS_ENDS_WITH = ORDERED_ORDERS_START_WITH + "\\uf8ff";
    public static final String FINISHED_ORDERS_START_WITH = STATE_READY.substring(0, 1);
    public static final String FINISHED_ORDERS_END_WITH = FINISHED_ORDERS_START_WITH + "\\uf8ff";

    public Cart productsCart = new Cart();
    public String clientName;
    public String status;
    public String secretCode;
    public String key;
    public User user;

    //    @Transient
    public HashMap<String, Object> createdAt; // should be Transient and Object in this app

    @ParcelConstructor
    public Order() {
    }

    public Order(PushNotificationBuffet parsedJson) {
        this.key = parsedJson.getOrderKey();
    }

    @Exclude
    public void initTimeStamp() {
        this.createdAt = new HashMap<>();
        createdAt.put("timestamp", ServerValue.TIMESTAMP);
//        this.createdAt = ServerValue.TIMESTAMP;
    }


    public Map<String, Object> getCreatedAt() {
        return createdAt;
    }

    @Exclude
    public Long getCreatedAtLong() {
        try {
            Object timestampStr = createdAt.get("timestamp");
            return (Long) timestampStr;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isCurrentUser() {
        if (clientName == null) return false;
        return clientName.equals(Build.MODEL);
    }

    @Exclude
    public boolean isStatusAccepted() {
        return status.equals(STATE_ACCEPTED);
    }

    @Exclude
    public boolean isStatusReady() {
        if (status == null) {
            return false;
        }
        return status.equals(STATE_READY);
    }

    @Exclude
    public boolean isStatusOrdered() {
        return status.equals(STATE_ORDERED);
    }

    @Exclude
    public boolean isStatusRejected() {
        return status.equals(STATE_REJECTED);
    }

    interface IStatesSwitch {
        void ordered();

        void accepted();

        void ready();

        void rejected();
    }

    public int getSumOfTimeToWait() {

        return CartTools.getSumOfTimeToWait();
    }

    //region Parcel

    //endregion

}
