package PROGA.Model;

import java.time.LocalDate;

public abstract class Creator {

    protected abstract HomeAnimals createNewAnimals(HomeAnimalsType type);

    public HomeAnimals createAnimal(HomeAnimalsType type, String name, LocalDate date) {

        HomeAnimals animal = createNewAnimals(type);
        animal.setName(name);
        animal.setBirthday(date);
        return animal;
    }
}