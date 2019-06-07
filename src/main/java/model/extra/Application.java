package model.extra;

public class Application {
    public final int id;
    public final int idPersonInfo;
    public final String body;

    public Application(int id, int idPersonInfo, String applicationBody) {
        this.id = id;
        this.idPersonInfo = idPersonInfo;
        this.body = applicationBody;
    }

    public Application(int idPersonInfo, String applicationBody) {
        this.id = -1;
        this.idPersonInfo = idPersonInfo;
        this.body = applicationBody;
    }
}