package tieorange.com.pjabuffet.activities.ui.sections_adapter;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import tieorange.com.pjabuffet.R;

/**
 * Created by tieorange on 19/12/2016.
 */

public class SectionViewHolder extends ParentViewHolder {
  @BindView(R.id.sectionText) TextView text;

  public SectionViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }
}
