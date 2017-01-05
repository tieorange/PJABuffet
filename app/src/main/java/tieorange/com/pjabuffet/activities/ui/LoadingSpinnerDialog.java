package tieorange.com.pjabuffet.activities.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by root on 1/5/17.
 */

public class LoadingSpinnerDialog extends DialogFragment {


    public FragmentManager mSupportFragmentManager;
    private Context mContext;

    public LoadingSpinnerDialog() {
    }


    public static LoadingSpinnerDialog newInstance(FragmentManager supportFragmentManager) {
        LoadingSpinnerDialog fragment = new LoadingSpinnerDialog();
        fragment.mSupportFragmentManager = supportFragmentManager;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        ProgressDialog _dialog = new ProgressDialog(getActivity());
        this.setStyle(STYLE_NO_TITLE, getTheme()); // You can use styles or inflate a view
        _dialog.setMessage("Loading..."); // set your messages if not inflated from XML

        _dialog.setCancelable(false);

        return _dialog;
    }

    public void show() {
        this.show(mSupportFragmentManager, "lading");
    }
}