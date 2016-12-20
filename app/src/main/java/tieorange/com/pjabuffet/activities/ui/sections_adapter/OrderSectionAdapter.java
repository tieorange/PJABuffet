package tieorange.com.pjabuffet.activities.ui.sections_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import java.util.List;
import tieorange.com.pjabuffet.R;
import tieorange.com.pjabuffet.pojo.OrderSection;
import tieorange.com.pjabuffet.pojo.api.Order;

/**
 * Created by tieorange on 19/12/2016.
 */

public class OrderSectionAdapter
    extends ExpandableRecyclerAdapter<SectionViewHolder, SectionItemViewHolder> {

  private final LayoutInflater mInflator;
  private Context mContext;

  public OrderSectionAdapter(Context context,
      @NonNull List<? extends ParentListItem> parentItemList) {
    super(parentItemList);
    mContext = context;
    mInflator = LayoutInflater.from(context);
  }

  @Override public SectionViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
    final View view = mInflator.inflate(R.layout.item_section, parentViewGroup, false);
    return new SectionViewHolder(view);
  }

  @Override public SectionItemViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
    final View view = mInflator.inflate(R.layout.item_section_item, childViewGroup, false);
    return new SectionItemViewHolder(view);
  }

  @Override public void onBindParentViewHolder(SectionViewHolder holder, int position,
      ParentListItem parentListItem) {
    final OrderSection section = (OrderSection) parentListItem;
    holder.text.setText(section.getTitle());
  }

  @Override public void onBindChildViewHolder(SectionItemViewHolder holder, int position,
      Object childListItem) {
    final Order order = (Order) childListItem;
    holder.mTextView.setText(order.key);
  }
}
