package tieorange.com.pjabuffet.activities.ui.sections_adapter;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import tieorange.com.pjabuffet.R;

/**
 * Created by tieorange on 19/12/2016.
 */

public class SectionItemViewHolder extends ChildViewHolder {
  @BindView(R.id.sectionItemText) TextView mTextView;

  public SectionItemViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);

  }
}
