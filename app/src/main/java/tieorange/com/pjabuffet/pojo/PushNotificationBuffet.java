package tieorange.com.pjabuffet.pojo;

import tieorange.com.pjabuffet.pojo.api.Order;

/**
 * Created by tieorange on 16/12/2016.
 */

public class PushNotificationBuffet {
  private String token;
  private String userUID;
  private String secretCode;
  private String orderKey;

  public PushNotificationBuffet() {
  }

  public PushNotificationBuffet(Order order) {
    this.token = order.user.getToken();
    this.userUID = order.user.getUid();
    this.secretCode = order.secretCode;
    this.orderKey = order.key;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getUserUID() {
    return userUID;
  }

  public void setUserUID(String userUID) {
    this.userUID = userUID;
  }

  public String getSecretCode() {
    return secretCode;
  }

  public void setSecretCode(String secretCode) {
    this.secretCode = secretCode;
  }

  public String getOrderKey() {
    return orderKey;
  }

  public void setOrderKey(String orderKey) {
    this.orderKey = orderKey;
  }
}
