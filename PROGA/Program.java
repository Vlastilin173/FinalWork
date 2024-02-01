package PROGA;

import PROGA.Controller.AnimalController;
import PROGA.Model.HomeAnimals;
import PROGA.Servise.HomeAnimalsRepository;
import PROGA.Servise.IRepository;
import PROGA.UserInterface.ConsoleMenu;

public class Program {
    public static void main(String[] args) throws Exception {

        IRepository <HomeAnimals> myFarm = new HomeAnimalsRepository();
        AnimalController controller = new AnimalController(myFarm);
        new ConsoleMenu (controller).start();
    }
}    