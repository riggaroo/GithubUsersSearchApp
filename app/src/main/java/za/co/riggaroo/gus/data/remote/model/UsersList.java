
package za.co.riggaroo.gus.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UsersList {

    @SerializedName("total_count")
    @Expose
    private Integer totalCount;
    @SerializedName("incomplete_results")
    @Expose
    private Boolean incompleteResults;
    @SerializedName("items")
    @Expose
    private List<User> items = new ArrayList<User>();

    /**
     * @return The totalCount
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount The total_count
     */
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @return The incompleteResults
     */
    public Boolean getIncompleteResults() {
        return incompleteResults;
    }

    /**
     * @param incompleteResults The incomplete_results
     */
    public void setIncompleteResults(Boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    /**
     * @return The items
     */
    public List<User> getItems() {
        return items;
    }

    /**
     * @param items The items
     */
    public void setItems(List<User> items) {
        this.items = items;
    }

}
