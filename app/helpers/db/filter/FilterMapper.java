package helpers.db.filter;

import java.util.List;
import java.util.Map;

/**
 * Maps datatables columns to table columns for filtering.
 *
 * @author jtremeaux
 */
@FunctionalInterface
public interface FilterMapper {
    List<FilterColumn> map(Map<String, String> filterMap);
}
