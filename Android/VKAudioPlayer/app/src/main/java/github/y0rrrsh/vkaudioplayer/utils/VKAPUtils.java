package github.y0rrrsh.vkaudioplayer.utils;

import android.content.Context;

import github.y0rrrsh.vkaudioplayer.AudioPlayer;
import github.y0rrrsh.vkaudioplayer.models.AudioModel;
import github.y0rrrsh.vkaudioplayer.vkapi.VKApi;

/**
 * @author Artur Yorsh. 03.06.16.
 */
public class VKAPUtils {

    public static boolean canBeAddedOrRestored(Context context, AudioModel audio) { ;
        return !userIsOwner(audio) || canBeRestored(context, audio);
    }

    public static boolean canBeRestored(Context context, AudioModel audio) {
        return audio.getId() == VKAPPreferences.getRestoreAudioId(context);
    }

    public static boolean userIsOwner(AudioModel audio) {
        return audio.getOwnerId() == VKApi.USER_ID.intValue();
    }
}
