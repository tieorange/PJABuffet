package tieorange.com.pjabuffet.fragmants;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tieorange.com.pjabuffet.MyApplication;
import tieorange.com.pjabuffet.R;
import tieorange.com.pjabuffet.activities.ui.AdapterMenu;
import tieorange.com.pjabuffet.activities.ui.GridItemSpacingDecorator;
import tieorange.com.pjabuffet.activities.ui.ItemClickSupport;
import tieorange.com.pjabuffet.pojo.api.retro.ProductSheet;
import tieorange.com.pjabuffet.pojo.events.EventToolbarSetVisibility;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

  private static final String TAG = MenuFragment.class.getCanonicalName();
  @BindView(R.id.recycler) RecyclerView mRecycler;
  @BindView(R.id.rootLayout) RelativeLayout rootLayout;
  private AdapterMenu mAdapter;

  public MenuFragment() {
    // Required empty public constructor
  }

  public static MenuFragment newInstance() {
    Bundle args = new Bundle();
    MenuFragment fragment = new MenuFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_menu, container, false);
    ButterKnife.bind(this, view);

    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initRecycler();
  }

  private void initRetrofit() {
    MyApplication.sApiService.getAllProducts().enqueue(new Callback<List<ProductSheet>>() {
      @Override
      public void onResponse(Call<List<ProductSheet>> call, Response<List<ProductSheet>> response) {
        if (response == null || response.body() == null) return;
        initAdapter(response.body());
        //FirebaseTools.pushProducts(response.body());
        //addTestProductsToCart(response.body());
      }

      @Override public void onFailure(Call<List<ProductSheet>> call, Throwable t) {

      }
    });
  }

  @Override public void onStart() {
    super.onStart();

    initRetrofit();
  }

  @Override public void onResume() {
    super.onResume();
  }

  @Override public void onPause() {

    super.onPause();
  }

  @Override public void onStop() {
    super.onStop();
  }

  private void initRecycler() {
    int spanCount;
    int orientation = getResources().getConfiguration().orientation;
    if (orientation == ORIENTATION_PORTRAIT) {
      spanCount = 2;
    } else {
      spanCount = 3;
    }

    StaggeredGridLayoutManager layoutManager =
        new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
    //layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
    mRecycler.setLayoutManager(layoutManager);
    int spacing = 20;
    //mRecycler.addItemDecoration(new SpacesItemDecoration(spacing, spacing, spacing, spacing));
    mRecycler.addItemDecoration(new GridItemSpacingDecorator(spanCount, spacing));

    setItemClickListener();
    setItemLongClickListener();

    setRecyclerScrollListener();

    //initAdapter(null);
  }

  private void setItemClickListener() {
    ItemClickSupport.addTo(mRecycler)
        .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
          @Override public void onItemClicked(RecyclerView recyclerView, int position, View view) {
            final AdapterMenu.ViewHolderMenuItem viewHolder =
                (AdapterMenu.ViewHolderMenuItem) recyclerView.findViewHolderForAdapterPosition(
                    position);
            viewHolder.amountIncrement();
          }
        });
  }

  private void setItemLongClickListener() {
    ItemClickSupport.addTo(mRecycler)
        .setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
          @Override
          public boolean onItemLongClicked(RecyclerView recyclerView, int position, View view) {
            final AdapterMenu.ViewHolderMenuItem viewHolder =
                (AdapterMenu.ViewHolderMenuItem) recyclerView.findViewHolderForAdapterPosition(
                    position);

            viewHolder.amountDecrement();
            return true;
          }
        });
  }

  private void setRecyclerScrollListener() {
    mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
      int SENSITIVITY_HIDE = 30;
      int SENSITIVITY_SHOW = -30;

      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        Log.d(TAG, "onScrolled: dy = " + dy);
        if (dy > SENSITIVITY_HIDE) {
          EventBus.getDefault().post(new EventToolbarSetVisibility(false));
        } else if (dy < SENSITIVITY_SHOW) {
          EventBus.getDefault().post(new EventToolbarSetVisibility(true));
        }
      }
    });
  }

  private void initAdapter(List<ProductSheet> products) {
    mAdapter = new AdapterMenu(getContext(), products);
    mRecycler.setAdapter(mAdapter);
  }
}
