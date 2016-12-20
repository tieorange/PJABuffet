package tieorange.com.pjabuffet.activities.ui.sections_adapter;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import tieorange.com.pjabuffet.R;
import tieorange.com.pjabuffet.pojo.OrderSection;

/**
 * Created by tieorange on 19/12/2016.
 */

public class SectionViewHolder extends ParentViewHolder {
  @BindView(R.id.sectionText) TextView sectionText;
  @BindView(R.id.ordersAmount) TextView amount;

  public SectionViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public void onBind(OrderSection section) {
    if (section == null || section.getItems() == null) return;

    sectionText.setText(section.getTitle());

    final int size = section.getItems().size();
    amount.setText("(" + size + ")");
  }

  @Override public boolean shouldItemViewClickToggleExpansion() {
    return false;
  }
}
