package tieorange.com.pjabuffet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import tieorange.com.pjabuffet.R;
import tieorange.com.pjabuffet.activities.ui.HidingScrollListener;
import tieorange.com.pjabuffet.fragmants.MenuFragment;
import tieorange.com.pjabuffet.fragmants.OrdersFragment;
import tieorange.com.pjabuffet.fragmants.ProfileFragment;
import tieorange.com.pjabuffet.pojo.events.EventProductAddedToCart;
import tieorange.com.pjabuffet.pojo.events.EventToolbarSetVisibility;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = MainActivity.class.getCanonicalName();
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.bottomBar) BottomBar bottomBar;
  @BindView(R.id.fab) FloatingActionButton fab;
  @BindView(R.id.frameContainer) FrameLayout frameContainer;
  @BindView(R.id.map_toolbar_container) CardView toolbarContainer;
  @BindView(R.id.nestedScroll) NestedScrollView nestedScroll;
  private MenuFragment mMenuFragment;
  private OrdersFragment mOrdersFragment;
  private String mCurrentTabTag;
  private String TAG_MENU = "menu";
  private String TAG_ORDER = "order";
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
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragmentByTag = fragmentManager.findFragmentByTag(tabTag);
        if (fragmentByTag == null) {
          fragmentByTag = getSelectedTab();
        }
        fragmentManager.beginTransaction().replace(R.id.frameContainer, fragmentByTag, tabTag).commitNow();

        //fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        //fragmentTransaction.replace(R.id.frameContainer, fragment, mCurrentTabTag);
        //fragmentTransaction.commitAllowingStateLoss();
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

      @Override public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
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
    Intent intent = PaymentActivity.buildIntent(this);
    startActivity(intent);
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
    mBottomTabOrders.setBadgeCount(++mBadgeCount);
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void onEvent(EventToolbarSetVisibility event) {
    if (event.isVisible) {
      showToolbar();
    } else {
      hideToolbar();
    }
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
    toolbarContainer.animate().translationY(0).alpha(1).setDuration(200).setInterpolator(new DecelerateInterpolator(2)).start();
  }
}
