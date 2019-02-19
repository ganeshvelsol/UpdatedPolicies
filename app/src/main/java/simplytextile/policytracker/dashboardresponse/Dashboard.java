package simplytextile.policytracker.dashboardresponse;

import java.util.List;

public class Dashboard
{


    private List<Object> sectionlist = null;

    private List<Dataset> dataset = null;

    public List<Object> getSectionlist()
    {
        return sectionlist;
    }

    public void setSectionlist(List<Object> sectionlist)
    {
        this.sectionlist = sectionlist;
    }

    public List<Dataset> getDataset()
    {
        return dataset;
    }

    public void setDataset(List<Dataset> dataset)
    {
        this.dataset = dataset;
    }

}
