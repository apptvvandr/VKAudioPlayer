package github.y0rrrsh.vkaudioplayer.models.mapper.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Artur Yorsh. 30.05.16.
 */
public abstract class ListMapper<M1, M2> implements Mapper<List<M1>, List<M2>> {

    @Override
    public List<M2> map(List<M1> values) {
        List<M2> mappedValues = new ArrayList<>(values.size());
        for (M1 value : values) {
            M2 mappedValue = mapValue(value);
            mappedValues.add(mappedValue);
        }
        return mappedValues;
    }

    protected abstract M2 mapValue(M1 value);
}
