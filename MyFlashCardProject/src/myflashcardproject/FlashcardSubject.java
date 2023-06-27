package myflashcardproject;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

public class FlashcardSubject  implements Serializable  {
	//Class Attributes
    private String subjectName;
    private List<Flashcard> subject;
    //Constructor

    public FlashcardSubject(String subjectName) {
        this.subjectName = subjectName;
        this.subject = new ArrayList<>();
    }

    //get subject name
    public String getSubjectName() {
        return subjectName;
    }
    
    //get All flashcard in subject
    public List<Flashcard> getFlashcards() {
        return subject;
    }
    
    //add flashcard to subject
    public void addFlashcard(Flashcard flashcard) {
        subject.add(flashcard);
    }

    //remove flashcard from subject
    public void removeFlashcard(Flashcard flashcard) {
        subject.remove(flashcard);
    }
    
    @Override
    public String toString() {
        return "Subject: " + subjectName ;
    }
  
}
