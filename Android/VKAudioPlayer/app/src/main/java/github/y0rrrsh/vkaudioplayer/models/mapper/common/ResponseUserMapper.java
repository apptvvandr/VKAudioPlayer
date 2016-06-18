package github.y0rrrsh.vkaudioplayer.models.mapper.common;

import github.y0rrrsh.vkaudioplayer.models.UserModel;
import github.y0rrrsh.vkaudioplayer.models.dto.UserDTO;

/**
 * @author Artur Yorsh. 14.06.16.
 */
public class ResponseUserMapper implements Mapper<UserDTO,UserModel> {

    @Override
    public UserModel map(UserDTO value) {
        return new UserModel(value.getId(), value.getFirstName(), value.getLastName(),
                value.getPhotoMax());
    }
}
