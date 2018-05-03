public class Customer {

    private int custId;
    private String custName;
    private String custEmail;
    private Boolean mailing;

    public Customer(int custId) {
        this.custId = custId;
    }

    public Customer(String custName, String custEmail, Boolean mailing) {
        this.custName = custName;
        this.custEmail = custEmail;
        this.mailing = mailing;
    }

    public Customer(int custId, String custName, String custEmail, Boolean mailing) {
        this.custId = custId;
        this.custName = custName;
        this.custEmail = custEmail;
        this.mailing = mailing;
    }

    public Customer(int custId, String custName) {
        this.custId = custId;
        this.custName = custName;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public Boolean getMailing() {
        return mailing;
    }

    public void setMailing(Boolean mailing) {
        this.mailing = mailing;
    }
}
