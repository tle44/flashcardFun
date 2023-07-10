package myflashcardproject;

import java.io.Serializable;

public class Flashcard  implements Serializable  {
	//Class Attributes
	private String question;
	private String answer;
	
	//Constructor
	public Flashcard(String question, String answer) {
		this.question = question;
		this.answer = answer;
	}
	
	//Getter
	public String getQuestion() {
		return question;
	}
	public String getAnswer() {
		return answer;
	}
	
	//Setter
	public void setQuestion(String question) {
		this.question = question;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	

	//print FlashCard 
	public void printFlashCard() {
		System.out.println("Question: " +  question);
		System.out.println("Answer: " + answer);
	}
	
	
	@Override
    public String toString() {
        return "Question: " + question + "\nAnswer: " + answer;
    }
	
}
