/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package loginsystem;
import java.io.Console;

public class LoginSystem {
    public static void main(String[] args) {
        // Set correct username and password
        String correctUsername = "gabriel@30";
        String correctPassword = "gabriel30";

        // Obtain the system console
        Console console = System.console();

        if (console == null) {
            System.out.println("Console is not available.");
            System.out.println("Please run this program in a terminal or command prompt.");
            return;
        }

        // Allow 3 login attempts
        int attempts = 3;

        while (attempts > 0) {
            // Get username input
            String username = console.readLine("Enter username: ");

            // Get password input (masked as '*')
            char[] passwordChars = console.readPassword("Enter password: ");
            String password = new String(passwordChars);

            // Validate credentials
            if (username.equals(correctUsername) && password.equals(correctPassword)) {
                System.out.println("Login successful. Welcome, " + username + "!");
                return; // Exit on successful login
            } else {
                attempts--;
                System.out.println("Incorrect username or password. Attempts remaining: " + attempts);
            }
        }

        System.out.println("Too many failed attempts. Access denied.");
    }
}
