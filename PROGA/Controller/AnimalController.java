package PROGA.Controller;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import PROGA.Model.Creator;
import PROGA.Model.HomeAnimals;
import PROGA.Model.HomeAnimalsCreator;
import PROGA.Model.HomeAnimalsType;
import PROGA.Servise.HomeAnimalsRepository;
import PROGA.Servise.IRepository;
import PROGA.UserInterface.ConsoleView;
import PROGA.UserInterface.View;

public class AnimalController {
    private IRepository<HomeAnimals> homeRepository;
    private Creator animalCreator;
    private final View<Work13.HomeAnimals> view;
    private Validator validator;

    public AnimalController(IRepository<HomeAnimals> homeRepository) {
        this.homeRepository = homeRepository;
        this.animalCreator = new HomeAnimalsCreator();
        this.view = new ConsoleView();
        this.validator = new Validator();
    }

    public void createAnimal(HomeAnimalsType type) {

        String[] data = new String[] { view.getName(), view.getBirthday() };
        validator.validate(data);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birthday = LocalDate.parse(data[1], formatter);
        try {
            int res = homeRepository.create(animalCreator.createAnimal(type, data[0], birthday));
            view.showMessage(String.format("%d запись добавлена", res));
        } catch (RuntimeException e) {
            view.showMessage(e.getMessage());
        }

    }

    public void updateAnimal(int id) {

        HomeAnimals animal = getById(id);
        String[] data = new String[] { view.getName(), view.getBirthday() };

        validator.validate(data);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birthday = LocalDate.parse(data[1], formatter);
        animal.setName(data[0]);
        animal.setBirthday(birthday);
        try {
            int res = homeRepository.update(animal);
            view.showMessage(String.format("%d запись изменена \n", res));
        } catch (RuntimeException e) {
            view.showMessage(e.getMessage());
        }

    }

    public void getAllAnimal() {
        try {
            view.printAll(homeRepository.getAll(), HomeAnimals.class);
        } catch (RuntimeException e) {
            view.showMessage(e.getMessage());
        }
    }

    public boolean trainAnimal(int id, String command) {
        try {

            if (((HomeAnimalsRepository) homeRepository).getCommandsById(id, 1).contains(command))
                view.showMessage("это мы уже умеем");
            else {
                if (!((HomeAnimalsRepository) homeRepository).getCommandsById(id, 2).contains(command))
                    view.showMessage("невыполнимая команда");
                else {
                    ((HomeAnimalsRepository) homeRepository).train(id, command);
                    view.showMessage("команда разучена\n");
                    return true;
                }
            }
        } catch (RuntimeException e) {
            view.showMessage(e.getMessage());
        }
        return false;
    }

    public HomeAnimals getById(int id) {
        try {
            return homeRepository.getById(id);
        } catch (RuntimeException e) {
            view.showMessage(e.getMessage());
        }
        return null;
    }

    public void delete(int id) {
        try {
            homeRepository.delete(id);
            view.showMessage("запись удалена");
        } catch (RuntimeException e) {
            view.showMessage(e.getMessage());
        }
    }

    public void getCommands(int id) {
        try {
            view.printAll(((HomeAnimalsRepository) homeRepository).getCommandsById(id, 1), String.class);
        } catch (RuntimeException e) {
            view.showMessage(e.getMessage());
        }
    }
}