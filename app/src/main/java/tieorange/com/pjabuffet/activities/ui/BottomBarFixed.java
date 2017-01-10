package tieorange.com.pjabuffet.activities.ui;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;

/**
 * Created by root on 1/10/17.
 */

public class BottomBarFixed extends BottomBar {
    public BottomBarFixed(Context context) {
        super(context);
    }

    public BottomBarFixed(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        // HACK: in order to avoid a crash in onRestoreInstanceState when badges are present
        // remove badges here
        for (int i = 0; i < getTabCount(); i++) {
            BottomBarTab tab = getTabAtPosition(i);
            tab.removeBadge();
        }

        return super.onSaveInstanceState();
    }
}
