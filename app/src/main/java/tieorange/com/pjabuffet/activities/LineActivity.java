package tieorange.com.pjabuffet.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import tieorange.com.pjabuffet.MyApplication;
import tieorange.com.pjabuffet.R;
import tieorange.com.pjabuffet.pojo.api.Order;
import tieorange.com.pjabuffet.utils.CartTools;
import tieorange.com.pjabuffet.utils.FirebaseTools;

public class LineActivity extends AppCompatActivity {
  @BindView(R.id.recycler) RecyclerView mRecycler;

  @InjectExtra Order mOrder;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.line2) View mLine2;
  @BindView(R.id.circleYourOrder) View mCircleYourOrder;
  @BindView(R.id.circleOtherOrders) View mCircleOtherOrders;
  @BindView(R.id.circleFinish) View mCircleFinish;
  @BindView(R.id.line1) View mLine1;
  @BindView(R.id.timeToWait) TextView mTimeToWait;
  @BindView(R.id.timeToWaitTextView) TextView mTimeToWaitTextView;
  @BindView(R.id.constraintLayout) ConstraintLayout mConstraintLayout;
  @BindView(R.id.otherOrdersTextView) TextView mOtherOrdersTextView;
  @BindView(R.id.otherOrders) TextView mOtherOrders;
  @BindView(R.id.yourOrderTextView) TextView mYourOrderTextView;
  @BindView(R.id.yourOrder) TextView mYourOrder;
  @BindView(R.id.finishTextView) TextView mFinishTextView;
  @BindView(R.id.content_line) ConstraintLayout mContentLine;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_line);
    ButterKnife.bind(this);
    Dart.inject(this);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    initRecycler();
    initOrderAcceptedListener();

    initDummy();
  }

  private void initDummy() {
    mTimeToWait.setText("~ 15 min.");

    mOtherOrders.setText("~ 10 min.");

    mYourOrder.setText("~ 5 min.");
  }

  private void initOrderAcceptedListener() {
    MyApplication.sReferenceOrders.child(mOrder.key)
        .addValueEventListener(new ValueEventListener() {
          @Override public void onDataChange(DataSnapshot dataSnapshot) {
            final Order value = dataSnapshot.getValue(Order.class);
            if (value.isStatusAccepted() || value.isStatusReady()) {
              final Intent intent =
                  Henson.with(LineActivity.this).gotoPaymentActivity().mOrder(mOrder).build();
              startActivity(intent);
            }
          }

          @Override public void onCancelled(DatabaseError databaseError) {

          }
        });
  }

  private void initRecycler() {
    mRecycler.setLayoutManager(new LinearLayoutManager(this));

    initAdapter();
  }

  private void initAdapter() {
    Query query = FirebaseTools.getQueryOrdersOrdered();
    //        Query query = MyApplication.sReferenceOrders.orderByChild(Constants.STATUS).startAt(1);

    FirebaseRecyclerAdapter<Order, ViewHolderLineOrder> adapter =
        new FirebaseRecyclerAdapter<Order, ViewHolderLineOrder>(Order.class,
            R.layout.item_line_order, ViewHolderLineOrder.class, query) {
          @Override protected void populateViewHolder(ViewHolderLineOrder viewHolder, Order model,
              int position) {
            model.key = getRef(position).getKey();
            viewHolder.init(LineActivity.this, model);
          }
        };

    mRecycler.setAdapter(adapter);
  }

  public static class ViewHolderLineOrder extends RecyclerView.ViewHolder {
    @BindView(R.id.rootLayout) LinearLayout mRootLayout;
    @BindView(R.id.productAmount) TextView mProductsAmount;
    @BindView(R.id.waitingTime) TextView mWaitingTime;
    private Context mContext;

    public ViewHolderLineOrder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void init(Context context, Order model) {
      mContext = context;
      if (isCurrentUserOrder(model)) return;

      if (model.status.equals(Order.STATE_ACCEPTED)) {
        mProductsAmount.setTypeface(null, Typeface.BOLD);
      }
      String text = CartTools.size() + " " + context.getString(R.string.products_ordered);
      mProductsAmount.setText(text);
    }

    private boolean isCurrentUserOrder(Order model) {
      if (!model.isCurrentUser()) return false;

      // Root:
      int bgColor = ContextCompat.getColor(mContext, R.color.material_color_amber_100);
      mRootLayout.setBackgroundColor(bgColor);

      // Amount:
      mProductsAmount.setTypeface(null, Typeface.BOLD);
      final String textViewContent = mContext.getString(R.string.your_order)
          + "\n"
          + CartTools.size()
          + " "
          + mContext.getString(R.string.products_ordered);
      mProductsAmount.setText(textViewContent);

      // Waiting time:
      mWaitingTime.setText(mContext.getString(R.string.estimated_waiting_time)
          + model.getSumOfTimeToWait()
          + " min.");

      return true;
    }
  }
}
