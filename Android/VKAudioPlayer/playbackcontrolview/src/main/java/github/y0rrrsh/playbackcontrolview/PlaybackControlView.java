package github.y0rrrsh.playbackcontrolview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;

/**
 * @author Artur Yorsh. 30.05.16.
 */
public class PlaybackControlView extends LinearLayout {

    private ImageButton btnPrevious;
    private ImageButton btnPlay;
    private ImageButton btnNext;
    private ImageButton btnShuffle;
    private ImageButton btnRepeat;
    private SeekBar seekProgress;

    private PlaybackActionHandler actionHandler;

    private int btnBackground;
    private int seekThumb;
    private int seekBackground;

    public PlaybackControlView(Context context) {
        super(context);
        init();
    }

    public PlaybackControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        obtainAttributes(context, attrs, 0);
        init();
    }

    public PlaybackControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttributes(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PlaybackControlView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        obtainAttributes(context, attrs, 0);
        init();
    }

    private void obtainAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PlaybackControlView, defStyleAttr, 0);
        btnBackground = array.getResourceId(R.styleable.PlaybackControlView_pcv_button_background, R.drawable.bg_button_circle);
        seekBackground = array.getResourceId(R.styleable.PlaybackControlView_pcv_seek_background, 0);
        seekThumb = array.getResourceId(R.styleable.PlaybackControlView_pcv_seek_thumb, 0);
        array.recycle();
    }

    private void init() {
        View v = LayoutInflater.from(this.getContext()).inflate(R.layout.view_player_control, this, true);

        btnPrevious = (ImageButton) v.findViewById(R.id.btn_player_previous);
        btnPlay = (ImageButton) v.findViewById(R.id.btn_player_play);
        btnNext = (ImageButton) v.findViewById(R.id.btn_player_next);
        btnShuffle = (ImageButton) v.findViewById(R.id.btn_player_shuffle);
        btnRepeat = (ImageButton) v.findViewById(R.id.btn_player_repeat);
        seekProgress = (SeekBar) v.findViewById(R.id.seek_player_progress);

        this.setButtonBackground(btnBackground);
        if (seekBackground != 0) {
            this.setSeekBackground(seekThumb);
        }
        if (seekThumb != 0) {
            this.setSeekThumb(seekThumb);
        }

        btnPrevious.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v1) {
                if (actionHandler == null) return;
                actionHandler.onPreviousClicked();

            }
        });
        btnPlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v1) {
                if (actionHandler == null) return;
                actionHandler.onPlayClicked();
            }
        });
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v1) {
                if (actionHandler == null) return;
                actionHandler.onNextClicked();
            }
        });
        btnShuffle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v1) {
                if (actionHandler == null) return;
                actionHandler.onShuffleClicked();
            }
        });
        btnRepeat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v1) {
                if (actionHandler == null) return;
                actionHandler.onRepeatClicked();
            }
        });
        seekProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (actionHandler == null) return;

                int currentValue = seekBar.getProgress();
                actionHandler.onSeekDragged(currentValue);
            }
        });
    }

    public void setButtonBackground(@DrawableRes int btnBackground) {
        this.btnBackground = btnBackground;

        btnPrevious.setBackgroundResource(btnBackground);
        btnPlay.setBackgroundResource(btnBackground);
        btnNext.setBackgroundResource(btnBackground);
        btnShuffle.setBackgroundResource(btnBackground);
        btnRepeat.setBackgroundResource(btnBackground);
    }

    public void setSeekBackground(@DrawableRes int seekBackground) {
        this.seekBackground = seekBackground;
        seekProgress.setProgressDrawable(getResources().getDrawable(seekBackground));
    }

    public void setSeekThumb(@DrawableRes int seekThumb) {
        this.seekThumb = seekThumb;
        seekProgress.setThumb(getResources().getDrawable(seekThumb));
    }

    public void setPreviousButtonIcon(@DrawableRes int icon) {
        setButtonIcon(btnPrevious, icon);
    }

    public void setPlayButtonIcon(@DrawableRes int icon) {
        setButtonIcon(btnPlay, icon);
    }

    public void setNextButtonIcon(@DrawableRes int icon) {
        setButtonIcon(btnNext, icon);
    }

    public void setRepeatButtonIcon(@DrawableRes int icon) {
        setButtonIcon(btnRepeat, icon);
    }

    public void setShuffleButtonIcon(@DrawableRes int icon) {
        setButtonIcon(btnShuffle, icon);
    }

    public void setPreviousButtonTintColor(@ColorRes int colorRes) {
        setButtonTintColor(btnPrevious, colorRes);
    }

    public void setPlayButtonTintColor(@ColorRes int colorRes) {
        setButtonTintColor(btnPlay, colorRes);
    }

    public void setNextButtonTintColor(@ColorRes int colorRes) {
        setButtonTintColor(btnNext, colorRes);
    }

    public void setRepeatButtonTintColor(@ColorRes int colorRes) {
        setButtonTintColor(btnRepeat, colorRes);
    }

    public void setShuffleButtonTintColor(@ColorRes int colorRes) {
        setButtonTintColor(btnShuffle, colorRes);
    }

    public void setSeekMaxProgress(int maxProgress) {
        seekProgress.setMax(maxProgress);
    }

    public void setSeekCurrentProgress(int progress) {
        seekProgress.setProgress(progress);
    }

    public void setSeekSecondaryProgress(int progress) {
        seekProgress.setSecondaryProgress(progress);
    }

    private void setButtonIcon(@NonNull ImageButton button, @DrawableRes int icon) {
        button.setImageDrawable(getResources().getDrawable(icon));
    }

    private void setButtonTintColor(@NonNull ImageButton button, @ColorRes int colorRes) {
        button.setColorFilter(getResources().getColor(colorRes));
    }

    public void setActionHandler(PlaybackActionHandler actionHandler) {
        this.actionHandler = actionHandler;
    }

    public interface PlaybackActionHandler {

        void onPreviousClicked();

        void onPlayClicked();

        void onNextClicked();

        void onShuffleClicked();

        void onRepeatClicked();

        void onSeekDragged(int currentValue);

    }
}
