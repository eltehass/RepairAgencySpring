package model.extra;

public class RegectedApplication {
    public final int id;
    public final int idApplication;
    public final int idManager;
    public final String message;

    public RegectedApplication(int id, int idApplication, int idManager, String message) {
        this.id = id;
        this.idApplication = idApplication;
        this.idManager = idManager;
        this.message = message;
    }

    public RegectedApplication(int idApplication, int idManager, String message) {
        this.id = -1;
        this.idApplication = idApplication;
        this.idManager = idManager;
        this.message = message;
    }

}
