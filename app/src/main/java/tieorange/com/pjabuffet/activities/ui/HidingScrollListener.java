package tieorange.com.pjabuffet.activities.ui;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;

/**
 * Created by tieorange on 17/10/2016.
 */

public abstract class HidingScrollListener implements NestedScrollView.OnScrollChangeListener {
  private static final int HIDE_THRESHOLD = 20;
  private int scrolledDistance = 0;
  private boolean controlsVisible = true;

  @Override public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
    int dy = scrollY - oldScrollY;

    if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
      onHide();
      controlsVisible = false;
      scrolledDistance = 0;
    } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
      onShow();
      controlsVisible = true;
      scrolledDistance = 0;
    }

    if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
      scrolledDistance += dy;
    }
  }

  public abstract void onHide();

  public abstract void onShow();
}