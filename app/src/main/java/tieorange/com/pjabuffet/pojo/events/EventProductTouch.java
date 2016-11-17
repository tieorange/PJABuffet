package tieorange.com.pjabuffet.pojo.events;

/**
 * Created by tieorange on 20/10/2016.
 */
public class EventProductTouch {
  public final float x;
  public final float y;

  public EventProductTouch(float x, float y) {
    this.x = x;
    this.y = y;
  }

  @Override public String toString() {
    return "EventProductTouch{" + "x=" + x + ", y=" + y + '}';
  }
}
