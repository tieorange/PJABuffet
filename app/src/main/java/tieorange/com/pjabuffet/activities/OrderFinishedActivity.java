package tieorange.com.pjabuffet.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;
import tieorange.com.pjabuffet.MyApplication;
import tieorange.com.pjabuffet.R;
import tieorange.com.pjabuffet.pojo.api.Order;
import tieorange.com.pjabuffet.utils.NotificationHandler;
import tieorange.com.pjabuffet.utils.Tools;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class OrderFinishedActivity extends AppCompatActivity {

  private static final String TAG = OrderFinishedActivity.class.getCanonicalName();

  @InjectExtra String mOrderKey;
  @InjectExtra @Nullable Integer mNotificationId;

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.circularFillableLoaders) CircularFillableLoaders circularFillableLoaders;
  @BindView(R.id.tvYourCode) TextView tvYourCode;
  @BindView(R.id.code) TextView code;
  @BindView(R.id.codeLayout) ConstraintLayout codeLayout;
  @BindView(R.id.rootLayout) ConstraintLayout rootLayout;
  @BindView(R.id.fab) FloatingActionButton fab;
  @BindView(R.id.tvAccepted) TextView mTvAccepted;
  @BindView(R.id.qrCode) ImageView qrCode;

  private Order mOrder;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_order_finished);
    ButterKnife.bind(this);
    Dart.inject(this);
    setSupportActionBar(toolbar);

    initViews();
    initFirebase();
    checkExtras();
  }

  private void checkExtras() {
    if (mNotificationId != null) {
      NotificationHandler.dismissNotification(OrderFinishedActivity.this, mNotificationId);
    }
  }

  @Override public void onBackPressed() {
    super.onBackPressed();
    Intent intent = Henson.with(OrderFinishedActivity.this).gotoMainActivity().build();
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
  }

  private void initFirebase() {
    if (mOrderKey == null) {
      Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
      return;
    }
    MyApplication.sReferenceOrders.child(mOrderKey).addValueEventListener(new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot dataSnapshot) {
        Order value = dataSnapshot.getValue(Order.class);
        if (value == null) return;

        mOrder = value;
        if (value.isStatusAccepted()) {
          orderAccepted();
        } else if (value.isStatusReady()) {
          showSecretCodeView();
        }
      }

      @Override public void onCancelled(DatabaseError databaseError) {
        Toast.makeText(OrderFinishedActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void orderAccepted() {
    circularFillableLoaders.setProgress(50);
    Tools.setViewVisibility(mTvAccepted, View.VISIBLE);
  }

  private void initViews() {
    initFAB();

    circularFillableLoaders.setProgress(100);

    //simulateLoading(new ISimulatedLoadingFinished() {
    //  @Override public void finished() {
    //    showSecretCodeView();
    //  }
    //});


    /*// 2. Hide water / show code
    circularFillableLoaders.setVisibility(View.GONE);
    codeLayout.setVisibility(View.VISIBLE);
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

    codeLayout.setVisibility(View.VISIBLE);
    codeLayout.startAnimation(fadeInCode);

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

  Bitmap encodeAsBitmap(String str) throws WriterException {
    int WIDTH = 300;
    BitMatrix result;
    try {
      result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, WIDTH, WIDTH, null);
    } catch (IllegalArgumentException iae) {
      // Unsupported format
      return null;
    }
    int w = result.getWidth();
    int h = result.getHeight();
    int[] pixels = new int[w * h];
    for (int y = 0; y < h; y++) {
      int offset = y * w;
      for (int x = 0; x < w; x++) {
        pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
      }
    }
    Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    bitmap.setPixels(pixels, 0, WIDTH, 0, 0, w, h);
    return bitmap;
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
