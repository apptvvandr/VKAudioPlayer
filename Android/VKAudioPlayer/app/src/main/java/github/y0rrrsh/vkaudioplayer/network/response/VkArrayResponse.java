package github.y0rrrsh.vkaudioplayer.network.response;

import java.util.List;

/**
 * @author Artur Yorsh. 08.06.16.
 *         <p>
 *         json response schema:
 *         response: [{
 *         id: 1,
 *         first_name: 'Pavel',
 *         last_name: 'Durov'
 *         }]
 *
 *         @see VkArray
 */
public class VkArrayResponse<T> extends VkResponse<List<T>> {
}
