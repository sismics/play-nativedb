package helpers.db.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Inclusive filter mapper: filter all specified columns using a String filter.
 *
 * @author jtremeaux
 */
public abstract class InclusiveFilterMapper extends DefaultFilterMapper {
    /**
     * Return the set of columns that can be filtered.
     *
     * @return Set of columns names or aliases
     */
    public abstract Set<String> getColumnName();

    @Override
    public List<FilterColumn> map(Map<String, String> filterMap) {
        List<FilterColumn> columnFilters = new ArrayList<>();

        for (Map.Entry<String, String> entry : filterMap.entrySet()) {
            if (getColumnName().contains(entry.getKey())) {
                columnFilters.add(new StringFilterColumn(entry.getKey(), entry.getValue()));
            }
        }
        return columnFilters;
    }
}
