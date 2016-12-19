package tieorange.com.pjabuffet.pojo.api;

import android.os.Build;
import android.os.Parcelable;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;
import org.parceler.Parcel;
import org.parceler.Transient;
import tieorange.com.pjabuffet.pojo.Cart;
import tieorange.com.pjabuffet.pojo.PushNotificationBuffet;
import tieorange.com.pjabuffet.utils.CartTools;

/**
 * Created by tieorange on 03/11/2016.
 */

@Parcel public class Order implements Parcelable {
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
  public User user;
  @Transient public Object createdAt;

  @Exclude public String key;

  public Order() {
  }

  public Order(PushNotificationBuffet parsedJson) {
    this.key = parsedJson.getOrderKey();
  }

  @Exclude public void initTimeStamp() {
    createdAt = ServerValue.TIMESTAMP;
  }

  @Exclude public Long getCreatedAt() {
    if (createdAt instanceof Long) {
      return (Long) createdAt;
    }
    return null;
  }

  public boolean isCurrentUser() {
    if (clientName == null) return false;
    return clientName.equals(Build.MODEL);
  }

  @Exclude public boolean isStatusAccepted() {
    return status.equals(STATE_ACCEPTED);
  }

  @Exclude public boolean isStatusReady() {
    if (status == null) {
      return false;
    }
    return status.equals(STATE_READY);
  }

  @Exclude public boolean isStatusOrdered() {
    return status.equals(STATE_ORDERED);
  }

  @Exclude public boolean isStatusRejected() {
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
  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(android.os.Parcel dest, int flags) {
    dest.writeParcelable(this.productsCart, flags);
    dest.writeString(this.clientName);
    dest.writeString(this.status);
    dest.writeString(this.secretCode);
    dest.writeParcelable(this.user, flags);
    dest.writeLong((Long) this.createdAt);
    dest.writeString(this.key);
  }

  protected Order(android.os.Parcel in) {
    this.productsCart = in.readParcelable(Cart.class.getClassLoader());
    this.clientName = in.readString();
    this.status = in.readString();
    this.secretCode = in.readString();
    this.user = in.readParcelable(User.class.getClassLoader());
    this.createdAt = in.readParcelable(Object.class.getClassLoader());
    this.key = in.readString();
  }

  public static final Creator<Order> CREATOR = new Creator<Order>() {
    @Override public Order createFromParcel(android.os.Parcel source) {
      return new Order(source);
    }

    @Override public Order[] newArray(int size) {
      return new Order[size];
    }
  };
  //endregion
}
