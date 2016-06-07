package github.y0rrrsh.vkaudioplayer.network.service;

import github.y0rrrsh.vkapi.VKApi.VKArrayCallback;
import github.y0rrrsh.vkapi.VKApi.VKCallback;
import github.y0rrrsh.vkaudioplayer.models.dto.AudioDTO;
import github.y0rrrsh.vkaudioplayer.models.dto.FriendDTO;
import github.y0rrrsh.vkaudioplayer.models.dto.GroupDTO;

/**
 * @author Artur Yorsh
 */
public interface VKAPService {

    void getAudios(String userId, VKArrayCallback<AudioDTO> callback);

    void getGroups(VKArrayCallback<GroupDTO> callback);

    void getFriends(VKArrayCallback<FriendDTO> callback);

    void addAudio(Integer id, Integer ownerId, VKCallback<Integer> callback);

    void removeAudio(Integer id, Integer ownerId, VKCallback<Integer> callback);

    void restoreAudio(Integer id, Integer ownerId, VKCallback<AudioDTO> callback);
}
