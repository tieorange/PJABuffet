package tieorange.com.pjabuffet.pojo.api;

import android.os.Build;
import com.google.firebase.database.Exclude;
import java.util.ArrayList;
import java.util.List;
import org.parceler.Parcel;

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
  public static final String FINISHED_ORDERS_START_WITH = STATE_READY.substring(0,1);
  public static final String FINISHED_ORDERS_END_WITH = FINISHED_ORDERS_START_WITH + "\\uf8ff";

  public List<Product> products = new ArrayList<>();
  public String clientName;
  public String status;
  public String secretCode;
  @Exclude public String key;

  @Exclude private int position;

  @Exclude private int textColor;

  public Order() {
  }

  public boolean isCurrentUser() {
    return clientName.equals(Build.MODEL);
  }

  public boolean isStatusAccepted() {
    return status.equals(STATE_ACCEPTED);
  }

  public boolean isStatusReady() {
    return status.equals(STATE_READY);
  }

  public boolean isStatusOrdered() {
    return status.equals(STATE_ORDERED);
  }

  public boolean isStatusRejected() {
    return status.equals(STATE_REJECTED);
  }

  interface IStatesSwitch {
    void ordered();

    void accepted();

    void ready();

    void rejected();
  }
}
