package tieorange.com.pjabuffet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tieorange on 15/10/2016.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

  private List<Product> mProducts = new ArrayList<>();
  private Context mContext;

  public MenuAdapter(Context mContext) {
    this.mContext = mContext;
    mProducts = Product.getDummy(10);
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(mContext).inflate(R.layout.item_menu, null);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    Product product = mProducts.get(position);
    holder.name.setText(product.name);
    holder.nameSecondary.setText(product.nameSecondary);
    holder.price.setText(String.valueOf(product.price));
    String cookingTimeText = String.valueOf(product.cookingTime) + "min";
    holder.cookingTime.setText(cookingTimeText);
  }

  @Override public int getItemCount() {
    return mProducts.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.image) ImageView image;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.nameSecondary) TextView nameSecondary;
    @BindView(R.id.price) TextView price;
    @BindView(R.id.cookingTime) TextView cookingTime;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
