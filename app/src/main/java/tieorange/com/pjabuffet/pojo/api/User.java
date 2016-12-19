package tieorange.com.pjabuffet.pojo.api;

import android.os.Parcelable;
import org.parceler.Parcel;
import tieorange.com.pjabuffet.utils.Constants;

/**
 * Created by tieorange on 15/12/2016.
 */

@Parcel public class User implements Parcelable {
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

  //region Parcel
  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(android.os.Parcel dest, int flags) {
    dest.writeString(this.device);
    dest.writeString(this.uid);
    dest.writeString(this.token);
  }

  protected User(android.os.Parcel in) {
    this.device = in.readString();
    this.uid = in.readString();
    this.token = in.readString();
  }

  public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
    @Override public User createFromParcel(android.os.Parcel source) {
      return new User(source);
    }

    @Override public User[] newArray(int size) {
      return new User[size];
    }
  };
  //endregion
}
