package tieorange.com.pjabuffet.activities.ui.sections_adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import tieorange.com.pjabuffet.R;
import tieorange.com.pjabuffet.activities.Henson;
import tieorange.com.pjabuffet.pojo.api.Order;
import tieorange.com.pjabuffet.pojo.api.Product;
import tieorange.com.pjabuffet.utils.CartTools;
import tieorange.com.pjabuffet.utils.OrderTools;
import tieorange.com.pjabuffet.utils.Tools;

/**
 * Created by tieorange on 19/12/2016.
 */

public class SectionItemViewHolder extends ChildViewHolder {
  @BindView(R.id.date) TextView mDate;
  @BindView(R.id.productsLinearLayout) LinearLayout mProductsLinearLayout;
  @BindView(R.id.Total) TextView mTotal;
  @BindView(R.id.secretCode) TextView mSecretCode;
  private View mItemView;

  public SectionItemViewHolder(View itemView) {
    super(itemView);
    mItemView = itemView;
    ButterKnife.bind(this, itemView);
  }

  public void onBind(Order order) {
    if (!order.isStatusReady()) {
      initClickListener(order);
    }

    String dateString = OrderTools.getDateStringFormatted(order);
    String totalPrice = CartTools.getCartTotalPrice(order.productsCart);
    mDate.setText(dateString);
    mTotal.setText("Total =  " + totalPrice);
    if (order.secretCode != null) {
      mSecretCode.setText(order.secretCode);
    } else {
      Tools.setViewVisibility(mSecretCode, View.GONE);
    }

    initProductsList(order);
  }

  private void initClickListener(final Order order) {
    mItemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        final Intent intent;
        final Context context = mItemView.getContext();

        final String key = order.key;
        if (order.isStatusOrdered()) {
          StringBuilder stringBuilder = new StringBuilder(key);
          final String newKey = stringBuilder.toString();
          intent = Henson.with(context).gotoLineActivity().mOrderKey(key).build();
        } else {
          intent = Henson.with(context).gotoPaymentActivity().mOrderKey(key).build();
        }
        context.startActivity(intent);
      }
    });
  }

  private void initProductsList(Order order) {
    int size = order.productsCart.getProducts().size();
    for (int i = 0; i < size; i++) {
      final Pair<Product, Integer> entry = CartTools.getEntry(i, order.productsCart);

      if (entry == null) continue;

      final String name = entry.first.name;
      final Integer amount = entry.second;
      final String price = entry.first.getStringPrice();
      String text = name + "  " + amount + " x " + price;

      TextView textView = new TextView(mItemView.getContext());
      textView.setText(text);
      textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.WRAP_CONTENT));
      mProductsLinearLayout.addView(textView);
    }
  }
}
