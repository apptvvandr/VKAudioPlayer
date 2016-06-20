package github.y0rrrsh.vkaudioplayer.models.mapper;

import github.y0rrrsh.vkaudioplayer.models.GroupModel;
import github.y0rrrsh.vkaudioplayer.models.dto.GroupDTO;
import github.y0rrrsh.vkaudioplayer.models.mapper.common.ListMapper;

/**
 * @author Artur Yorsh. 09.06.16.
 */
public class ResponseGroupsMapper extends ListMapper<GroupDTO, GroupModel> {

    @Override
    protected GroupModel mapValue(GroupDTO value) {
        return new GroupModel(value.getId(),
                value.getName(),
                value.getPhotoMaxOrig());
    }
}
