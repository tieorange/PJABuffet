package tieorange.com.pjabuffet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;
import io.codetail.animation.arcanimator.ArcAnimator;
import io.codetail.animation.arcanimator.Side;
import java.util.Collections;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import tieorange.com.pjabuffet.R;
import tieorange.com.pjabuffet.activities.ui.HidingScrollListener;
import tieorange.com.pjabuffet.fragmants.MenuFragment;
import tieorange.com.pjabuffet.fragmants.OrdersFragment;
import tieorange.com.pjabuffet.fragmants.ProfileFragment;
import tieorange.com.pjabuffet.pojo.api.Order;
import tieorange.com.pjabuffet.pojo.events.EventProductAddedToCart;
import tieorange.com.pjabuffet.pojo.events.EventProductTouch;
import tieorange.com.pjabuffet.pojo.events.EventToolbarSetVisibility;
import tieorange.com.pjabuffet.utils.FirebaseTools;
import tieorange.com.pjabuffet.utils.Interfaces.IOrderPushed;
import tieorange.com.pjabuffet.utils.OrderTools;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = MainActivity.class.getCanonicalName();
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.bottomBar) BottomBar bottomBar;
  @BindView(R.id.fab) FloatingActionButton fab;
  @BindView(R.id.frameContainer) FrameLayout frameContainer;
  @BindView(R.id.map_toolbar_container) CardView toolbarContainer;
  @BindView(R.id.nestedScroll) NestedScrollView nestedScroll;
  @BindView(R.id.rootLayout) CoordinatorLayout rootLayout;
  @BindView(R.id.button) Button button;
  private MenuFragment mMenuFragment;
  private OrdersFragment mOrdersFragment;
  private String mCurrentTabTag;
  private String TAG_MENU = "menu";
  private String TAG_ORDER = "mOrder";
  private String TAG_PROFILE = "profile";
  private Handler mHandler;
  private ProfileFragment mProfileFragment;
  private BottomBarTab mBottomTabOrders;
  private int mBadgeCount = 0;
  //@BindView(R.id.bottomBar) public BottomBar mBottomBar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);

    initFragments();
    mHandler = new Handler();
    initViews();

    experimentPusher();

  }

  private void experimentPusher() {
    PusherOptions options = new PusherOptions();
    options.setCluster("eu");
    Pusher pusher = new Pusher("a53ab27b8c366f3151ff", options);

    Channel channel = pusher.subscribe("test_channel");

    channel.bind("my_event", new SubscriptionEventListener() {
      @Override
      public void onEvent(String channelName, String eventName, final String data) {
        Log.d(TAG, "onEvent() called with: channelName = ["
            + channelName
            + "], eventName = ["
            + eventName
            + "], data = ["
            + data
            + "]");

        Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
      }
    });

    pusher.connect();
  }

  @Override public void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override public void onStop() {
    EventBus.getDefault().unregister(this);
    super.onStop();
  }

  private void initFragments() {
    mMenuFragment = MenuFragment.newInstance();
    mOrdersFragment = OrdersFragment.newInstance();
    mProfileFragment = ProfileFragment.newInstance();
  }

  private void switchTab(final String tabTag) {
    showToolbar();

    mCurrentTabTag = tabTag;
    Runnable mPendingRunnable = new Runnable() {
      @Override public void run() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragmentByTag = fragmentManager.findFragmentByTag(tabTag);
        if (fragmentByTag == null) {
          fragmentByTag = getSelectedTab();
          fragmentManager.beginTransaction()
              .replace(R.id.frameContainer, fragmentByTag, tabTag)
              .commitAllowingStateLoss();
        }
      }
    };

    if (mHandler != null) {
      mHandler.post(mPendingRunnable);
    }
  }

  private Fragment getSelectedTab() {
    Fragment fragment = mMenuFragment;
    if (mCurrentTabTag.equals(TAG_MENU)) {
      fragment = mMenuFragment;
      setFabVisibility(false);
    } else if (mCurrentTabTag.equals(TAG_ORDER)) {
      fragment = mOrdersFragment;
      setFabVisibility(true);
    } else if (mCurrentTabTag.equals(TAG_PROFILE)) {
      fragment = mProfileFragment;
      setFabVisibility(false);
    }
    return fragment;
  }

  private void setFabVisibility(boolean isVisible) {
    int delayMillis = 200;
    if (isVisible) {
      fab.postDelayed(new Runnable() {
        @Override public void run() {
          fab.show();
        }
      }, delayMillis);
    } else {
      fab.postDelayed(new Runnable() {
        @Override public void run() {
          fab.hide();
        }
      }, delayMillis);
    }
  }

  private void initViews() {
    setStatusBarTranslucent(true);
    mBottomTabOrders = bottomBar.getTabWithId(R.id.tab_orders);
    mBottomTabOrders.setBadgeCount(mBadgeCount);

    bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
      @Override public void onTabSelected(@IdRes int tabId) {
        if (tabId == R.id.tab_menu) {
          switchTab(TAG_MENU);
        } else if (tabId == R.id.tab_orders) {
          switchTab(TAG_ORDER);
        } else if (tabId == R.id.tab_profile) {
          switchTab(TAG_PROFILE);
        }
      }
    });

    nestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
      int SENSITIVITY_HIDE = 30;
      int SENSITIVITY_SHOW = -30;

      @Override
      public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX,
          int oldScrollY) {
        int scrollYDelta = scrollY - oldScrollY;
        Log.d(TAG, "onScrollChange() called with:" + "], scrollX = [" + scrollYDelta);

        if (scrollYDelta > SENSITIVITY_HIDE) {
          hideToolbar();
        } else if (scrollYDelta < SENSITIVITY_SHOW) {
          showToolbar();
        }
      }
    });

    nestedScroll.setOnScrollChangeListener(new HidingScrollListener() {
      @Override public void onHide() {
        hideToolbar();
      }

      @Override public void onShow() {
        showToolbar();
      }
    });
  }

  @OnClick(R.id.fab) public void onClickFab() {
    Order order = OrderTools.getCurrentOrder();

    orderToFirebase(order);
  }

  private void orderToFirebase(final Order order) {
    // TODO: 06/11/2016 show progress bar

    FirebaseTools.pushOrder(order, this, new IOrderPushed() {
      @Override public void orderPushed(String key) {
        // TODO: 06/11/2016 hide progress bar
        order.key = key;

        // TODO: check if there is a line:
        Intent intent = Henson.with(MainActivity.this).gotoLineActivity().mOrder(order).build();
        startActivity(intent);

       /* Intent intent = Henson.with(MainActivity.this).gotoPaymentActivity().mOrder(mOrder).build();
        startActivity(intent);*/
      }
    });
  }

  protected void setStatusBarTranslucent(boolean makeTranslucent) {
    if (makeTranslucent) {
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    } else {
      getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void onEvent(EventProductAddedToCart event) {
    //Toast.makeText(MainActivity.this, event.id, Toast.LENGTH_SHORT).show();
    mBadgeCount++;
    mBottomTabOrders.setBadgeCount(mBadgeCount - 1);
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void onEvent(EventToolbarSetVisibility event) {
    if (event.isVisible) {
      showToolbar();
    } else {
      hideToolbar();
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void onEvent(EventProductTouch event) {
    Log.d(TAG, "onEvent() called with: event = [" + event + "]");
    button.setX(event.x);
    button.setY(event.y);
    //experiment();
  }

  private void experiment() {
    int widthPixels = getResources().getDisplayMetrics().widthPixels;
    int heightPixels = getResources().getDisplayMetrics().heightPixels;

    int endX = widthPixels / 2;
    int endY = heightPixels;
    ArcAnimator arcAnimator =
        ArcAnimator.createArcAnimator(button, endX, endY, 100, Side.LEFT).setDuration(3000);

    Animation animationFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out_anim);
    animationFadeOut.setDuration(2000);
    animationFadeOut.setAnimationListener(new Animation.AnimationListener() {
      @Override public void onAnimationStart(Animation animation) {

      }

      @Override public void onAnimationEnd(Animation animation) {
        button.setVisibility(GONE);
      }

      @Override public void onAnimationRepeat(Animation animation) {

      }
    });

    arcAnimator.start();
    button.startAnimation(animationFadeOut);

    //TransitionManager.beginDelayedTransition(transitionsContainer, new ChangeBounds().setPathMotion(new ArcMotion()).setDuration(500));

    //ArcDebugView arcDebugView = new ArcDebugView(getContext());
    //arcDebugView.drawArcAnimator(arcAnimator);
  }

  private void hideToolbar() {
    float alpha = toolbarContainer.getAlpha();
    if (alpha == 0f) return;
    toolbarContainer.animate()
        .translationY(-toolbar.getBottom() * 2)
        .alpha(0)
        .setDuration(200)
        .setInterpolator(new AccelerateInterpolator(2))
        .start();
  }

  private void showToolbar() {
    float alpha = toolbarContainer.getAlpha();
    if (alpha == 1f) return;
    toolbarContainer.animate()
        .translationY(0)
        .alpha(1)
        .setDuration(200)
        .setInterpolator(new DecelerateInterpolator(2))
        .start();
  }
}
