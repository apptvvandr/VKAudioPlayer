package github.y0rrrsh.vkaudioplayer.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

/**
 * @author Artur Yorsh. 13.06.16.
 */
public class SimpleAlertDialog extends AlertDialog {

    private OnPositiveClick onPositiveClick;
    private OnNegativeClick onNegativeClick;

    public SimpleAlertDialog(Context context, String title, String message) {
        this(context, title, message, null, null, null, null);
    }

    public SimpleAlertDialog(Context context, String title, String message,
                             String btnPositive, OnPositiveClick onPositiveClick) {
        this(context, title, message, btnPositive, onPositiveClick, null, null);
    }

    public SimpleAlertDialog(Context context, @StringRes int title, @StringRes int message,
                             String btnPositive, OnPositiveClick onPositiveClick,
                             String btnNegative, OnNegativeClick onNegativeClick) {
        this(context, context.getString(title), context.getString(message),
                btnPositive, onPositiveClick,
                btnNegative, onNegativeClick);
    }

    public SimpleAlertDialog(Context context, @StringRes int title, @StringRes int message,
                             @StringRes int btnPositive, OnPositiveClick onPositiveClick,
                             @StringRes int btnNegative, OnNegativeClick onNegativeClick) {
        this(context, context.getString(title), context.getString(message),
                context.getString(btnPositive), onPositiveClick,
                context.getString(btnNegative), onNegativeClick);
    }

    public SimpleAlertDialog(Context context, String title, String message,
                             String btnPositive, OnPositiveClick onPositiveClick,
                             String btnNegative, OnNegativeClick onNegativeClick) {
        super(context);
        this.onPositiveClick = onPositiveClick;
        this.onNegativeClick = onNegativeClick;

        setTitle(title);
        setMessage(message);
        setButton(BUTTON_POSITIVE, btnPositive, (dialog, which) -> {
            if (onPositiveClick != null) onPositiveClick.onClick();
        });
        setButton(BUTTON_NEGATIVE, btnNegative, (dialog, which) -> {
            if (onNegativeClick != null) onNegativeClick.onClick();
        });
    }

    public interface OnPositiveClick {
        void onClick();
    }

    public interface OnNegativeClick {
        void onClick();
    }
}
