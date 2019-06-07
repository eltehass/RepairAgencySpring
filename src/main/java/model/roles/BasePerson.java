package model.roles;


import model.db.DBProvider;

import java.io.Serializable;

public abstract class BasePerson implements Serializable {
    public final int id;
    public final int idPersonType;
    public final String name;
    public final String surname;
    public final int age;
    public final String login;
    public final String password;
    public final String email;
    public final String phone;
    protected final DBProvider dbProvider = new DBProvider();

    public BasePerson(int id, int idPersonType, String name, String surname, int age, String login, String password, String email, String phone) {
        this.id = id;
        this.idPersonType = idPersonType;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.login = login;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public BasePerson(int idPersonType, String name, String surname, int age, String login, String password, String email, String phone) {
        this.id = -1;
        this.idPersonType = idPersonType;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.login = login;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

//    public void pushItSelfToDataBase() {
//        dbProvider.personInfoDao().create(this);
//    }

//    public PersonType getPersonType() {
//        switch (idPersonType) {
//            case 1: return PersonType.CUSTOMER;
//            case 2: return PersonType.MANAGER;
//            case 3: return PersonType.MASTER;
//            default: return PersonType.NONE;
//        }
//    }

}
