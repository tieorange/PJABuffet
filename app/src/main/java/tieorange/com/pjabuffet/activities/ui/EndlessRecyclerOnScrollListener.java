package tieorange.com.pjabuffet.activities.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by tieorange on 17/10/2016.
 */
public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

  public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();

  // use your LayoutManager instead
  private LinearLayoutManager llm;

  public EndlessRecyclerOnScrollListener(LinearLayoutManager sglm) {
    this.llm = llm;
  }

  @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    super.onScrolled(recyclerView, dx, dy);

    if (!recyclerView.canScrollVertically(1)) {
      onScrolledToEnd();
    }
  }

  public abstract void onScrolledToEnd();
}
