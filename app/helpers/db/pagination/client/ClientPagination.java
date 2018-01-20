package helpers.db.pagination.client;

import helpers.db.PaginatedList;
import helpers.db.filter.FilterColumn;
import helpers.db.filter.FilterCriteria;
import helpers.db.sort.SortColumn;
import helpers.db.sort.SortCriteria;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Client-side (Java) pagination / sorting / filtering.
 *
 * @author jtremeaux
 */
public class ClientPagination {
    /**
     * Paginate, filter and order a list on client (Java) side.
     *
     * @param paginatedList The list to paginate
     * @param fullList The full list of results
     * @param <E> The type of elements
     */
    public static <E> void paginate(PaginatedList<E> paginatedList, List<E> fullList, SortCriteria sortCriteria, FilterCriteria filterCriteria) {
        // Filter list
        if (filterCriteria != null) {
            fullList = doFilterClient(fullList, filterCriteria);
        }

        // Order list
        if (sortCriteria != null) {
            doSortClient(fullList, sortCriteria);
        }

        // Paginate list
        paginatedList.setList(doPaginateClient(paginatedList, fullList));
        paginatedList.setTotalRowCount(fullList.size());
    }

    @SuppressWarnings("unchecked")
    private static <E> void doSortClient(List<E> fullList, SortCriteria sortCriteria) {
        for (SortColumn sortColumn : sortCriteria.getSortColumnList()) {
            fullList.sort((e1, e2) -> {
                try {
                    Object value1 = PropertyUtils.getProperty(e1, sortColumn.getPath());
                    if (value1 instanceof Comparable) {
                        return ((Comparable) value1)
                                .compareTo(PropertyUtils.getProperty(e2, sortColumn.getPath()))
                                * (sortColumn.getDirection() ? 1 : -1);
                    } else {
                        return (BeanUtils.getProperty(e1, sortColumn.getPath()))
                                .compareTo(BeanUtils.getProperty(e2, sortColumn.getPath()))
                                * (sortColumn.getDirection() ? 1 : -1);
                    }
                } catch (Exception e) {
                    return 0;
                }
            });
        }
    }

    private static <E> List<E> doFilterClient(List<E> fullList, FilterCriteria filterCriteria) {
        for (FilterColumn filterColumn : filterCriteria.getFilterColumnList()) {
            fullList = fullList.stream().filter(e -> {
                try {
                    return BeanUtils.getProperty(e, filterColumn.getColumn()).contains(filterColumn.getFilter());
                } catch (Exception ex) {
                    return true;
                }
            }).collect(Collectors.toList());
        }
        return fullList;
    }

    private static <T> List<T> doPaginateClient(PaginatedList<T> paginatedList, List<T> fullList) {
        return fullList.subList(
                paginatedList.getOffset(),
                Integer.min(paginatedList.getOffset() + paginatedList.getLimit(), fullList.size()));
    }
}
