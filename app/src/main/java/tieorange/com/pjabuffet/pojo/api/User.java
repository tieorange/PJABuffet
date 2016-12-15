package tieorange.com.pjabuffet.pojo.api;

import org.parceler.Parcel;
import tieorange.com.pjabuffet.utils.Constants;

/**
 * Created by tieorange on 15/12/2016.
 */

@Parcel public class User {
  private final String device;
  private String uid;

  private String token;

  private User() {
    this.device = Constants.ANDROID;
  }

  public User(String token, String uid) {
    this();
    this.token = token;
    this.uid = uid;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getDevice() {
    return device;
  }
}
