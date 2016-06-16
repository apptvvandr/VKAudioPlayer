package github.y0rrrsh.vkaudioplayer;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.y0rrrsh.vkaudioplayer.database.syncitem.SyncItemDB;
import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItem;
import github.y0rrrsh.vkaudioplayer.models.AudioModel;

/**
 * @author Artur Yorsh. 16.06.16.
 */
public class NewAudiosService extends IntentService {

    private static final String TAG = "NewAudiosService";

    private Map<VkItem, List<AudioModel>> newAudiosMap = new HashMap<>();

    public NewAudiosService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: ");
        int[] syncItemIds = SyncItemDB.getInstance().getAllIds();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, NewAudiosService.class);
        context.startService(starter);
    }
}
