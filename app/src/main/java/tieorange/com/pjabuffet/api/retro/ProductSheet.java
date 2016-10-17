package tieorange.com.pjabuffet.api.retro;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tieorange on 17/10/2016.
 */

public class ProductSheet {
  @SerializedName("name") @Expose public String name;
  @SerializedName("price") @Expose public Double price;
  @SerializedName("photourl") @Expose public String photoUrl;

  public ProductSheet(String name, double price) {
    this.name = name;
    this.price = price;
  }
}
