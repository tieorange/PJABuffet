package tieorange.com.pjabuffet.pojo.api;

import android.os.Build;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;
import org.parceler.Parcel;
import org.parceler.Transient;
import tieorange.com.pjabuffet.pojo.Cart;
import tieorange.com.pjabuffet.utils.CartTools;

/**
 * Created by tieorange on 03/11/2016.
 */

@Parcel public class Order {
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
  @Transient public Object createdAt;
  // TODO: 13/12/2016 RM:
  /*private HashMap<String, Object> dateCreated = new HashMap<>();
  private HashMap<String, Object> dateLastChanged = new HashMap<>();*/

  @Exclude public String key;

  @Exclude private int position;

  @Exclude private int textColor;

  public Order() {
  }

  // TODO: 13/12/2016 REMOVE:
  /*public HashMap<String, Object> getDateLastChanged() {
    return dateLastChanged;
  }

  public HashMap<String, Object> getDateCreated() {
    if (dateCreated != null) {
      return dateCreated;
    }
    final HashMap<String, Object> dateCreated = new HashMap<>();
    dateCreated.put("date", ServerValue.TIMESTAMP);
    return dateCreated;
  }

  @Exclude public long getDateLastChangedLong() {
    return (long) dateLastChanged.get(Constants.DATE);
  }

  @Exclude public long getDateCreatedLong() {
    return (long) dateCreated.get("date");
  }

  @Exclude public void initTimeStampManually() {
    dateCreated.put(Constants.DATE, ServerValue.TIMESTAMP);
  }*/

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
}
