package com.example.userservice;

import com.example.userservice.dao.UserDao;
import com.example.userservice.model.User;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserDao dao = new UserDao();

    public static void main(String[] args) {
        while (true) {
            System.out.println("enter 1 to Create\nenter 2 to Read\nenter 3 to Update\nenter 4 to Delete\nenter 5 to Exit");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> createUser();
                case "2" -> readUsers();
                case "3" -> updateUser();
                case "4" -> deleteUser();
                case "5" -> System.exit(0);
                default -> System.out.println("Invalid option");
            }
        }
    }

    private static void createUser() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Age: ");
        int age = Integer.parseInt(scanner.nextLine());

        dao.create(new User(name, email, age));
        System.out.println("User created.");
    }

    private static void readUsers() {
        dao.getAll().forEach(u -> System.out.printf("%d: %s, %s, %d y.o.\n", u.getId(), u.getName(), u.getEmail(), u.getAge()));
    }

    private static void updateUser() {
        System.out.print("Enter user ID: ");
        Long id = Long.parseLong(scanner.nextLine());
        User user = dao.get(id);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.print("New name (" + user.getName() + "): ");
        user.setName(scanner.nextLine());
        System.out.print("New email (" + user.getEmail() + "): ");
        user.setEmail(scanner.nextLine());
        System.out.print("New age (" + user.getAge() + "): ");
        user.setAge(Integer.parseInt(scanner.nextLine()));

        dao.update(user);
        System.out.println("User updated.");
    }

    private static void deleteUser() {
        System.out.print("Enter user ID: ");
        Long id = Long.parseLong(scanner.nextLine());
        dao.delete(id);
        System.out.println("User deleted.");
    }
}
