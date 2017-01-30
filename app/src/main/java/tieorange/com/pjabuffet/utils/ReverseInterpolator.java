package tieorange.com.pjabuffet.utils;

import android.view.animation.Interpolator;

/**
 * Created by tieorange on 05/12/2016.
 */

public class ReverseInterpolator implements Interpolator {
  @Override public float getInterpolation(float paramFloat) {
    return Math.abs(paramFloat - 1f);
  }
}
