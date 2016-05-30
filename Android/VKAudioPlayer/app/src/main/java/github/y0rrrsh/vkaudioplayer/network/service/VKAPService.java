package github.y0rrrsh.vkaudioplayer.network.service;

import github.y0rrrsh.vkaudioplayer.models.dto.AudioDTO;
import github.y0rrrsh.vkaudioplayer.models.dto.Friend;
import github.y0rrrsh.vkaudioplayer.models.dto.Group;
import github.y0rrrsh.vkaudioplayer.vkapi.VKApi.VkArrayCallback;

/**
 * @author Artur Yorsh
 */
public interface VKAPService {

    void getAudios(String userId, VkArrayCallback<AudioDTO> callback);

    void getGroups(VkArrayCallback<Group> callback);

    void getFriends(VkArrayCallback<Friend> callback);
}
