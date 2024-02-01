package PROGA.Model;

public enum HomeAnimalsType {
    Cat,
    Dog,
    Hamster;

    public static HomeAnimalsType getType (int typeId){
        switch (typeId){
            case 1:
                return HomeAnimalsType.Cat;
            case 2:
                return HomeAnimalsType.Dog;
            case 3:
                return HomeAnimalsType.Hamster;
            default:
                return null;
        }
    }
}