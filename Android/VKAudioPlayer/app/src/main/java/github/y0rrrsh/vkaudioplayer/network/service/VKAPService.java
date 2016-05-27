package github.y0rrrsh.vkaudioplayer.network.service;

import github.y0rrrsh.vkaudioplayer.models.Audio;
import github.y0rrrsh.vkaudioplayer.models.Friend;
import github.y0rrrsh.vkaudioplayer.models.Group;
import github.y0rrrsh.vkaudioplayer.network.service.VkApi.VkArrayCallback;

/**
 * @author Artur Yorsh
 */
public interface VKAPService {

    void getAudios(String userId, VkArrayCallback<Audio> callback);

    void getGroups(VkArrayCallback<Group> callback);

    void getFriends(VkArrayCallback<Friend> callback);
}
