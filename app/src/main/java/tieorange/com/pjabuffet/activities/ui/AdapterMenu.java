package tieorange.com.pjabuffet.activities.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import tieorange.com.pjabuffet.MyApplication;
import tieorange.com.pjabuffet.R;
import tieorange.com.pjabuffet.api.Product;
import tieorange.com.pjabuffet.api.retro.ProductSheet;

import static android.view.View.GONE;

/**
 * Created by tieorange on 15/10/2016.
 */
public class AdapterMenu extends RecyclerView.Adapter<AdapterMenu.ViewHolder> {

  public List<Product> mProducts = new ArrayList<>();
  private Context mContext;

  public AdapterMenu(Context mContext, List<ProductSheet> products) {
    this.mContext = mContext;
    if (products == null) {
      mProducts = new ArrayList<>(MyApplication.mProducts);
    } else {
      initProducts(products);
    }
  }

  private void initProducts(List<ProductSheet> products) {
    for (ProductSheet product : products) {
      if (product.price != null && product.photoUrl != null) {
        mProducts.add(new Product(product));
      }
    }
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(mContext).inflate(R.layout.item_menu, null);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    Product product = mProducts.get(position);
    holder.name.setText(product.name);

    if (!product.nameSecondary.isEmpty()) {
      holder.nameSecondary.setText(product.nameSecondary);
    } else {
      holder.nameSecondary.setVisibility(GONE);
    }

    holder.price.setText(product.getStringPrice());
    String cookingTimeText = String.valueOf(product.cookingTime) + " min";
    holder.cookingTime.setText(cookingTimeText);
    Picasso.with(mContext).load(product.photoUrl).placeholder(R.drawable.pierogi_ruskie).into(holder.image);
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
