package ru.yandex.dunaev.mick.myrealm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Person extends RealmObject {
    @PrimaryKey
    private int id;

    private String name;
    private RealmList<Dog> dogs;
}
