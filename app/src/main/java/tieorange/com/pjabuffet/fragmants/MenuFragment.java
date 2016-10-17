package tieorange.com.pjabuffet.fragmants;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import org.greenrobot.eventbus.EventBus;
import tieorange.com.pjabuffet.MyApplication;
import tieorange.com.pjabuffet.pojo.events.EventProductAddedToCart;
import tieorange.com.pjabuffet.api.Product;
import tieorange.com.pjabuffet.R;
import tieorange.com.pjabuffet.activities.ui.GridItemSpacingDecorator;
import tieorange.com.pjabuffet.activities.ui.ItemClickSupport;
import tieorange.com.pjabuffet.activities.ui.AdapterMenu;
import tieorange.com.pjabuffet.pojo.events.EventToolbarSetVisibility;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

  private static final String TAG = MenuFragment.class.getCanonicalName();
  @BindView(R.id.recycler) RecyclerView mRecycler;
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

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_menu, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    initRecycler();
  }

  @Override public void onStart() {
    super.onStart();
    //EventBus.getDefault().register(this);
  }

  @Override public void onStop() {
    //EventBus.getDefault().unregister(this);
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

    mRecycler.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
    int spacing = 40;
    //mRecycler.addItemDecoration(new SpacesItemDecoration(spacing, spacing, spacing, spacing));
    mRecycler.addItemDecoration(new GridItemSpacingDecorator(spanCount, spacing));

    ItemClickSupport.addTo(mRecycler).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
      @Override public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        Product product = mAdapter.mProducts.get(position);
        MyApplication.mProductsInCart.add(product);
        EventBus.getDefault().post(new EventProductAddedToCart());
      }
    });

    setRecyclerScrollListener();

    initAdapter();
  }

  private void setRecyclerScrollListener() {
    mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
      int SENSITIVITY_HIDE = 30;
      int SENSITIVITY_SHOW = -30;

      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        Log.d(TAG, "onScrolled: dy = " + dy);
        if (dy > SENSITIVITY_HIDE) {
          EventBus.getDefault().post(new EventToolbarSetVisibility(false));
        } else if (dy < SENSITIVITY_SHOW) EventBus.getDefault().post(new EventToolbarSetVisibility(true));
      }
    });
  }

  private void initAdapter() {
    mAdapter = new AdapterMenu(getContext());
    mRecycler.setAdapter(mAdapter);
  }
}
