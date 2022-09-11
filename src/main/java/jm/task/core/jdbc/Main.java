package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        Util.getSessionFactory();

        User user3 = new User("Ivan", "Groznyi", (byte) 55);
        User user4 = new User("Olga", "Buzova", (byte) 40);
        User user5 = new User("Ivan", "Dulin", (byte) 35);
        User user6 = new User("Arina", "Rodionova", (byte) 19);


        UserService userService = new UserServiceImpl();

        //   userService.createUsersTable();

        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        System.out.println("User с именем " + user3.getName() + " добавлен в базу данных");
        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());
        System.out.println("User с именем " + user4.getName() + " добавлен в базу данных");
        userService.saveUser(user5.getName(), user5.getLastName(), user5.getAge());
        System.out.println("User с именем " + user5.getName() + " добавлен в базу данных");
        userService.saveUser(user6.getName(), user6.getLastName(), user6.getAge());
        System.out.println("User с именем " + user6.getName() + " добавлен в базу данных");

        System.out.println(userService.getAllUsers());

//        userService.cleanUsersTable();
//        userService.dropUsersTable();
    }
}
