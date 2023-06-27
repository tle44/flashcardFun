package myflashcardproject;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FlashcardApp  implements Serializable {
	//Class Attribute
	private List<UserAccount> accounts;
    private static final String ACCOUNTS_FILE = "accounts.ser";

	//Constructor
    public FlashcardApp() {
        accounts = new ArrayList<>();
    }

    //method to get all accounts
    public List<UserAccount> getAllAccounts() {
        return accounts;
    }
    
    //method to get account by username
    public UserAccount getAccount(String username) {
        UserAccount userAccount = null; // Initialize to null

        for (UserAccount user : accounts) {
            if (user.getUsername().equals(username)) {
                userAccount = user;
                break; // Exit the loop once a match is found
            }
        }
        return userAccount;
    }

    //addAccount method
    public void addAccount(UserAccount account) {
        accounts.add(account);
    }

    //remove an account method
    public void removeAccount(UserAccount account) {
        accounts.remove(account);
    }

    // Save accounts to a file
    public void saveAccountsToFile() {
        try {
            FileOutputStream fileOut = new FileOutputStream(ACCOUNTS_FILE);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(getAllAccounts());
            objectOut.close();
            fileOut.close();
            System.out.println("Accounts saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Check if an account already exists in a file
    public boolean accountExistsInFile(String username) {
        try {
            FileInputStream fileIn = new FileInputStream(ACCOUNTS_FILE);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            List<UserAccount> storedAccounts = (List<UserAccount>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            for (UserAccount storedAccount : storedAccounts) {
                if (storedAccount.getUsername().equals(username)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
 // load accounts in file
    public void loadAccountsFromFile() {
        try {
            FileInputStream fileIn = new FileInputStream(ACCOUNTS_FILE);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            accounts = (List<UserAccount>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            System.out.println("Accounts loaded successfully.");
            System.out.println(accounts);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
     }
  
}
    
    
    

    

