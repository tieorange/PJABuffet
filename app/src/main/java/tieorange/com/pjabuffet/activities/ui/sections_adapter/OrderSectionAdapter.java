package tieorange.com.pjabuffet.activities.ui.sections_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import java.util.List;
import tieorange.com.pjabuffet.R;
import tieorange.com.pjabuffet.pojo.api.Order;

/**
 * Created by tieorange on 19/12/2016.
 */

public class OrderSectionAdapter
    extends ExpandableRecyclerViewAdapter<SectionViewHolder, SectionItemViewHolder> {

  private Context mContext;

  public OrderSectionAdapter(Context context, List<? extends ExpandableGroup> groups) {
    super(groups);
    mContext = context;
  }

  @Override public SectionViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
    final View view = LayoutInflater.from(mContext).inflate(R.layout.item_section, parent, false);
    return new SectionViewHolder(view);
  }

  @Override public SectionItemViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
    final View view =
        LayoutInflater.from(mContext).inflate(R.layout.item_section_item, parent, false);
    return new SectionItemViewHolder(view);
  }

  @Override public void onBindChildViewHolder(SectionItemViewHolder holder, int flatPosition,
      ExpandableGroup group, int childIndex) {
    final Order order = (Order) group.getItems().get(childIndex);
    holder.mTextView.setText(order.key);
  }

  @Override public void onBindGroupViewHolder(SectionViewHolder holder, int flatPosition,
      ExpandableGroup group) {
    holder.text.setText(group.getTitle());
  }
}
