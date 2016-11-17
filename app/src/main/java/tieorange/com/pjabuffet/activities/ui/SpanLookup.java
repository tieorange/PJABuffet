package tieorange.com.pjabuffet.activities.ui;

/**
 * Created by tieorange on 15/10/2016.
 */
public interface SpanLookup {
  /**
   * @return number of spans in a row
   */
  int getSpanCount();

  /**
   * @return start span for the item at the given adapter position
   */
  int getSpanIndex(int itemPosition);

  /**
   * @return number of spans the item at the given adapter position occupies
   */
  int getSpanSize(int itemPosition);
}
