package tieorange.com.pjabuffet.activities.ui.sections_adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import tieorange.com.pjabuffet.R;
import tieorange.com.pjabuffet.pojo.api.Order;
import tieorange.com.pjabuffet.utils.CartTools;
import tieorange.com.pjabuffet.utils.OrderTools;

/**
 * Created by tieorange on 19/12/2016.
 */

public class SectionItemViewHolder extends ChildViewHolder {
  @BindView(R.id.date) TextView mDate;
  @BindView(R.id.productsLinearLayout) LinearLayout mProductsLinearLayout;
  @BindView(R.id.Total) TextView mTotal;
  @BindView(R.id.secretCode) TextView mSecretCode;

  public SectionItemViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public void onBind(Order order) {
    String dateString = OrderTools.getDateHuman(order);
    String totalPrice = CartTools.getCartTotalPrice(order.productsCart);
    mDate.setText(dateString);
    mTotal.setText("Total =  " + totalPrice);
    mSecretCode.setText(order.secretCode);

    initProductsList(order);
  }

  private void initProductsList(Order order) {
    // TODO: 20/12/2016  
  }
}
