package ru.yandex.dunaev.mick.myrealm;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class MyBook extends RealmObject {
    @Required
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
