package github.y0rrrsh.vkaudioplayer.models.mapper;

import github.y0rrrsh.vkaudioplayer.models.FriendModel;
import github.y0rrrsh.vkaudioplayer.models.dto.FriendDTO;
import github.y0rrrsh.vkaudioplayer.models.mapper.common.ListMapper;

/**
 * @author Artur Yorsh. 09.06.16.
 */
public class ResponseFriendsMapper extends ListMapper<FriendDTO, FriendModel> {

    @Override
    protected FriendModel mapValue(FriendDTO value) {
        return new FriendModel(value.getId(),
                value.getFirstName(),
                value.getLastName(),
                value.getPhoto200());
    }
}
