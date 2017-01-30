package tieorange.com.pjabuffet.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
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
import android.widget.Button;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.f2prateek.dart.HensonNavigable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.gson.Gson;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;
import tieorange.com.pjabuffet.R;
import tieorange.com.pjabuffet.activities.ui.BottomBarFixed;
import tieorange.com.pjabuffet.activities.ui.HidingScrollListener;
import tieorange.com.pjabuffet.activities.ui.LoadingSpinnerDialog;
import tieorange.com.pjabuffet.fragmants.EventProductRemovedFromCart;
import tieorange.com.pjabuffet.fragmants.MenuFragment;
import tieorange.com.pjabuffet.fragmants.OrdersFragment;
import tieorange.com.pjabuffet.fragmants.ProfileFragment;
import tieorange.com.pjabuffet.pojo.PushNotificationBuffet;
import tieorange.com.pjabuffet.pojo.api.Order;
import tieorange.com.pjabuffet.pojo.events.EventProductAddedToCart;
import tieorange.com.pjabuffet.pojo.events.EventProductTouch;
import tieorange.com.pjabuffet.pojo.events.EventToolbarSetVisibility;
import tieorange.com.pjabuffet.utils.CartTools;
import tieorange.com.pjabuffet.utils.Constants;
import tieorange.com.pjabuffet.utils.FirebaseTools;
import tieorange.com.pjabuffet.utils.Interfaces.IOrderPushed;
import tieorange.com.pjabuffet.utils.OrderTools;
import tieorange.com.pjabuffet.utils.Tools;

