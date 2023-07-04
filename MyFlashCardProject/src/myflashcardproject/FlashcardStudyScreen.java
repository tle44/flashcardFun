package myflashcardproject;

import javax.swing.*;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.Style;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FlashcardStudyScreen {

    private List<Flashcard> flashcards;
    DefaultListModel<Flashcard> flashcardListModel;
    private int currentFlashcardIndex;
    private JFrame frame;
    private JLabel flashcardLabel;
    private JPanel panel;
    private JTextPane flashcardTextPane;

    public FlashcardStudyScreen(List<Flashcard> flashcards, DefaultListModel<Flashcard> flashcardListModel) {
        this.flashcards = flashcards;
        this.currentFlashcardIndex = -1;
        this.flashcardListModel = flashcardListModel;
    }

    public void startStudy() {
        if (flashcards.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No flashcards available.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        frame = new JFrame();
        frame.setBounds(100, 100, 853, 502);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 853, 502);
        frame.add(panel);

        flashcardTextPane = new JTextPane();
        flashcardTextPane.setBounds(47, 32, 756, 344);
        flashcardTextPane.setFont(new Font("Arial", Font.PLAIN, 16));
        flashcardTextPane.setEditable(false);
        flashcardTextPane.setFocusable(false);

        JScrollPane scrollPane = new JScrollPane(flashcardTextPane);
        scrollPane.setBounds(47, 32, 756, 344);

        panel.add(scrollPane);

        JButton previousButton = new JButton("Previous");
        previousButton.setBounds(43, 405, 117, 29);
        panel.add(previousButton);

        JButton flipButton = new JButton("Flip");
        flipButton.setBounds(174, 405, 117, 29);
        panel.add(flipButton);

        JButton nextButton = new JButton("Next");
        nextButton.setBounds(303, 405, 117, 29);
        panel.add(nextButton);

        JButton addButton = new JButton("Add Flashcard");
        addButton.setBounds(431, 405, 117, 29);
        panel.add(addButton);

        JButton editButton = new JButton("Edit Flashcard");
        editButton.setBounds(560, 405, 117, 29);
        panel.add(editButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(689, 405, 117, 29);
        panel.add(deleteButton);

        frame.setVisible(true);
     

        
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNextFlashcard();
            }
        });

        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPreviousFlashcard();
            }
        });

        flipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flipFlashcard();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFlashcard();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteFlashcard();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editFlashcard();
            }
        });

        showNextFlashcard();
    }

    private void updateFlashcardLabel() {
        if (currentFlashcardIndex >= 0 && currentFlashcardIndex < flashcards.size()) {
            Flashcard currentFlashcard = flashcards.get(currentFlashcardIndex);
            flashcardTextPane.setText(currentFlashcard.getQuestion());
        }
    }

    private void showNextFlashcard() {
        currentFlashcardIndex++;

        if (currentFlashcardIndex >= flashcards.size()) {
            JOptionPane.showMessageDialog(frame, "End of flashcards.", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            updateFlashcardLabel();
        }
    }

    private void showPreviousFlashcard() {
        currentFlashcardIndex--;

        if (currentFlashcardIndex < 0) {
            JOptionPane.showMessageDialog(frame, "Beginning of flashcards.", "Information", JOptionPane.INFORMATION_MESSAGE);
            currentFlashcardIndex = 0;
        }else {
        	updateFlashcardLabel();
        }
    }
   

    private void flipFlashcard() {
        if (currentFlashcardIndex >= 0 && currentFlashcardIndex < flashcards.size()) {
            Flashcard currentFlashcard = flashcards.get(currentFlashcardIndex);
            String flippedQuestion = currentFlashcard.getAnswer();
            
            if ( flashcardTextPane.getText().equals(currentFlashcard.getQuestion())) {
            	 flashcardTextPane.setText(flippedQuestion);
            } else {
            	 flashcardTextPane.setText(currentFlashcard.getQuestion());
            }
        }
    }

    private void addFlashcard() {
        String question = JOptionPane.showInputDialog(frame, "Enter the question:");
        if (question != null && !question.isEmpty()) {
            String answer = JOptionPane.showInputDialog(frame, "Enter the answer:");
            if (answer != null && !answer.isEmpty()) {
                Flashcard flashcard = new Flashcard(question, answer);
                flashcards.add(flashcard);
                JOptionPane.showMessageDialog(frame, "Flashcard added successfully.", "Information", JOptionPane.INFORMATION_MESSAGE);
                flashcardListModel.addElement(flashcard);
            }
        }
    }

    private void deleteFlashcard() {
        if (currentFlashcardIndex >= 0 && currentFlashcardIndex < flashcards.size()) {
            flashcards.remove(currentFlashcardIndex);
            flashcardListModel.remove(currentFlashcardIndex);
            JOptionPane.showMessageDialog(frame, "Flashcard deleted successfully.", "Information", JOptionPane.INFORMATION_MESSAGE);
            if (currentFlashcardIndex >= flashcards.size()) {
                currentFlashcardIndex = flashcards.size() - 1;
            }
            updateFlashcardLabel();
        }
    }

    private void editFlashcard() {
        if (currentFlashcardIndex >= 0 && currentFlashcardIndex < flashcards.size()) {
            Flashcard currentFlashcard = flashcards.get(currentFlashcardIndex);
            String updatedQuestion = JOptionPane.showInputDialog(frame, "Enter the updated question:", currentFlashcard.getQuestion());
            if (updatedQuestion != null && !updatedQuestion.isEmpty()) {
                String updatedAnswer = JOptionPane.showInputDialog(frame, "Enter the updated answer:", currentFlashcard.getAnswer());
                if (updatedAnswer != null && !updatedAnswer.isEmpty()) {
                    currentFlashcard.setQuestion(updatedQuestion);
                    currentFlashcard.setAnswer(updatedAnswer);
                    JOptionPane.showMessageDialog(frame, "Flashcard updated successfully.", "Information", JOptionPane.INFORMATION_MESSAGE);
                    updateFlashcardLabel();                  
                }
            }
        }
    }
    
    
    
}

