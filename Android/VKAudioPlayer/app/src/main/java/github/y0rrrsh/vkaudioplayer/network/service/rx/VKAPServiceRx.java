package github.y0rrrsh.vkaudioplayer.network.service.rx;

import java.util.List;
import java.util.Map;

import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItem;
import github.y0rrrsh.vkaudioplayer.models.AudioModel;
import github.y0rrrsh.vkaudioplayer.models.FriendModel;
import github.y0rrrsh.vkaudioplayer.models.GroupModel;
import github.y0rrrsh.vkaudioplayer.models.UserModel;
import github.y0rrrsh.vkaudioplayer.network.Callback;

/**
 * @author Artur Yorsh. 16.06.16.
 */
public interface VKAPServiceRx {

    void getAudios(Integer ownerId, Callback<List<AudioModel>> callback);

    void getNewAudios(Integer ownerId, Callback<List<AudioModel>> callback);

    void getNewAudios(int[] ids, Callback<Map<VkItem, List<AudioModel>>> callback);

    void getGroups(Callback<List<GroupModel>> callback);

    void getFriends(Callback<List<FriendModel>> callback);

    void getUserInfo(Integer id, Callback<UserModel> callback);

    void addAudio(Integer id, Integer ownerId, Callback<Integer> callback);

    void removeAudio(Integer id, Integer ownerId, Callback<Integer> callback);

    void restoreAudio(Integer id, Integer ownerId, Callback<AudioModel> callback);
}
