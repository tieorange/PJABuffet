package tieorange.com.pjabuffet.activities.ui;

import android.animation.Animator;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
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
import tieorange.com.pjabuffet.utils.CartTools;
import tieorange.com.pjabuffet.utils.ProductsTools;
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

    @Override
    public ViewHolderMenuItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_menu, null);
        return new ViewHolderMenuItem(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderMenuItem holder, int position) {
        Product product = mProducts.get(position);
        holder.name.setText(product.name);

        holder.price.setText(product.getStringPrice());
        String cookingTimeText = String.valueOf(product.cookingTime) + " min";
        holder.cookingTime.setText(cookingTimeText);
        Picasso.with(mContext)
                .load(product.photoUrl)
                .placeholder(R.drawable.pierogi_ruskie)
                .into(holder.image);
        holder.checkIfAlreadyAddedToCart(product);
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ViewHolderMenuItem extends RecyclerView.ViewHolder {
        public static final int ANIMATION_DURATION_MILLIS = 300;
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.cookingTime)
        TextView cookingTime;
        @BindView(R.id.amount)
        TextView mAmount;
        @BindView(R.id.cancel)
        ImageButton mCancel;
        @BindView(R.id.revealView)
        View mRevealView;
        private int mSelectedBackgroundColor;
        private int mUnselectedBackgroundColor;

        public ViewHolderMenuItem(View view) {
            super(view);
            ButterKnife.bind(this, view);
            init();
            setCancelClickListener();
        }

        private void init() {
            mSelectedBackgroundColor = ContextCompat.getColor(mContext, R.color.material_color_green_50);
            mUnselectedBackgroundColor = ContextCompat.getColor(mContext, R.color.white);
        }

        private void checkIfAlreadyAddedToCart(Product position) {
            Integer productAmount = MyApplication.sProductsInCart.getProducts().get(position);

            //final boolean isNotAdded =
            //    productPair == null || productPair.first == null || productPair.second <= 0;
            if (productAmount == null || productAmount <= 0) return;

            mRevealView.setBackgroundColor(mSelectedBackgroundColor);
            mAmount.setVisibility(View.VISIBLE);
            mAmount.setText("" + CartTools.getCurrentAmount(getProduct()));
            mCancel.setVisibility(View.VISIBLE);


//      final Pair<Product, Integer> productPair = CartTools.getEntry(position);
//
//      //final boolean isNotAdded =
//      //    productPair == null || productPair.first == null || productPair.second <= 0;
//      if (productPair == null || productPair.first == null || productPair.second <= 0) return;
//
//      mRevealView.setBackgroundColor(mSelectedBackgroundColor);
//      mAmount.setVisibility(View.VISIBLE);
//      mAmount.setText("" + CartTools.getCurrentAmount(getProduct()));
//      mCancel.setVisibility(View.VISIBLE);
        }

        private void setCancelClickListener() {
            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    amountDecrement();
                }
            });
        }

        private void checkCancelButton() {
            if (CartTools.getCurrentAmount(getProduct()) >= 1) {
                Tools.setViewVisibility(mCancel, View.VISIBLE);
            } else {
                Tools.setViewVisibility(mCancel, View.GONE);
            }
        }

        public void amountIncrement() {
            CartTools.addProductToCart(ProductsTools.getProduct(getAdapterPosition()));

            amountAlphaAnimation();

            mAmount.setText("" + CartTools.getCurrentAmount(getProduct()));

            checkCancelButton();

            circularReveal(CartTools.getCurrentAmount(getProduct()), false, mContext);
        }

        public void amountDecrement() {
            CartTools.removeProductFromCart(ProductsTools.getProduct(getAdapterPosition()));

            mAmount.setText("" + CartTools.getCurrentAmount(getProduct()));
            checkCancelButton();
            if (CartTools.getCurrentAmount(getProduct()) <= 0) {
                zeroAmount();
                return;
            }

            amountAlphaAnimation();
        }

        private void zeroAmount() {
            CartTools.setCurrentAmount(0, ProductsTools.getProduct(getAdapterPosition()));
            mAmount.setVisibility(View.GONE);
            circularReveal(CartTools.getCurrentAmount(ProductsTools.getProduct(getAdapterPosition())), true,
                    mContext);
        }

        private void amountAlphaAnimation() {
            // alpha animation:
            Animation fadeInCode = AnimationUtils.loadAnimation(mContext, R.anim.fade_in_anim);
            fadeInCode.setDuration(ANIMATION_DURATION_MILLIS);
            mAmount.setVisibility(View.VISIBLE);
            mAmount.startAnimation(fadeInCode);
        }

        public void circularReveal(int amount, boolean isInverted, Context context) {
            if (amount < 0) return;

            int cx = (mRevealView.getLeft() + mRevealView.getRight()) / 2;
            int cy = (mRevealView.getTop() + mRevealView.getBottom()) / 2;

            // getProduct the final radius for the clipping circle
            int dx = Math.max(cx, mRevealView.getWidth() - cx);
            int dy = Math.max(cy, mRevealView.getHeight() - cy);
            float finalRadius = (float) Math.hypot(dx, dy);

            Animator animator =
                    ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, 0, finalRadius);

            if (isInverted) {
                animator = ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, finalRadius, 0);
            }

            animator.setInterpolator(new AccelerateDecelerateInterpolator());

            animator.setDuration(ANIMATION_DURATION_MILLIS);
            if (isInverted) {
                setAnimatorListener(mUnselectedBackgroundColor, animator);
            } else {
                mRevealView.setBackgroundColor(mSelectedBackgroundColor);
            }

            animator.start();
        }

        private void setAnimatorListener(final int whiteColor, Animator animator) {
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    mRevealView.setBackgroundColor(whiteColor);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }

        public Product getProduct() {
            return ProductsTools.getProduct(getAdapterPosition());
        }
    }
}
