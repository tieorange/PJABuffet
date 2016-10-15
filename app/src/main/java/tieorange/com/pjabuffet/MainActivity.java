package tieorange.com.pjabuffet;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.bottomBar) BottomBar bottomBar;
  @BindView(R.id.fab) FloatingActionButton fab;
  @BindView(R.id.frameContainer) FrameLayout frameContainer;
  private MenuFragment mMenuFragment;
  private OrdersFragment mOrdersFragment;
  private String mCurrentTabTag;
  private String TAG_MENU = "menu";
  private String TAG_ORDER = "order";
  private String TAG_PROFILE = "profile";
  private Handler mHandler;
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

  private void initFragments() {
    mMenuFragment = MenuFragment.newInstance();
    mOrdersFragment = OrdersFragment.newInstance();
    mProfileFragment = ProfileFragment.newInstance();
  }

  private void switchTab(String tabTag) {
    mCurrentTabTag = tabTag;
    Runnable mPendingRunnable = new Runnable() {
      @Override public void run() {
        Fragment fragment = getSelectedProfile();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frameContainer, fragment, mCurrentTabTag);
        fragmentTransaction.commitAllowingStateLoss();
      }
    };

    if (mHandler != null) {
      mHandler.post(mPendingRunnable);
    }
  }

  private Fragment getSelectedProfile() {
    Fragment fragment = mMenuFragment;
    if (mCurrentTabTag.equals(TAG_MENU)) {
      fragment = mMenuFragment;
    } else if (mCurrentTabTag.equals(TAG_ORDER)) {
      fragment = mOrdersFragment;
    } else if (mCurrentTabTag.equals(TAG_PROFILE)) {
      // TODO: 15/10/2016
    }
    return fragment;
  }

  private void initViews() {
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
      }
    });

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
}
