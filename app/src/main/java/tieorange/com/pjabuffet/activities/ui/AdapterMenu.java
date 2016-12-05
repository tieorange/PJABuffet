package tieorange.com.pjabuffet.activities.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import tieorange.com.pjabuffet.MyApplication;
import tieorange.com.pjabuffet.R;
import tieorange.com.pjabuffet.pojo.api.Product;
import tieorange.com.pjabuffet.pojo.api.retro.ProductSheet;
import tieorange.com.pjabuffet.utils.Tools;

/**
 * Created by tieorange on 15/10/2016.
 */
public class AdapterMenu extends RecyclerView.Adapter<AdapterMenu.ViewHolderMenuItem> {
  private static final String TAG = AdapterMenu.class.getCanonicalName();
  public List<Product> mProducts = new ArrayList<>();
  private Context mContext;

  public AdapterMenu(Context mContext, List<ProductSheet> products) {
    this.mContext = mContext;
    if (products == null) {
      mProducts = new ArrayList<>(MyApplication.sProducts);
    } else {
      initProducts(products);
    }
  }

  private void initProducts(List<ProductSheet> products) {
    for (ProductSheet product : products) {
      if (product.price != null && product.photoUrl != null) {
        mProducts.add(Product.createProduct(product));
      }
    }

    MyApplication.sProducts.clear();
    MyApplication.sProducts.addAll(mProducts);
  }

  @Override public ViewHolderMenuItem onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(mContext).inflate(R.layout.item_menu, null);
    return new ViewHolderMenuItem(view);
  }

  @Override public void onBindViewHolder(ViewHolderMenuItem holder, int position) {
    Product product = mProducts.get(position);
    holder.name.setText(product.name);

    holder.price.setText(product.getStringPrice());
    String cookingTimeText = String.valueOf(product.cookingTime) + " min";
    holder.cookingTime.setText(cookingTimeText);
    Picasso.with(mContext)
        .load(product.photoUrl)
        .placeholder(R.drawable.pierogi_ruskie)
        .into(holder.image);
  }

  @Override public int getItemCount() {
    return mProducts.size();
  }

  public class ViewHolderMenuItem extends RecyclerView.ViewHolder {
    @BindView(R.id.image) ImageView image;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.price) TextView price;
    @BindView(R.id.cookingTime) TextView cookingTime;
    @BindView(R.id.amount) TextView amount;
    @BindView(R.id.cancel) ImageButton cancel;
    int currentAmount = 0;

    public ViewHolderMenuItem(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void amountIncrement() {
      amountAlphaAnimation();

      currentAmount++;
      amount.setText("" + currentAmount);

      checkCancelButton();
    }

    private void checkCancelButton() {
      if (currentAmount >= 1) {
        Tools.setViewVisibility(cancel, View.VISIBLE);
      } else {
        Tools.setViewVisibility(cancel, View.GONE);
      }
    }

    public void amountDecrement() {
      currentAmount--;
      amount.setText("" + currentAmount);
      if (currentAmount <= 0) {
        amount.setVisibility(View.GONE);
        return;
      }

      amountAlphaAnimation();
      checkCancelButton();
    }

    private void amountAlphaAnimation() {
      // alpha animation:
      Animation fadeInCode = AnimationUtils.loadAnimation(mContext, R.anim.fade_in_anim);
      fadeInCode.setDuration(300);
      amount.setVisibility(View.VISIBLE);
      amount.startAnimation(fadeInCode);
    }

    public int getCurrentAmount() {
      return currentAmount;
    }
   /* private void experimentAnimation(View itemView) {
      itemView.setOnTouchListener(new View.OnTouchListener() {
        @Override public boolean onTouch(View v, MotionEvent event) {
          Log.d(TAG, "onTouch() called with: v = [" + v + "], event = [" + event + "]");

          if (event.getAction() == MotionEvent.ACTION_UP) {
            float x = event.getRawX();
            float y = event.getRawY();
            EventBus.getDefault().post(new EventProductTouch(x, y));
          }
          return true;
        }
      });
    }*/
  }
}
