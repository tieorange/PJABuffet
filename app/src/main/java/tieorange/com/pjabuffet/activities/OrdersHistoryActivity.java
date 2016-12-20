package tieorange.com.pjabuffet.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.f2prateek.dart.HensonNavigable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import tieorange.com.pjabuffet.MyApplication;
import tieorange.com.pjabuffet.R;
import tieorange.com.pjabuffet.activities.ui.sections_adapter.OrderSectionAdapter;
import tieorange.com.pjabuffet.pojo.OrderSection;
import tieorange.com.pjabuffet.pojo.api.Order;
import tieorange.com.pjabuffet.utils.Constants;
import tieorange.com.pjabuffet.utils.Tools;

@HensonNavigable public class OrdersHistoryActivity extends AppCompatActivity {

  private static final String TAG = OrdersHistoryActivity.class.getSimpleName();
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.content_orders_history) RelativeLayout mContentOrdersHistory;
  @BindView(R.id.fab) FloatingActionButton mFab;
  @BindView(R.id.recycler) RecyclerView mRecycler;
  private OrderSectionAdapter mAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_orders_history);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);

    final String userUID = Tools.getUserUID();
    MyApplication.sReferenceOrders.orderByChild(Constants.ORDER_CLIENT_NAME)
        .equalTo(userUID)
        .addValueEventListener(new ValueEventListener() {
          @Override public void onDataChange(DataSnapshot dataSnapshot) {
            List<Order> ordersList = new ArrayList<>();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
              final Order order = snapshot.getValue(Order.class);
              order.key = dataSnapshot.getKey();
              order.productsCart.convertProductsFromFirebase();
              ordersList.add(order);
            }

            initRecycler(ordersList);
          }

          @Override public void onCancelled(DatabaseError databaseError) {
            Log.d(TAG, "onCancelled() called with: databaseError = [" + databaseError + "]");
          }
        });
  }

  private void initRecycler(List<Order> ordersList) {
    mRecycler.setLayoutManager(new LinearLayoutManager(OrdersHistoryActivity.this));

    initAdapter(ordersList);
  }

  private void initAdapter(List<Order> ordersList) {
    List<OrderSection> sections = getOrdersSections(ordersList);
    mAdapter = new OrderSectionAdapter(OrdersHistoryActivity.this, sections);

    mRecycler.setAdapter(mAdapter);
  }

  private List<OrderSection> getOrdersSections(List<Order> ordersList) {
    List<OrderSection> sectionsList = new ArrayList<>();
    List<Order> current = new ArrayList<>();
    List<Order> past = new ArrayList<>();

    for (Order order : ordersList) {
      if (order.isStatusReady()) {
        past.add(order);
      } else {
        current.add(order);
      }
    }

    sectionsList.add(new OrderSection("Current", current));
    sectionsList.add(new OrderSection("Past", past));

    return sectionsList;
  }
}
