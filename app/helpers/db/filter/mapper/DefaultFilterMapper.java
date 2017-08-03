package helpers.db.filter.mapper;

import com.google.common.collect.Multimap;
import helpers.db.filter.FilterColumn;
import helpers.db.filter.column.StringFilterColumn;

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
    public List<FilterColumn> map(Multimap<String, String> filterMap) {
        List<FilterColumn> columnFilters = new ArrayList<>();

        for (Map.Entry<String, String> entry : filterMap.entries()) {
            columnFilters.add(new StringFilterColumn(entry.getKey(), entry.getValue()));
        }
        return columnFilters;
    }
}
