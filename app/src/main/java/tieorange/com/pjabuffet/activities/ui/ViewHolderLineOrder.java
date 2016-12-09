package tieorange.com.pjabuffet.activities.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import tieorange.com.pjabuffet.R;
import tieorange.com.pjabuffet.pojo.api.Order;
import tieorange.com.pjabuffet.utils.CartTools;

/**
 * Created by tieorange on 09/12/2016.
 */

// TODO: 09/12/2016 REMOVE (unused)
public class ViewHolderLineOrder extends RecyclerView.ViewHolder {
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
    mWaitingTime.setText(
        mContext.getString(R.string.estimated_waiting_time) + model.getSumOfTimeToWait() + " min.");

    return true;
  }
}
