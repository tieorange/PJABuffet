package tieorange.com.pjabuffet.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import tieorange.com.pjabuffet.R;
import tieorange.com.pjabuffet.pojo.api.Product;
import tieorange.com.pjabuffet.utils.Interfaces.IAllowedToRemove;

/**
 * Created by tieorange on 08/12/2016.
 */

public class DialogTools {
  public static MaterialDialog.Builder getRemoveProduct(final IAllowedToRemove iAllowedToRemove,
      Context context, Product product) {
    return new MaterialDialog.Builder(context).title(R.string.would_you_like_to_remove)
        .content(product.name)
        .positiveText(R.string.OK)
        .negativeText(R.string.No)
        .onPositive(new MaterialDialog.SingleButtonCallback() {
          @Override
          public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            iAllowedToRemove.remove();
          }
        });
  }
}
