package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

//        Создание таблицы User(ов)
        userService.createUsersTable();

//        Добавление 4 User(ов) в таблицу с данными на свой выбор.
//        После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
        userService.saveUser("Гарик", "Харламов", (byte) 42);
        userService.saveUser("Демис", "Карибидис", (byte) 40);
        userService.saveUser("Илья", "Соболев", (byte) 40);
        userService.saveUser("Андрей", "Бебуришвили", (byte) 30);

//        Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
        userService.getAllUsers();

//        Очистка таблицы User(ов)
        userService.cleanUsersTable();

//        Удаление таблицы
        userService.dropUsersTable();
    }
}
