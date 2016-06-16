package github.y0rrrsh.vkaudioplayer.network.service.rx;

import java.util.List;
import java.util.Map;

import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItem;
import github.y0rrrsh.vkaudioplayer.models.AudioModel;
import github.y0rrrsh.vkaudioplayer.network.Callback;

/**
 * @author Artur Yorsh. 16.06.16.
 */
public interface VKAPServiceRx {

    void getAudios(Integer userId, Callback<List<AudioModel>> callback);

    void getNewAudios(Integer userId, Callback<List<AudioModel>> callback);

    void getNewAudios(int[] ids, Callback<Map<VkItem, List<AudioModel>>> callback);
}
