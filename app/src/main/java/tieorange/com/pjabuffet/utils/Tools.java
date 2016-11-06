package tieorange.com.pjabuffet.utils;

import android.view.View;

/**
 * Created by tieorange on 16/10/2016.
 */

public class Tools {
  public static void setViewVisibility(View view, int visibility) {
    if (view == null) return;
    view.setVisibility(visibility);
  }
}
