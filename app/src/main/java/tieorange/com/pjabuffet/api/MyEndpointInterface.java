package tieorange.com.pjabuffet.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import tieorange.com.pjabuffet.api.retro.ProductSheet;

/**
 * Created by tieorange on 17/10/2016.
 */

public interface MyEndpointInterface {
  @GET("TIEO/Jadlopis2") Call<List<ProductSheet>> getAllProducts();
}
