package simplytextile.policytracker.dashboardresponse;

import java.util.List;

public class Dataset
{


    private String description;

    private Integer id;

    private List<String> headerlist = null;

    private List<Rowlist> rowlist = null;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getHeaderlist() {
        return headerlist;
    }

    public void setHeaderlist(List<String> headerlist) {
        this.headerlist = headerlist;
    }

    public List<Rowlist> getRowlist() {
        return rowlist;
    }

    public void setRowlist(List<Rowlist> rowlist) {
        this.rowlist = rowlist;
    }
}
