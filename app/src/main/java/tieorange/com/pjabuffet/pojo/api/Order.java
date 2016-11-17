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
  public static final int STATE_ORDERED = 11;
  public static final int STATE_ACCEPTED = 12;
  public static final int STATE_READY = 21;
  public static final int STATE_REJECTED = 22;
  public static final String ORDERED_ORDERS_START_WITH = "1";
  public static final String FINISHED_ORDERS_START_WITH = "2";

  public List<Product> products = new ArrayList<>();
  public String clientName;
  public int status;
  @Exclude
  public String key;

  @Exclude
  private int position;

  @Exclude
  private int textColor;

  public Order() {
  }

  /*@Exclude
  public String getStatusString() {
    String statusNow = "NO STATE";
    if (getStatus() == STATE_ACCEPTED) {
      statusNow = "Accepted";
    } else if (getStatus() == STATE_ORDERED) {
      statusNow = "Ordered";
    } else if (getStatus() == STATE_READY) {
      statusNow = "Ready";
    } else if (getStatus() == STATE_REJECTED) {
      statusNow = "Rejected";
    }
    return statusNow;
  }

  @Exclude
  public void getExperiment(IStatesSwitch iStatesSwitch) {
    String statusNow = "NO STATE";
    if (getStatus() == STATE_ACCEPTED) {
      iStatesSwitch.accepted();
    } else if (getStatus() == STATE_ORDERED) {
      iStatesSwitch.ordered();
    } else if (getStatus() == STATE_READY) {
      iStatesSwitch.ready();
    } else {
//        if (getStatus() == STATE_REJECTED) {
      iStatesSwitch.rejected();
    }
//        return statusNow;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  @Bindable
  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
//    notifyPropertyChanged(com.android.databinding.library.baseAdapters.BR.products);
  }

  @Bindable
  public String getClientName() {
    return clientName;
  }

  public void setClientName(String clientName) {
    this.clientName = clientName;
//    notifyPropertyChanged(com.android.databinding.library.baseAdapters.BR.clientName);
  }

  @Bindable
  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
//    notifyPropertyChanged(com.android.databinding.library.baseAdapters.BR.status);
  }

  @Bindable
  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
//    notifyPropertyChanged(com.android.databinding.library.baseAdapters.BR.position);
  }

  @Bindable
  public int getTextColor() {
    textColor = R.color.material_color_green_400;

    getExperiment(new IStatesSwitch() {
      @Override
      public void ordered() {
        textColor = R.color.material_color_yellow_800;
      }

      @Override
      public void accepted() {
        textColor = R.color.material_color_green_500;
      }

      @Override
      public void ready() {
        textColor = R.color.material_color_green_800;
      }

      @Override
      public void rejected() {
        textColor = R.color.material_color_red_500;
      }
    });
    return textColor;
  }*/

  interface IStatesSwitch {
    void ordered();

    void accepted();

    void ready();

    void rejected();
  }
}
