package helpers.db.dao;

import helpers.db.PaginatedList;
import helpers.db.PaginatedLists;
import helpers.db.QueryParam;
import helpers.db.filter.FilterCriteria;
import helpers.db.sort.SortCriteria;

import java.util.List;

/**
 * Base DAO.
 * 
 * @author jtremeaux
 */
public abstract class BaseDao<T, C> {
    /**
     * Search items by criteria.
     *
     * @param list Paginated list (updated by side effects)
     * @param criteria Search criteria
     * @param sortCriteria Sort criteria
     * @param filterCriteria Filter criteria
     */
    public void findByCriteria(PaginatedList<T> list, C criteria, SortCriteria sortCriteria, FilterCriteria filterCriteria) {
        PaginatedLists.executePaginatedQuery(list, getQueryParam(criteria, filterCriteria), sortCriteria);
    }

    /**
     * Search items by criteria.
     *
     * @param criteria Search criteria
     * @param sortCriteria Sort criteria
     * @param filterCriteria Filter criteria
     */
    public List<T> findByCriteria(C criteria, SortCriteria sortCriteria, FilterCriteria filterCriteria) {
        return PaginatedLists.executeQuery(getQueryParam(criteria, filterCriteria), sortCriteria);
    }

    /**
     * Search items by criteria.
     *
     * @param criteria Search criteria
     */
    public List<T> findByCriteria(C criteria) {
        return findByCriteria(criteria, null, null);
    }


    /**
     * Search items by criteria.
     *
     * @param criteria Search criteria
     */
    public T findFirstByCriteria(C criteria) {
        List<T> list = PaginatedLists.executeQuery(getQueryParam(criteria, null), null);
        return !list.isEmpty() ? list.iterator().next() : null;
    }

    protected abstract QueryParam getQueryParam(C criteria, FilterCriteria filterCriteria);

}
