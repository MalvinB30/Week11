package exceptions;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);
    static ArrayList<User> listOfUser = new ArrayList<>();

    public static void main(String[] args) {

        initialize();

        boolean running = true;

        while (running) {
            System.out.println("\nMenu Utama");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.print("Pilihan : ");
            int pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {
                case 1:
                    handleLogin();
                    break;
                case 2:
                    handleSignUp();
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia");
            }
        }
    }

    public static void initialize() {
        User defaultUser = new User(
                "John",
                "Doe",
                'L',
                "Jl. Merpati No. 1 RT 1 RW 1, Banten",
                "admin",
                "admin"
        );
        listOfUser.add(defaultUser);
    }

    public static void handleLogin() {

        int attempts = 0;

        while (attempts < 3) {

            try {
                System.out.print("Username : ");
                String username = input.nextLine();

                System.out.print("Password : ");
                String password = input.nextLine();

                boolean success = false;

                for (User u : listOfUser) {
                    if (u.login(username, password)) {
                        System.out.println(u.greeting());
                        success = true;
                        return;
                    }
                }

                attempts++;
                System.out.println("Username atau password salah! Sisa percobaan: " + (3 - attempts));

            } catch (ExcessiveFailedLoginException ex) {
                System.out.println(ex.getMessage());
                return;
            }
        }

        System.out.println("Anda telah melewati batas percobaan login!");
    }

    public static void handleSignUp() {

        try {
            System.out.print("Nama Depan : ");
            String firstName = input.nextLine();

            System.out.print("Nama Belakang : ");
            String lastName = input.nextLine();

            System.out.print("Jenis Kelamin (L/P) : ");
            Character gender = input.nextLine().toUpperCase().charAt(0);

            System.out.print("Alamat : ");
            String address = input.nextLine();

            System.out.print("Username : ");
            String username = input.nextLine();

            if (username.length() <= 8) {
                throw new InvalidPropertyException("Username harus lebih dari 8 karakter!");
            }

            System.out.print("Password : ");
            String password = input.nextLine();

            if (password.length() < 6 || password.length() > 16 ||
                !password.matches(".*[A-Z].*") ||
                !password.matches(".*[0-9].*")) {

                throw new InvalidPropertyException(
                        "Password harus mengandung huruf besar, angka, minimal 6 karakter dan maksimal 16 karakter"
                );
            }

            listOfUser.add(new User(
                    firstName,
                    lastName,
                    gender,
                    address,
                    username,
                    password
            ));

            System.out.println("User berhasil didaftarkan!");

        } catch (InvalidPropertyException ex) {
            System.out.println(ex.getMessage());
        }
    }
}