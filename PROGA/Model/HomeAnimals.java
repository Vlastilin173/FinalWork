package PROGA.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class HomeAnimals {
    protected int homeId;
    protected String name;
    protected LocalDate birthday;
    
    public void setHomeId(int homeId) {
        this.homeId = homeId;
    }

    public int getHomeId() {
        return homeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBirthday(LocalDate date) {
        this.birthday = date;
    }

    public LocalDate getBirthdayDate(){
        return birthday;
    }

    public String getBirthday() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return formatter.format(birthday);
    }

    @Override
    public String toString() {
        return String.format("%d. %s: имя: %s, дата рождения: %s ", getHomeId(), getClass().getSimpleName(), name, getBirthday());
    }
}