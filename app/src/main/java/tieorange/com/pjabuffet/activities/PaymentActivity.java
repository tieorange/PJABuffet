package tieorange.com.pjabuffet.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;
import tieorange.com.pjabuffet.MyApplication;
import tieorange.com.pjabuffet.R;
import tieorange.com.pjabuffet.pojo.api.Order;
import tieorange.com.pjabuffet.utils.OrderTools;
import tieorange.com.pjabuffet.utils.Tools;

public class PaymentActivity extends AppCompatActivity {

  private static final String TAG = PaymentActivity.class.getCanonicalName();
  @InjectExtra Order mOrder;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.circularFillableLoaders) CircularFillableLoaders circularFillableLoaders;
  @BindView(R.id.tvYourCode) TextView tvYourCode;
  @BindView(R.id.code) TextView code;
  @BindView(R.id.CodeLayout) ConstraintLayout CodeLayout;
  @BindView(R.id.rootLayout) ConstraintLayout rootLayout;
  @BindView(R.id.fab) FloatingActionButton fab;
  @BindView(R.id.tvAccepted) TextView mTvAccepted;

  public static Intent buildIntent(Context context) {
    Intent intent = new Intent(context, PaymentActivity.class);
    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_payment);
    ButterKnife.bind(this);
    Dart.inject(this);
    setSupportActionBar(toolbar);

    initFAB();

    initViews();
    initFirebase();
  }

  private void initFirebase() {
    if (mOrder == null || mOrder.key == null) {
      Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
      return;
    }
    MyApplication.sReferenceOrders.child(mOrder.key)
        .addValueEventListener(new ValueEventListener() {
          @Override public void onDataChange(DataSnapshot dataSnapshot) {
            Order value = dataSnapshot.getValue(Order.class);
            mOrder = value;
            if (value.isStatusAccepted()) {
              orderAccepted();
            } else if (value.isStatusReady()) {
              showSecretCodeView();
            }
          }

          @Override public void onCancelled(DatabaseError databaseError) {

          }
        });
  }

  private void orderAccepted() {
    circularFillableLoaders.setProgress(50);
    Tools.setViewVisibility(mTvAccepted, View.VISIBLE);
  }

  private void initViews() {
    circularFillableLoaders.setProgress(100);

    //simulateLoading(new ISimulatedLoadingFinished() {
    //  @Override public void finished() {
    //    showSecretCodeView();
    //  }
    //});


    /*// 2. Hide water / show code
    circularFillableLoaders.setVisibility(View.GONE);
    CodeLayout.setVisibility(View.VISIBLE);
    code.setText(getRandomCode());*/
  }

  private void showSecretCodeView() {
    Tools.setViewVisibility(mTvAccepted, View.GONE);
    circularFillableLoaders.setProgress(0);

    Animation fadeInCode = AnimationUtils.loadAnimation(this, R.anim.fade_in_anim);
    Animation fadeOutWater = AnimationUtils.loadAnimation(this, R.anim.fade_out_anim);
    fadeOutWater.setAnimationListener(new Animation.AnimationListener() {
      @Override public void onAnimationStart(Animation animation) {

      }

      @Override public void onAnimationEnd(Animation animation) {
        circularFillableLoaders.setVisibility(View.GONE);
      }

      @Override public void onAnimationRepeat(Animation animation) {

      }
    });

    circularFillableLoaders.startAnimation(fadeOutWater);

    CodeLayout.setVisibility(View.VISIBLE);
    CodeLayout.startAnimation(fadeInCode);

    code.setText(mOrder.secretCode);
  }

  private void simulateLoading(final ISimulatedLoadingFinished loadingFinished) {
    Thread t = new Thread(new Runnable() {
      @Override public void run() {

        for (int i = 0; i <= 100; i++) {
          final int finalI = i;
          runOnUiThread(new Runnable() {
            @Override public void run() {
              circularFillableLoaders.setProgress(finalI);

              Log.d(TAG, "run: progress = " + finalI);
              if (finalI == 100) {
                loadingFinished.finished();
              }
            }
          });

          try {
            Thread.sleep(50);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    });
    t.start();
  }

  private void initFAB() {
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show();
      }
    });
  }
}
