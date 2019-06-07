package model.db;


import model.db.dao.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DBProvider {

    private static final String URL = "jdbc:mysql://localhost:3306/RAgencyDatabase?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "qazwsx123";

    private Connection connection;
    private final Map<String, AbstractDao> daoMap = new HashMap<>();

    public DBProvider() {
        connect();
        daoMap.put("applicationDao", new ApplicationDao(connection));
        daoMap.put("invoiceDao", new InvoiceDao(connection));
        daoMap.put("personInfoDao", new PersonInfoDao(connection));
        daoMap.put("personTypeDao", new PersonTypeDao(connection));
        daoMap.put("regectedApplicationsDao", new RegectedApplicationsDao(connection));
        daoMap.put("userResponsesDao", new UserResponsesDao(connection));
    }

    private void connect() {
        connection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.print("Success\n");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private AbstractDao getDao(String daoName){
        return daoMap.getOrDefault(daoName, null);
    }

    public final ApplicationDao applicationDao() {
        return (ApplicationDao) getDao("applicationDao");
    }

    public final InvoiceDao invoiceDao() {
        return (InvoiceDao) getDao("invoiceDao");
    }

    public final PersonInfoDao personInfoDao() {
        return (PersonInfoDao) getDao("personInfoDao");
    }

    public final PersonTypeDao personTypeDao() {
        return (PersonTypeDao) getDao("personTypeDao");
    }

    public final RegectedApplicationsDao regectedApplicationsDao() { return (RegectedApplicationsDao) getDao("regectedApplicationsDao"); }

    public final UserResponsesDao userResponsesDao() {
        return (UserResponsesDao) getDao("userResponsesDao");
    }
}