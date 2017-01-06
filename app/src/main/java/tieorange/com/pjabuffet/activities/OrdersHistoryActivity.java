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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import tieorange.com.pjabuffet.R;
import tieorange.com.pjabuffet.activities.ui.GridItemSpacingDecorator;
import tieorange.com.pjabuffet.activities.ui.sections_adapter.OrderSectionAdapter;
import tieorange.com.pjabuffet.pojo.api.Order;
import tieorange.com.pjabuffet.utils.OrderTools;

@HensonNavigable
public class OrdersHistoryActivity extends AppCompatActivity {

    private static final String TAG = OrdersHistoryActivity.class.getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.content_orders_history)
    RelativeLayout mContentOrdersHistory;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    private OrderSectionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_history);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        Query query = OrderTools.getCurrentUserOrders();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Order> ordersList = OrderTools.processOrdersDataSnapshot(dataSnapshot);

                initRecycler(ordersList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled() called with: databaseError = [" + databaseError + "]");
            }
        });
    }


    private void initRecycler(List<Order> ordersList) {
        mRecycler.setLayoutManager(new LinearLayoutManager(OrdersHistoryActivity.this));
        final int spaceInPixels = 7;
        mRecycler.addItemDecoration(new GridItemSpacingDecorator(1, spaceInPixels));

        initAdapter(ordersList);
    }

    private void initAdapter(List<Order> ordersList) {
        mAdapter = new OrderSectionAdapter(OrdersHistoryActivity.this, ordersList);
        mRecycler.setAdapter(mAdapter);
    }
}
