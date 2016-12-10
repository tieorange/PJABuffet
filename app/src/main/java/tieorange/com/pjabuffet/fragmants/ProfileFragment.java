package tieorange.com.pjabuffet.fragmants;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.codetail.animation.arcanimator.ArcAnimator;
import io.codetail.animation.arcanimator.Side;
import tieorange.com.pjabuffet.R;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

  @BindView(R.id.buttonLearnAnimation) Button button;
  @BindView(R.id.rootLayout) RelativeLayout rootLayout;

  public ProfileFragment() {
    // Required empty public constructor
  }

  public static ProfileFragment newInstance() {
    Bundle args = new Bundle();
    ProfileFragment fragment = new ProfileFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_profile, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @OnClick(R.id.buttonLearnAnimation) public void onClickButton() {
    experiment();
  }

  private void experiment() {
    int widthPixels = getContext().getResources().getDisplayMetrics().widthPixels;
    int heightPixels = getContext().getResources().getDisplayMetrics().heightPixels;

    int endX = widthPixels / 2;
    int endY = heightPixels;
    ArcAnimator arcAnimator =
        ArcAnimator.createArcAnimator(button, endX, endY, 100, Side.LEFT).setDuration(3000);

    Animation animationFadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out_anim);
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
}
