package github.y0rrrsh.vkaudioplayer.network.service;

import github.y0rrrsh.vkapi.VKApi.VKArrayCallback;
import github.y0rrrsh.vkapi.VKApi.VKCallback;
import github.y0rrrsh.vkaudioplayer.models.AudioModel;
import github.y0rrrsh.vkaudioplayer.models.FriendModel;
import github.y0rrrsh.vkaudioplayer.models.GroupModel;
import github.y0rrrsh.vkaudioplayer.models.UserModel;
import github.y0rrrsh.vkaudioplayer.models.dto.AudioDTO;
import github.y0rrrsh.vkaudioplayer.models.dto.FriendDTO;
import github.y0rrrsh.vkaudioplayer.models.dto.GroupDTO;
import github.y0rrrsh.vkaudioplayer.models.dto.UserDTO;

/**
 * @author Artur Yorsh
 */
public interface VKAPService {

    void getAudios(Integer userId, VKArrayCallback<AudioModel> callback);

    void getGroups(VKArrayCallback<GroupModel> callback);

    void getFriends(VKArrayCallback<FriendModel> callback);

    void addAudio(Integer id, Integer ownerId, VKCallback<Integer> callback);

    void removeAudio(Integer id, Integer ownerId, VKCallback<Integer> callback);

    void restoreAudio(Integer id, Integer ownerId, VKCallback<AudioModel> callback);

    void getUserInfo(Integer id, VKCallback<UserModel> callback);
}
