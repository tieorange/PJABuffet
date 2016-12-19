package tieorange.com.pjabuffet.pojo;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import java.util.List;
import tieorange.com.pjabuffet.pojo.api.Order;

/**
 * Created by tieorange on 19/12/2016.
 */

public class OrderSection extends ExpandableGroup<Order> {

  private final String mTitle;
  private final List<Order> mItems;

  public OrderSection(String title, List<Order> items) {
    super(title, items);

    mTitle = title;
    mItems = items;
  }


}
