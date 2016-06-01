package github.y0rrrsh.vkaudioplayer.receivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import github.y0rrrsh.vkaudioplayer.AudioPlayer;
import github.y0rrrsh.vkaudioplayer.AudioPlayer.AudioPlayerItem;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.activities.AudioPlayerActivity;
import github.y0rrrsh.vkaudioplayer.utils.VKAPPreferences;

/**
 * @author Artur Yorsh
 */
public class PlaybackReceiver extends BroadcastReceiver {

    public static final int NOTIFICATION_ID_PLAYER = 2417;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (AudioPlayer.ACTION_START.equals(action) || AudioPlayer.ACTION_PAUSE.equals(action)) {
            AudioPlayerItem currentItem = AudioPlayer.getInstance(context).getCurrentItem();
            Notification notification = buildNowPlayingNotification(context, currentItem);
            getNotificationService(context).notify(NOTIFICATION_ID_PLAYER, notification);
            return;
        }

        if (AudioPlayer.ACTION_COMPLETE.equals(action)) {
            AudioPlayer player = AudioPlayer.getInstance(context);
            if (VKAPPreferences.isRepeatEnabled(context)) {
                player.play();
                return;
            }
            player.playNext();
        }
    }

    private Notification buildNowPlayingNotification(Context context, AudioPlayerItem currentItem) {
        RemoteViews widgetLayout = new RemoteViews(context.getPackageName(), R.layout.widget_audio_player);
        widgetLayout.setTextViewText(R.id.text_widget_artist, currentItem.getArtist());
        widgetLayout.setTextViewText(R.id.text_widget_name, currentItem.getName());
        int playButtonRes = AudioPlayer.getInstance(context).isPlaying() ? R.drawable.ic_pause_black_24dp : R.drawable.ic_play_arrow_black_24dp;
        widgetLayout.setImageViewResource(R.id.btn_widget_pause, playButtonRes);

        Intent playerActivityStarter = new Intent(context, AudioPlayerActivity.class);
        PendingIntent pendingPlayerStarter = PendingIntent.getActivity(context, 0, playerActivityStarter, PendingIntent.FLAG_UPDATE_CURRENT);
        widgetLayout.setOnClickPendingIntent(R.id.container_widget, pendingPlayerStarter);

        Intent playPauseIntent = new Intent(WidgetControlReceiver.ACTION_PLAY);
        PendingIntent pendingPauseIntent = PendingIntent.getBroadcast(context, 0, playPauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        widgetLayout.setOnClickPendingIntent(R.id.btn_widget_pause, pendingPauseIntent);

        Intent playNextIntent = new Intent(WidgetControlReceiver.ACTION_NEXT);
        PendingIntent pendingPlayNext = PendingIntent.getBroadcast(context, 0, playNextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        widgetLayout.setOnClickPendingIntent(R.id.btn_widget_next, pendingPlayNext);

        return new Notification.Builder(context)
                .setContent(widgetLayout)
                .setContentTitle(currentItem.getArtist())
                .setContentText(currentItem.getName())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true)
                .build();
    }

    private NotificationManager getNotificationService(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
