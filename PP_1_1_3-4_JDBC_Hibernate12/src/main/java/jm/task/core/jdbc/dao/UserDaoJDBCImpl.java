package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;


public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }
    private final Connection connection = getConnection();;

    public void createUsersTable() {

            String sql = "CREATE TABLE IF NOT EXISTS`mydbtest`.`users` (`id` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(45) NULL,`lastname` VARCHAR(45) NULL,`age` INT(3) NULL, PRIMARY KEY (`id`))";
            try (Statement statement = connection.createStatement() ) {

                statement.execute(sql);
            }
            catch (SQLException e) {

                e.printStackTrace();
                System.out.println("ERROR в методе createUsersTable 1 ");
            }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";

        try (Statement statement = connection.createStatement()) {

            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERROR в методе dropUsersTable 1 ");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        User user = new User( name,  lastName,  age);

        String sql = "insert into users (name,lastname,age) values(?,?,?)";
        try ( PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setByte(3,user.getAge());
            preparedStatement.executeUpdate();
            System.out.println("User с именем – "+ name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERORR в методе saveUser 1");
        }
    }

    public void removeUserById(long id) {

        String sql = "delete from users where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERROR в методе removeUserById 1 ");
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<User>();
        String sql = " SELECT ID, NAME, LASTNAME, AGE FROM USERs";

        try ( Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                User user = new User ();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                list.add(user);
            }
        } catch ( SQLException e) {
            e.printStackTrace();
            System.out.println("ERORR в методе getAllUsers 1");
        }
        System.out.println(list);
        return  list;
    }

    public void cleanUsersTable() {

        String sql = " DELETE FROM users";
        try ( Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
