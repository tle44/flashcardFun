package myflashcardproject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

public class UserAccount  implements Serializable {
    private String username;
    private String password;
    private List<FlashcardSubject> subjects;

    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
        this.subjects = new ArrayList<>();
    }

    //get username
    public String getUsername() {
        return username;
    }
    
    //check password
    public boolean authenticate(String password) {
        return this.password.equals(password);
    }
  

    //get subjects in user account
    public List<FlashcardSubject> getAllSubjects() {
        return subjects;
    }

    //add new subject
    public void addSubject(FlashcardSubject subject) {
        subjects.add(subject);
    }
    
    //remove subject
    public void removeSubject(FlashcardSubject subject) {
        subjects.remove(subject);	
    }
    
    
    public void saveFlashcards(String filename) {
    	String fileName = username + "_flashcards.ser";
	    Path filePath = Paths.get(fileName).toAbsolutePath();

	    try (FileOutputStream fileOut = new FileOutputStream(fileName);
	         ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
	        objectOut.writeObject(subjects);
	        objectOut.close();
          	fileOut.close();
	        System.out.println("Flashcards saved successfully for user: " + username);
	        System.out.println("File saved at: " + filePath);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }
    
    public void loadFlashcards(String username) {
    
    	String fileName = username + "_flashcards.ser";
	    Path filePath = Paths.get(fileName).toAbsolutePath();

	    try (FileInputStream fileIn = new FileInputStream(fileName);
	         ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
	        subjects = (List<FlashcardSubject>) objectIn.readObject();
	        System.out.println("Flashcards loaded successfully for user: " + username);
	        System.out.println("File loaded from: " + filePath);
	    } catch (IOException | ClassNotFoundException e) {
	        e.printStackTrace();
    	    
    	}
    }
    
    @Override
    public String toString() {
        return "UserAccount{" + "username='" + username + '\'' + ", password='" + password + '\'' + '}';
        }
}
