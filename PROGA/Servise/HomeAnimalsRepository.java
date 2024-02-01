package PROGA.Servise;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import PROGA.Model.Creator;
import PROGA.Model.HomeAnimals;
import PROGA.Model.HomeAnimalsCreator;
import PROGA.Model.HomeAnimalsType;

public class HomeAnimalsRepository implements IRepository<HomeAnimals> {

    private Creator animalCreator;
    private Statement sqlSt;
    private ResultSet resultSet;
    private String SQLstr;
    
    public HomeAnimalsRepository() {
        this.animalCreator = new HomeAnimalsCreator();
    };

    @Override
    public List<HomeAnimals> getAll() {
        List<HomeAnimals> farm = new ArrayList<HomeAnimals>();
        HomeAnimals animal;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection dbConnection = getConnection()) {
                sqlSt = dbConnection.createStatement();
                SQLstr = "SELECT GenusId, Id, Name, Birthday FROM animal_list ORDER BY Id";
                resultSet = sqlSt.executeQuery(SQLstr);
                while (resultSet.next()) {

                    HomeAnimalsType type = HomeAnimalsType.getType(resultSet.getInt(1));
                    int id = resultSet.getInt(2);
                    String name = resultSet.getString(3);
                    LocalDate birthday = resultSet.getDate(4).toLocalDate();
                    
                    animal = animalCreator.createAnimal(type, name, birthday);
                    animal.setHomeId(id);
                    farm.add(animal);
                }
                return farm;
            } 
        } catch (ClassNotFoundException | IOException | SQLException ex) {
            Logger.getLogger(HomeAnimalsRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
        }           
    }

    @Override
    public HomeAnimals getById(int homeId) {
        HomeAnimals animal = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection dbConnection = getConnection()) {

                SQLstr = "SELECT GenusId, Id, Name, Birthday FROM animal_list WHERE Id = ?";
                PreparedStatement prepSt = dbConnection.prepareStatement(SQLstr);
                prepSt.setInt(1, homeId);
                resultSet = prepSt.executeQuery();

                if (resultSet.next()) {

                    HomeAnimalsType type = HomeAnimalsType.getType(resultSet.getInt(1));
                    int id = resultSet.getInt(2);
                    String name = resultSet.getString(3);
                    LocalDate birthday = resultSet.getDate(4).toLocalDate();

                    animal = animalCreator.createAnimal(type, name, birthday);
                    animal.setHomeId(id);
                } 
                return animal;
            } 
        } catch (ClassNotFoundException | IOException | SQLException ex) {
            Logger.getLogger(HomeAnimalsRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public int create(HomeAnimals animal) {
        int rows;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection dbConnection = getConnection()) {

                SQLstr = "INSERT INTO animal_list (HomeName, Birthday, GenusId) SELECT ?, ?, (SELECT Id FROM animal_types WHERE Genus_name = ?)";
                PreparedStatement prepSt = dbConnection.prepareStatement(SQLstr);
                prepSt.setString(1, animal.getName());
                prepSt.setDate(2, Date.valueOf(animal.getBirthdayDate())); 
                prepSt.setString(3, animal.getClass().getSimpleName());

                rows = prepSt.executeUpdate();
                return rows;
            }
        } catch (ClassNotFoundException | IOException | SQLException ex) {
            Logger.getLogger(HomeAnimalsRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
        } 
    }

    public void train (int id, String command){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection dbConnection = getConnection()) {
                String SQLstr = "INSERT INTO animal_command (homeId, CommandId) SELECT ?, (SELECT Id FROM commands WHERE Command_name = ?)";
                PreparedStatement prepSt = dbConnection.prepareStatement(SQLstr);
                prepSt.setInt(1, id);
                prepSt.setString(2, command);

                prepSt.executeUpdate();
            }
        } catch (ClassNotFoundException | IOException | SQLException ex) {
            Logger.getLogger(HomeAnimalsRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
        } 
    }

    public List<String> getCommandsById (int homeId, int commands_type){   
        
         List <String> commands = new ArrayList <>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection dbConnection = getConnection()) {
                if (commands_type == 1){
                    SQLstr = "SELECT Command_name FROM animals_command pc JOIN commands c ON pc.CommandId = c.Id WHERE pc.homeId = ?";
                } else {
                    SQLstr = "SELECT Command_name FROM commands c JOIN Genus_command gc ON c.Id = gc.CommandId WHERE gc.GenusId = (SELECT GenusId FROM animalanimal_list WHERE Id = ?)";
                }
                PreparedStatement prepSt = dbConnection.prepareStatement(SQLstr);
                prepSt.setInt(1, homeId);
                resultSet = prepSt.executeQuery();
                while (resultSet.next()) {
                    commands.add(resultSet.getString(1));
                }
                return commands;
            }
        } catch (ClassNotFoundException | IOException | SQLException ex) {
            Logger.getLogger(HomeAnimalsRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
        } 
    }

    @Override
    public int update(HomeAnimals animals) {
        int rows;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection dbConnection = getConnection()) {
                SQLstr = "UPDATE animal_list SET AnimalName = ?, Birthday = ? WHERE Id = ?";
                PreparedStatement prepSt = dbConnection.prepareStatement(SQLstr);

                prepSt.setString(1, animals.getName());
                prepSt.setDate(2, Date.valueOf(animals.getBirthdayDate())); 
                prepSt.setInt(3,animals.getHomeId());
                
                rows = prepSt.executeUpdate();
                return rows;
            }
        } catch (ClassNotFoundException | IOException | SQLException ex) {
            Logger.getLogger(HomeAnimalsRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
        } 
    }

    @Override
    public void delete (int id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection dbConnection = getConnection()) {
                SQLstr = "DELETE FROM animal_list WHERE Id = ?";
                PreparedStatement prepSt = dbConnection.prepareStatement(SQLstr);
                prepSt.setInt(1,id);
                prepSt.executeUpdate();
            }
        } catch (ClassNotFoundException | IOException | SQLException ex) {
            Logger.getLogger(HomeAnimalsRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
        } 
    }

    public static Connection getConnection() throws SQLException, IOException {

        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("PROGA/Resource/database.properties")) {

            props.load(fis);
            String url = props.getProperty("url");
            String username = props.getProperty("username");
            String password = props.getProperty("password");

            return DriverManager.getConnection(url, username, password);
        }
    }
}