package tieorange.com.pjabuffet.activities.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tieorange.com.pjabuffet.R;
import tieorange.com.pjabuffet.pojo.Cart;
import tieorange.com.pjabuffet.pojo.api.Product;
import tieorange.com.pjabuffet.utils.CartTools;
import tieorange.com.pjabuffet.utils.DialogTools;
import tieorange.com.pjabuffet.utils.Interfaces.IAllowedToRemove;
import tieorange.com.pjabuffet.utils.ProductsTools;

/**
 * Created by tieorange on 16/10/2016.
 */

public class AdapterOrderItem extends RecyclerView.Adapter<AdapterOrderItem.ViewHolder> {

  private final Context mContext;
  private Cart mCart;

  public AdapterOrderItem(Context context, Cart productList) {
    mContext = context;
    mCart = productList;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(mContext).inflate(R.layout.item_order, parent, false);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    final Pair<Product, Integer> entry = CartTools.getEntry(position);
    Product product = entry.first;
    final Integer amount = entry.second;

    holder.name.setText(product.name);
    holder.price.setText(product.getStringPrice());
    holder.amount.setText("" + amount);
  }

  @Override public int getItemCount() {
    return CartTools.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.name) TextView name;
    @BindView(R.id.price) TextView price;
    @BindView(R.id.amount) TextView amount;
    @BindView(R.id.plus) ImageButton plus;
    @BindView(R.id.minus) ImageButton minus;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.plus) void onClickPlus() {
      CartTools.addProductToCart(getProduct());
      updateAmountUI();
    }

    @OnClick(R.id.minus) void onClickMinus() {
      isAllowedToRemove(new IAllowedToRemove() {
        @Override public void remove() {
          CartTools.removeProductFromCart(getProduct());
          updateAmountUI();
        }
      });
    }

    private void isAllowedToRemove(final IAllowedToRemove iAllowedToRemove) {
      final int amount = CartTools.getAmount(getProduct());

      if (amount > 1) {
        iAllowedToRemove.remove();
      } else {
        DialogTools.getRemoveProduct(iAllowedToRemove, mContext, getProduct()).show();
      }
    }

    private Product getProduct() {
      return ProductsTools.getProduct(getAdapterPosition());
    }

    private void updateAmountUI() {
      int productAmount = CartTools.getAmount(getProduct());
      amount.setText("" + productAmount);

      // change icon:
      if (productAmount <= 1) {
        Drawable removeDrawable =
            ContextCompat.getDrawable(mContext, R.drawable.ic_remove_circle_black_24dp);
        minus.setImageDrawable(removeDrawable);
      } else {
        Drawable removeDrawable =
            ContextCompat.getDrawable(mContext, R.drawable.ic_minus_black_24dp);
        minus.setImageDrawable(removeDrawable);
      }
    }
  }
}
