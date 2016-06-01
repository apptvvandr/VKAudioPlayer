package github.y0rrrsh.playbackcontrolview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
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

    public ImageButton btnPrevious;
    public ImageButton btnPlay;
    public ImageButton btnNext;
    public ImageButton btnShuffle;
    public ImageButton btnRepeat;
    public SeekBar seekProgress;

    private PlaybackActionHandler actionHandler;

    public PlaybackControlView(Context context) {
        super(context);
        init();
    }

    public PlaybackControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlaybackControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PlaybackControlView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        View v = LayoutInflater.from(this.getContext()).inflate(R.layout.view_player_control, this, true);

        btnPrevious = (ImageButton) v.findViewById(R.id.btn_player_previous);
        btnPlay = (ImageButton) v.findViewById(R.id.btn_player_play);
        btnNext = (ImageButton) v.findViewById(R.id.btn_player_next);
        btnShuffle = (ImageButton) v.findViewById(R.id.btn_player_shuffle);
        btnRepeat = (ImageButton) v.findViewById(R.id.btn_player_repeat);
        seekProgress = (SeekBar) v.findViewById(R.id.seek_player_progress);

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
