package tieorange.com.pjabuffet.pojo;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import java.util.List;
import tieorange.com.pjabuffet.pojo.api.Order;

/**
 * Created by tieorange on 19/12/2016.
 */

public class OrderSection implements ParentListItem {

  private String mTitle;
  private List<Order> mItems;

  public OrderSection(String title, List<Order> items) {
    mTitle = title;
    mItems = items;
  }

  @Override public List<?> getChildItemList() {
    return mItems;
  }

  @Override public boolean isInitiallyExpanded() {
    return true;
  }

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String title) {
    mTitle = title;
  }

  public List<Order> getItems() {
    return mItems;
  }

  public void setItems(List<Order> items) {
    mItems = items;
  }
}
