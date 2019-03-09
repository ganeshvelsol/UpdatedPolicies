package simplytextile.policytracker.changepasswordresponse;

public class Changespassword
{

    private int statuscode;

    private String message;

    private Data data;

    public int getStatuscode()
    {
        return statuscode;
    }

    public void setStatuscode(int statuscode)
    {
        this.statuscode = statuscode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data)
    {
        this.data = data;
    }
}