@HensonNavigable public class MainActivity extends AppCompatActivity {
  private static final String TAG = MainActivity.class.getCanonicalName();
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.bottomBar) BottomBarFixed mBottomBar;
  @BindView(R.id.fab) FloatingActionButton fab;
  @BindView(R.id.frameContainer) FrameLayout frameContainer;
  @BindView(R.id.map_toolbar_container) CardView toolbarContainer;
  @BindView(R.id.nestedScroll) NestedScrollView nestedScroll;
  @BindView(R.id.rootLayout) CoordinatorLayout rootLayout;
  @BindView(R.id.buttonLearnAnimation) Button button;
  private MenuFragment mMenuFragment;
  private OrdersFragment mOrdersFragment;
  private String mCurrentTabTag;
  private String TAG_MENU = "menu";
  private String TAG_ORDER = "mOrder";
  private String TAG_PROFILE = "profile";
  private Handler mHandlerForFAB;
  private ProfileFragment mProfileFragment;
  private BottomBarTab mBottomTabOrders;
  private int mBadgeCount = 0;
  private MenuItem mHistoryMenuItem;
  private ValueEventListener mFirebaseListenerBadge;
  private Snackbar mSnackBar;
  //@BindView(R.id.mBottomBar) public BottomBar mBottomBar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);

    initFragments();
    initViews();
    startPushService();
    checkExtras();
  }

  private void checkExtras() {
    // Check notification extras
    Bundle extras = getIntent().getExtras();
    if (extras == null) return;
    String orderKey = extras.getString(Constants.ORDER_KEY_);
    if (orderKey == null) return;
    Intent intent =
        Henson.with(MainActivity.this).gotoOrderFinishedActivity().mOrderKey(orderKey).build();
    startActivity(intent);
  }

  private void initBadgeChecking() {
    Query query = OrderTools.getCurrentUserOrders();
    mFirebaseListenerBadge = new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot dataSnapshot) {
        List<Order> userOrders = OrderTools.processOrdersDataSnapshot(dataSnapshot);
        List<Order> currentOrdersList = OrderTools.getCurrentOrders(userOrders);
        if (currentOrdersList == null) return;
        updateBudge(currentOrdersList.size());
      }

      @Override public void onCancelled(DatabaseError databaseError) {
        Log.d(TAG, "onCancelled() called with: databaseError = [" + databaseError + "]");
      }
    };
    query.addValueEventListener(mFirebaseListenerBadge);
  }

  private void experimentParseJson() {
    String json =
        "{\\\"orderKey\\\":\\\"-KZWgEI_srVso1eR8Uaq\\\",\\\"secretCode\\\":\\\"786\\\",\\\"token\\\":\\\"c8MxKBXI4sI:APA91bEGF0ux03XYkVKqQ4x7xDWMsIJc04IMj1tbj72UdQzZtMQeauy2Yibl8hT4b3ozOWgOKDsp2FFjbYnUI3FmCIUfLLXiUBDnLWEfafJzb0ffAm88jeFRVGAYZMA7jmrfapSvHjtY\\\",\\\"userUID\\\":\\\"Pixel\\\"}";
    //         Remove backslash:
    json = json.replace("\\", "");

    Gson gson = new Gson();
    JSONObject.quote(json);
    PushNotificationBuffet pushNotificationBuffet =
        gson.fromJson(json, PushNotificationBuffet.class);
  }

  private void startPushService() {
    final String token = FirebaseInstanceId.getInstance().getToken();
    Tools.changeUserToken(token);
    Log.d(TAG, "token() called: " + token);
    Intent intent = new Intent(this, FirebaseInstanceIdService.class);
    startService(intent);

    // TODO: 16/12/2016 RM:
    experimentParseJson();
    //NotificationHandler.showNotificationDummy(this);



/*
    Intent intent2 = new Intent(this, FirebaseMessagingService.class);
    startService(intent2);*/
  }

  @Override protected void onResume() {
    Log.d(TAG, "onResume() called");
    super.onResume();
    //        invalidateOptionsMenu();
    supportInvalidateOptionsMenu();
    initBadgeChecking();
  }

  @Override public void onStart() {
    Log.d(TAG, "onStart() called");
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override public void onStop() {
    Log.d(TAG, "onStop() called");
    EventBus.getDefault().unregister(this);
    super.onStop();
  }

  @Override protected void onDestroy() {
    Query currentUserOrders = OrderTools.getCurrentUserOrders();
    if (mFirebaseListenerBadge != null) {
      currentUserOrders.removeEventListener(mFirebaseListenerBadge);
    }
    super.onDestroy();
  }

  @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    if (savedInstanceState == null) return;
    mBadgeCount = savedInstanceState.getInt(Constants.BADGE_COUNT, 0);
    if (mBottomTabOrders == null) return;
    mBottomTabOrders.setBadgeCount(mBadgeCount);
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    outState.putInt(Constants.BADGE_COUNT, mBadgeCount);
    super.onSaveInstanceState(outState);
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

    if (mHandlerForFAB != null) {
      mHandlerForFAB.post(mPendingRunnable);
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
    mHandlerForFAB = new Handler();
    setStatusBarTranslucent(false);
    mBottomTabOrders = mBottomBar.getTabAtPosition(1); /*mBottomBar.getTabWithId(R.id.tab_orders);*/

    mBottomTabOrders.setBadgeCount(mBadgeCount);

    mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
      @Override public void onTabSelected(@IdRes int tabId) {
        if (tabId == R.id.tab_menu) {
          switchTab(TAG_MENU);
        } else if (tabId == R.id.tab_orders_new) {
          switchTab(TAG_ORDER);
        } else if (tabId == R.id.tab_profile) {
          switchTab(TAG_PROFILE);
        }
      }
    });

    initToolbarHiding();
  }

  private void initToolbarHiding() {
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

    orderToFirebase();
  }

  private void orderToFirebase() {
    // TODO: 06/11/2016 show progress bar
    final LoadingSpinnerDialog loadingSpinnerDialog =
        LoadingSpinnerDialog.newInstance(getSupportFragmentManager());
    loadingSpinnerDialog.show();

    final Order order = OrderTools.getCurrentOrder();

    FirebaseTools.pushOrder(order, this, new IOrderPushed() {
      @Override public void orderPushed(String key) {
        // TODO: 06/11/2016 hide progress bar
        order.key = key;

        // TODO: check if there is a line:
        Intent intent =
            Henson.with(MainActivity.this).gotoLineActivity().mOrderKey(order.key).build();
        startActivity(intent);

        loadingSpinnerDialog.dismiss();

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

    // Badge library
    mHistoryMenuItem = menu.findItem(R.id.action_orders_history);

    return true;
  }

  private void updateBudge(final int notificationsCount) {
    if (mHistoryMenuItem == null) {
      return;
    }
    runOnUiThread(new Runnable() {
      @Override public void run() {
        if (notificationsCount > 0) {
          Drawable icon =
              ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_orders_history_action);
          ActionItemBadge.update(MainActivity.this, mHistoryMenuItem, icon,
              ActionItemBadge.BadgeStyles.RED, 3);
        }
      }
    });
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    switch (id) {
      case R.id.action_settings:
        return true;
      case R.id.action_search:
        break;
      case R.id.action_orders_history:
        final Intent intent = Henson.with(this).gotoOrdersHistoryActivity().build();
        startActivity(intent);
        break;
    }

    return super.onOptionsItemSelected(item);
  }

  private void updateSnackBarPrice() {
    float bottomBarHeight = getResources().getDimension(R.dimen.bottomBarHeight) - 10;

    String orderPriceSum = CartTools.getCartTotalPrice();
    String snackText = getString(R.string.sum) + orderPriceSum + " zł";
    if (mSnackBar == null || !mSnackBar.isShown()) {
      mSnackBar = Snackbar.make(rootLayout, snackText, Snackbar.LENGTH_INDEFINITE)
          .setAction("Order", new View.OnClickListener() {
            @Override public void onClick(View v) {
              switchTab(TAG_ORDER);
              mBottomBar.selectTabAtPosition(1);
            }
          });
      CoordinatorLayout.LayoutParams params =
          (CoordinatorLayout.LayoutParams) mSnackBar.getView().getLayoutParams();
      params.setMargins(0, 0, 0, (int) bottomBarHeight);
      mSnackBar.getView().setLayoutParams(params);
    } else {
      mSnackBar.setText(snackText);
    }

    mSnackBar.show();
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void onEvent(EventProductAddedToCart event) {
    mBadgeCount++;
    mBottomTabOrders.setBadgeCount(mBadgeCount);

    updateSnackBarPrice();
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void onEvent(EventProductRemovedFromCart event) {
    mBadgeCount--;
    mBottomTabOrders.setBadgeCount(mBadgeCount);

    updateSnackBarPrice();
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

  @Override protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    Bundle extras = intent.getExtras();
  }
}
