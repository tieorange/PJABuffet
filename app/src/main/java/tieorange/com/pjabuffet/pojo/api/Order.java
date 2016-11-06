package tieorange.com.pjabuffet.pojo.api;

import com.google.firebase.database.Exclude;
import java.util.ArrayList;
import java.util.List;
import org.parceler.Parcel;

/**
 * Created by tieorange on 03/11/2016.
 */
@Parcel public class Order {
  public static final int STATE_ORDERED = 1;
  public static final int STATE_ACCEPTED = 2;
  public static final int STATE_READY = 3;
  public static final int STATE_REJECTED = -1;

  public List<Product> products = new ArrayList<>();
  public String clientName;
  public int status;
  @Exclude public String key;

  public Order() {
  }
}
