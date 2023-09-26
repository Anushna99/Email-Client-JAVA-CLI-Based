public class Official extends Recipient{
    private String designation;

    public Official(String name, String email_address, String designation) {
        super(name, email_address);
        this.designation = designation;
    }

    public String getDesignation() {
        return this.designation;
    }
}
