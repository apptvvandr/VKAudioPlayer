package github.y0rrrsh.vkaudioplayer.models.mapper;

import github.y0rrrsh.vkaudioplayer.models.AudioModel;
import github.y0rrrsh.vkaudioplayer.models.dto.AudioDTO;
import github.y0rrrsh.vkaudioplayer.models.mapper.common.ListMapper;

/**
 * @author Artur Yorsh. 30.05.16.
 */
public class ResponseAudioMapper extends ListMapper<AudioDTO, AudioModel> {

    @Override
    protected AudioModel mapValue(AudioDTO value) {
        return new AudioModel(value.getId(), value.getOwnerId(),
                value.getUrl(), value.getDuration(), value.getArtist(), value.getTitle(),
                value.getDate());
    }
}
