package PROGA.Model;

public class HomeAnimalsCreator extends Creator {

    protected HomeAnimals createNewAnimal (HomeAnimalsType type) {

        switch (type) {
            case Cat:
                return new Cat();
            case Dog:
                return new Dog();
            case Hamster:
                return new Hamster();
        }
        return null;
    }

    @Override
    protected HomeAnimals createNewAnimals(HomeAnimalsType type) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createNewAnimals'");
    }


}