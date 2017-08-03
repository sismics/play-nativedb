package helpers.db.filter.mapper;

import com.google.common.collect.Multimap;
import helpers.db.filter.FilterColumn;

import java.util.List;

/**
 * Maps datatables columns to table columns for filtering.
 *
 * @author jtremeaux
 */
@FunctionalInterface
public interface FilterMapper {
    List<FilterColumn> map(Multimap<String, String> filterMap);
}
