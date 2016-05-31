package github.y0rrrsh.vkaudioplayer.network.service;

import github.y0rrrsh.vkaudioplayer.models.dto.AudioDTO;
import github.y0rrrsh.vkaudioplayer.models.dto.FriendDTO;
import github.y0rrrsh.vkaudioplayer.models.dto.GroupDTO;
import github.y0rrrsh.vkaudioplayer.vkapi.VKApi.VkArrayCallback;
import github.y0rrrsh.vkaudioplayer.vkapi.VKApi.VkCallback;

/**
 * @author Artur Yorsh
 */
public interface VKAPService {

    void getAudios(String userId, VkArrayCallback<AudioDTO> callback);

    void getGroups(VkArrayCallback<GroupDTO> callback);

    void getFriends(VkArrayCallback<FriendDTO> callback);

    void addAudio(Integer id, Integer ownerId, VkCallback<Integer> callback);

    void removeAudio(Integer id, Integer ownerId, VkCallback<Integer> callback);
}
