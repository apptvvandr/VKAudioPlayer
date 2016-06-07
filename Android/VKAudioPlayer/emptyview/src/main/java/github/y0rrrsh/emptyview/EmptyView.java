package github.y0rrrsh.emptyview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Artur Yorsh
 */
public class EmptyView extends LinearLayout {

    private ImageView imageNoData;
    private TextView textNoData;

    private Drawable image;
    private String message;

    public EmptyView(Context context, @DrawableRes int image, String message) {
        super(context);

        this.image = getResources().getDrawable(image);
        this.message = message;
        init();
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        obtainAttributes(context, attrs, 0);
        init();
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        obtainAttributes(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        obtainAttributes(context, attrs, defStyleAttr);
        init();
    }

    private void obtainAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.EmptyView, defStyleAttr, 0);
        image = array.getDrawable(R.styleable.EmptyView_ev_image);
        message = array.getString(R.styleable.EmptyView_ev_message);
        array.recycle();
    }

    private void init() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.empty_view, this, true);

        imageNoData = (ImageView) contentView.findViewById(R.id.image_no_data);
        textNoData = (TextView) contentView.findViewById(R.id.text_no_data);

        imageNoData.setImageDrawable(image);
        textNoData.setText(message);
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(@DrawableRes int image) {
        Drawable drawable = getResources().getDrawable(image);
        setImage(drawable);
    }

    public void setImage(Drawable image) {
        this.image = image;
        imageNoData.setImageDrawable(image);
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public void setMessage(String messageNoData) {
        this.message = messageNoData;
        textNoData.setText(messageNoData);
    }

    public void hide() {
        this.setVisibility(GONE);
    }

    public void show() {
        this.setVisibility(VISIBLE);
    }

    public boolean isVisible() {
        return this.getVisibility() == View.VISIBLE;
    }
}
