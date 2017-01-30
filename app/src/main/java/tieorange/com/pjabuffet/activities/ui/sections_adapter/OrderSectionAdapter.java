package tieorange.com.pjabuffet.activities.ui.sections_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import java.util.ArrayList;
import java.util.List;
import tieorange.com.pjabuffet.R;
import tieorange.com.pjabuffet.pojo.OrderSection;
import tieorange.com.pjabuffet.pojo.api.Order;

/**
 * Created by tieorange on 19/12/2016.
 */

public class OrderSectionAdapter
    extends ExpandableRecyclerAdapter<SectionViewHolder, SectionItemViewHolder> {

  private final LayoutInflater mInflater;
  private Context mContext;

  public OrderSectionAdapter(Context context, @NonNull List<Order> parentItemList) {
    super(getOrdersSections(parentItemList));
    mContext = context;
    mInflater = LayoutInflater.from(context);
  }

  private static List<OrderSection> getOrdersSections(List<Order> ordersList) {
    List<OrderSection> sectionsList = new ArrayList<>();
    List<Order> current = new ArrayList<>();
    List<Order> past = new ArrayList<>();

    for (Order order : ordersList) {
      if (order.isStatusReady()) {
        past.add(order);
      } else {
        current.add(order);
      }
    }

    if (current.size() > 0) {
      sectionsList.add(new OrderSection("Current orders", current));
    }
    if (past.size() > 0) sectionsList.add(new OrderSection("Past", past));

    return sectionsList;
  }

  @Override public SectionViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
    final View view = mInflater.inflate(R.layout.item_section, parentViewGroup, false);
    return new SectionViewHolder(view);
  }

  @Override public SectionItemViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
    final View view = mInflater.inflate(R.layout.item_section_item, childViewGroup, false);
    return new SectionItemViewHolder(view);
  }

  @Override public void onBindParentViewHolder(SectionViewHolder holder, int position,
      ParentListItem parentListItem) {
    final OrderSection section = (OrderSection) parentListItem;
    holder.onBind(section);
  }

  @Override public void onBindChildViewHolder(SectionItemViewHolder holder, int position,
      Object childListItem) {
    final Order order = (Order) childListItem;
    holder.onBind(order);
  }
}
