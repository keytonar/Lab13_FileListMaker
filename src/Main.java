import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    private static ArrayList<String> list = new ArrayList<String>();
    private static Scanner scanner = new Scanner(System.in);
    private static boolean needsToBeSaved = false; // Added boolean flag to keep track of list edits
    private static String currentListFilename = ""; // Added variable to keep track of current list filename

    public static void main(String[] args)
    {
        String choice = "";
        do
        {
            displayMenu();
            choice = SafeInput.getRegExString(scanner, "Enter your choice: ", "[AaDdOoSsVvCcqQ]");
            switch (choice.toUpperCase())
            {
                case "A":
                    addItem();
                    break;
                case "D":
                    deleteItem();
                    break;
                case "O":
                    openList();
                    break;
                case "S":
                    saveList();
                    break;
                case "C":
                    clearList();
                    break;
                case "V":
                    printList();
                    break;
                case "Q":
                    quitProgram();
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (!choice.equalsIgnoreCase("Q"));
    }

    private static void displayMenu()
    {
        System.out.println("List Menu");
        System.out.println("===================");
        System.out.println("A - Add an item to the list");
        System.out.println("D - Delete an item from the list");
        System.out.println("O - Open a list file from disk");
        System.out.println("S - Save the current list file to disk");
        System.out.println("C - Clear the current list");
        System.out.println("V - View the current list");
        System.out.println("Q - Quit the program");
        System.out.println();
        System.out.println("Current List:");
        printList();
        System.out.println();
    }

    private static void addItem()
    {
        String item = SafeInput.getNonZeroLenString(scanner, "Enter item to add: ");
        list.add(item);
        needsToBeSaved = true; // Set flag to indicate list has been edited and needs to be saved
        System.out.println(item + " has been added to the list.");
    }

    private static void deleteItem()
    {
        if (list.isEmpty())
        {
            System.out.println("The list is empty!");
            return;
        }
        int index = SafeInput.getRangedInt(scanner, "Enter item number to delete: ", 1, list.size());
        String item = list.remove(index - 1);
        needsToBeSaved = true; // Set flag to indicate list has been edited and needs to be saved
        System.out.println(item + " has been removed from the list.");
    }

    private static void openList()
    {
        if (needsToBeSaved)
        {
            System.out.println("Warning: Unsaved changes detected.");
            if (!SafeInput.getYNConfirm(scanner, "Do you want to save the current list? (Y/N): ")) {
                needsToBeSaved = false;
                list.clear();
                System.out.println("Current list discarded.");
            } else {
                saveList();
            }
        }

        String filename = SafeInput.getNonZeroLenString(scanner, "Enter list filename to open: ");
        try
        {
            FileInputStream fileIn = new FileInputStream(filename + ".txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            list = (ArrayList<String>) in.readObject();
            currentListFilename = filename;
            System.out.println("List loaded from " + filename + ".txt");
            in.close();
            fileIn.close();
        } catch(IOException | ClassNotFoundException e)

        {
            System.out.println("Error loading list from file: " + e.getMessage());
        }
    }

    private static void saveList()
    {
        if (!needsToBeSaved)
        {
            System.out.println("No changes to save.");
            return;
        }
        if (currentListFilename.isEmpty())
        {
            currentListFilename = SafeInput.getNonZeroLenString(scanner, "Enter list filename to save: ");
        }
        try
        {
            FileOutputStream fileOut = new FileOutputStream(currentListFilename + ".txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(list);
            out.close();
            fileOut.close();
            needsToBeSaved = false; // Reset flag as list has been saved
            System.out.println("List saved to " + currentListFilename + ".txt");
        } catch (IOException e)
        {
            System.out.println("Error saving list to file: " + e.getMessage());
        }
    }

    private static void clearList()
    {
        if (!list.isEmpty())
        {
            list.clear();
            needsToBeSaved = true; // Set flag to indicate list has been edited and needs to be saved
            System.out.println("List cleared.");
        }
        else
        {
            System.out.println("The list is already empty!");
        }
    }

    private static void printList()
    {
        if (!list.isEmpty())
        {
            for (int i = 0; i < list.size(); i++)
            {
                System.out.println((i + 1) + ". " + list.get(i));
            }
        } else {
            System.out.println("The list is empty!");
        }
    }

    private static void quitProgram()
    {
        if (needsToBeSaved)
        {
            System.out.println("Warning: Unsaved changes detected.");
            if (SafeInput.getYNConfirm(scanner, "Do you want to save the current list? (Y/N): "))
            {
                saveList();
            }
        }
        System.out.println("Goodbye!");
        scanner.close();
        System.exit(0);
    }
}
