package by.expert.soft.dao.impl;

public enum FieldSort {
    NAME ("name"),
    SURNAME ("surname"),
    EMAIL ("email"),
    LOGIN ("login"),
    PHONE_NUMBER ("phoneNumber"),
    DEFAULT ("");

    private final String name;

    private FieldSort(String s) {
        name = s;
    }

    public String toString() {
        return this.name;
    }
}
