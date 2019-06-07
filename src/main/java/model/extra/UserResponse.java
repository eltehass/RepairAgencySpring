package model.extra;

public class UserResponse {
    public final int id;
    public final int idInvoice;
    public final String comment;

    public UserResponse(int id, int idInvoice, String comment) {
        this.id = id;
        this.idInvoice = idInvoice;
        this.comment = comment;
    }

    public UserResponse(int idInvoice, String comment) {
        this.id = -1;
        this.idInvoice = idInvoice;
        this.comment = comment;
    }
}
