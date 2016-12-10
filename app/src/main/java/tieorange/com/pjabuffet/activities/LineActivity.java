package tieorange.com.pjabuffet.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.f2prateek.dart.InjectExtra;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import tieorange.com.pjabuffet.MyApplication;
import tieorange.com.pjabuffet.R;
import tieorange.com.pjabuffet.pojo.api.Order;
import tieorange.com.pjabuffet.pojo.api.Product;
import tieorange.com.pjabuffet.utils.CartTools;
import tieorange.com.pjabuffet.utils.FirebaseTools;
import tieorange.com.pjabuffet.utils.Tools;

public class LineActivity extends AppCompatActivity {
  private static final String TAG = LineActivity.class.getSimpleName();

  @InjectExtra Order mOrder;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.line2) View mLine2;
  @BindView(R.id.circleUserOrder) View mCircleUserOrder;
  @BindView(R.id.circleOtherOrders) View mCircleOtherOrders;
  @BindView(R.id.circleFinish) View mCircleFinish;
  @BindView(R.id.line1) View mLine1;
  @BindView(R.id.timeToWait) TextView mTimeToWait;
  @BindView(R.id.timeToWaitTextView) TextView mTimeToWaitTextView;
  @BindView(R.id.cardviewTime) CardView mCardviewTime;
  @BindView(R.id.otherOrdersTextView) TextView mOtherOrdersTextView;
  @BindView(R.id.otherOrders) TextView mOtherOrders;
  @BindView(R.id.userOrderTextView) TextView mUserOrderTextView;
  @BindView(R.id.userOrder) TextView mUserOrder;
  @BindView(R.id.finishTextView) TextView mFinishTextView;
  @BindView(R.id.content_line) ConstraintLayout mContentLine;
  @BindView(R.id.lineLongest) View mLineLongest;

  @BindDrawable(R.drawable.circle) Drawable mCircleDrawable;
  @BindColor(R.color.circleBackgroundPasive) int mColorPassiveCircle;
  @BindColor(R.color.circleBackgroundActive) int mColorActiveCircle;
  private int mSumOfTimeToWait = 0;
  private int mUserOrderTime;
  private int mOtherOrderTimeSum;
  private boolean mIsUserAloneInQueue = true;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_line);
    ButterKnife.bind(this);
    //Dart.inject(this);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    //initRecycler();
    //initOrderAcceptedListener();

    initDummy();
  }

  private void initDummy() {
    initLineListener();

    mTimeToWait.setText("~ 15 min.");

    mOtherOrders.setText("~ 10 min.");

    mUserOrder.setText("~ 5 min.");

    changeCircleColor(mCircleFinish, mColorPassiveCircle);

    //initOrderAloneInQueue();

    initDummyOrder();
  }

  // TODO: 10/12/2016 Remove
  private void initDummyOrder() {
    Order orderMine = getDummyOthersOrder(true, 3);
    Order orderOther = getDummyOthersOrder(false, 3);

    processTimeline(orderMine);
    //processTimeline(orderOther);

    initOrderAloneInQueue();
  }

  // TODO: 10/12/2016 Remove
  private Order getDummyOthersOrder(boolean isUsersOrder, int productsAmount) {
    Order order = new Order();
    order.status = Order.STATE_ORDERED;

    if (isUsersOrder) {
      order.clientName = Build.MODEL;
    } else {
      order.clientName = "Not current user";
    }

    for (int i = 0; i < productsAmount; i++) {
      Product product = null;
      try {
        product = (Product) MyApplication.sProducts.get(i).clone();
        product.cookingTime = 5;
      } catch (CloneNotSupportedException e) {
        Log.d(TAG, "getDummyOthersOrder() called " + e.getMessage());
      }
      CartTools.addProductToCart(product);
    }
    return order;
  }

  private void initOrderAloneInQueue() {
    if (!mIsUserAloneInQueue) return;
    // hide:
    mUserOrder.setVisibility(View.INVISIBLE);
    mUserOrderTextView.setVisibility(View.INVISIBLE);
    mCircleUserOrder.setVisibility(View.INVISIBLE);

    // show:
    Tools.setViewVisibility(mLineLongest, View.VISIBLE);

    // swap textViews:
    mOtherOrders = mUserOrder;
    mOtherOrdersTextView.setText(R.string.your_order);
  }

  private void initOrderNotAloneInQueue() {
    // TODO: 09/12/2016
  }

  private void changeCircleColor(View circleOtherOrders, int color) {
    // Change color:
    final LayerDrawable drawable = (LayerDrawable) circleOtherOrders.getBackground();
    final GradientDrawable smallerOvalSolid =
        (GradientDrawable) drawable.findDrawableByLayerId(R.id.smallerOvalSolid);
    final GradientDrawable biggerOvalStroke =
        (GradientDrawable) drawable.findDrawableByLayerId(R.id.biggerOvalStroke);

    final int strokeWidth = Tools.dpToPx(this, 2);
    smallerOvalSolid.setColor(color);
    biggerOvalStroke.setStroke(strokeWidth, color);
  }

  private void processTimeline(Order order) {
    // General time to wait:
    updateSumOfTime(order);

    if (!order.isCurrentUser()) {
      mIsUserAloneInQueue = false;
    }

    if (order.isCurrentUser()) {
      currentUserOrderProcess(order);
    } else {
      otherOrdersProcess(order);
    }
  }

  private void updateSumOfTime(Order order) {
    mSumOfTimeToWait += order.getSumOfTimeToWait();
    String sumOfTimeToWaitStr = getMinuteFormatString(mSumOfTimeToWait);
    mTimeToWait.setText(sumOfTimeToWaitStr);
  }

  private void currentUserOrderProcess(Order order) {
    // // TODO: 08/12/2016 handle - user can order multiple orders  ( += instead of =) ?
    mUserOrderTime = order.getSumOfTimeToWait();
    setCircleTime(mUserOrder, mUserOrderTime);
  }

  private void otherOrdersProcess(Order order) {
    mOtherOrderTimeSum += order.getSumOfTimeToWait();
    setCircleTime(mOtherOrders, mOtherOrderTimeSum);
  }

  private void setCircleTime(TextView textView, int time) {
    final String minutes = getMinuteFormatString(time);
    textView.setText(minutes);
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
            Log.d(TAG, "onCancelled() called with: databaseError = [" + databaseError + "]");
          }
        });
  }

  private void initLineListener() {
    Query query = FirebaseTools.getQueryOrdersOrdered();

    query.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot dataSnapshot) {
        Log.e("Count ", "" + dataSnapshot.getChildrenCount());
        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
          Order order = postSnapshot.getValue(Order.class);

          final boolean isOrderNotFinished =
              order.status != null && order.status.startsWith(Order.STATE_ACCEPTED.substring(0, 1));

          if (isOrderNotFinished) {
            processTimeline(order);
          }
        }

        initOrderAloneInQueue();
      }

      @Override public void onCancelled(DatabaseError databaseError) {
        Log.d(TAG, "onCancelled() called with: databaseError = [" + databaseError + "]");
      }
    });
  }

  private String getMinuteFormatString(int timeToWait) {
    return ("~ " + timeToWait + " min.");
  }
}
