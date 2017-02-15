package helpers.db.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Default filter mapper.
 *
 * @author jtremeaux
 */
public class DefaultFilterMapper implements FilterMapper {
    @Override
    public List<FilterColumn> map(Map<String, String> filterMap) {
        List<FilterColumn> columnFilters = new ArrayList<>();

        for (Map.Entry<String, String> entry : filterMap.entrySet()) {
            columnFilters.add(new StringFilterColumn(entry.getKey(), entry.getValue()));
        }
        return columnFilters;
    }
}
