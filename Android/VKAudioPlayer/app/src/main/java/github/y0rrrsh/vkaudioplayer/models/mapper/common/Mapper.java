package github.y0rrrsh.vkaudioplayer.models.mapper.common;

/**
 * @author Artur Yorsh. 30.05.16.
 */
public interface Mapper<M1, M2> {

    M2 map(M1 value);

}