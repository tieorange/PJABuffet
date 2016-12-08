package tieorange.com.pjabuffet.fragmants;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import tieorange.com.pjabuffet.R;
import tieorange.com.pjabuffet.activities.ui.AdapterOrderItem;
import tieorange.com.pjabuffet.utils.Constants;
import tieorange.com.pjabuffet.utils.CartTools;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment {

  @BindView(R.id.recycler) RecyclerView mRecycler;
  @BindView(R.id.footerLayout) RelativeLayout footerLayout;
  @BindView(R.id.footerTotalPrice) TextView footerTotalPrice;
  private AdapterOrderItem mAdapter;

  public OrdersFragment() {
    // Required empty public constructor
  }

  public static OrdersFragment newInstance() {
    Bundle args = new Bundle();
    OrdersFragment fragment = new OrdersFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_orders, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initRecycler();
  }

  private void initRecycler() {
    mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    initAdapter();
    initFooter();
  }

  private void initFooter() {
    if (CartTools.isEmpty()) {
      footerLayout.setVisibility(GONE);
    } else {
      footerLayout.setVisibility(View.VISIBLE);
    }

    footerTotalPrice.setText("Total price: " + CartTools.getCartTotalPrice() + Constants.CURRENCY);
  }

  private void initAdapter() {
    mAdapter = new AdapterOrderItem(getContext());
    mRecycler.setAdapter(mAdapter);
  }

  @Override public void onStop() {
    super.onStop();
  }
}
