package model.extra;

public class Invoice {
    public final int id;
    public final int idApplication;
    public final int idMaster;
    public final int price;
    public int isDone = 0;

    public Invoice(int id, int idApplication, int idMaster, int price) {
        this.id = id;
        this.idApplication = idApplication;
        this.idMaster = idMaster;
        this.price = price;
    }

    public Invoice(int idApplication, int idMaster, int price) {
        this.id = -1;
        this.idApplication = idApplication;
        this.idMaster = idMaster;
        this.price = price;
    }

    public boolean getProgress() { return isDone == 1; }
    public void setProgress(boolean isDone) { this.isDone = isDone ? 1 : 0; }
}