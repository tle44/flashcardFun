package myflashcardproject;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
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
        
        frame = new JFrame("Flashcard Study");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 700);
        frame = new JFrame("Flashcard Study");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 700);

        panel = new JPanel(new BorderLayout());

        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        cardPanel.setPreferredSize(new Dimension(600, 400));
        flashcardLabel = new JLabel();
        flashcardLabel.setFont(new Font("Arial", Font.BOLD, 16));
        flashcardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cardPanel.add(flashcardLabel);

        
        panel.add(cardPanel, BorderLayout.CENTER);

        JButton nextButton = new JButton("Next");
        JButton previousButton = new JButton("Previous");
        JButton flipButton = new JButton("Flip");
        JButton addButton = new JButton("Add Flashcard");
        JButton deleteButton = new JButton("Delete Flashcard");
        JButton editButton = new JButton("Edit Flashcard");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(previousButton);
        buttonPanel.add(flipButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
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
            flashcardLabel.setText(currentFlashcard.getQuestion());
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
            
            if (flashcardLabel.getText().equals(currentFlashcard.getQuestion())) {
                flashcardLabel.setText(flippedQuestion);
            } else {
                flashcardLabel.setText(currentFlashcard.getQuestion());
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

