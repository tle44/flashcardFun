package myflashcardproject;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.Style;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FlashcardStudyScreen {

    private List<Flashcard> flashcards;
    private DefaultTableModel flashcardTableModel;
    private int currentFlashcardIndex;
    private JFrame frame;
    private JLabel flashcardLabel;
    private JTextArea flashcardTextArea;
    private UserAccount userAccount;
    private String username;
    private static final Color PRIMARY_COLOR = new Color(52, 152, 219);
    private static final Color BACKGROUND_COLOR = new Color(246, 247, 251);
  
    private static final Font LABEL_FONT = new Font("Serif", Font.PLAIN, 24);
    private static final Font FIELD_FONT = new Font("Serif", Font.PLAIN, 20);
    private static final Color textColor = new Color(46, 56, 86); 
    private static final Color buttonColor = new Color(253, 205, 30);
    



    public FlashcardStudyScreen(List<Flashcard> flashcards, DefaultTableModel flashcardTableModel, UserAccount userAccount, String username) {
        this.flashcards = flashcards;
        this.currentFlashcardIndex = -1;
        this.flashcardTableModel = flashcardTableModel;
        this.userAccount = userAccount;
        this.username = username;
        
    }

    public void startStudy() {
        if (flashcards.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No flashcards available.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new GridBagLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);

        flashcardTextArea = new JTextArea();
        flashcardTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        flashcardTextArea.setEditable(false);
        flashcardTextArea.setFocusable(false);
        flashcardTextArea.setLineWrap(true);
        flashcardTextArea.setWrapStyleWord(true);
        
       
      

        JScrollPane scrollPane = new JScrollPane(flashcardTextArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);



     // Set a preferred size for the flashcardTextArea
        GridBagConstraints scrollPaneConstraints = new GridBagConstraints();
        scrollPaneConstraints.gridx = 0;
        scrollPaneConstraints.gridy = 0;
        scrollPaneConstraints.gridwidth = 3;
        scrollPaneConstraints.weightx = 1.0;
        scrollPaneConstraints.weighty = 1.0;
        scrollPaneConstraints.fill = GridBagConstraints.BOTH;
        scrollPaneConstraints.insets = new Insets(40, 40, 40, 40); // Adjusted Insets for scrollPane
        panel.add(scrollPane, scrollPaneConstraints);

        


        
        
        
        
        JButton previousButton = new JButton("Previous");
        buttonCustom(previousButton);
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 1;
        buttonConstraints.weightx = 0.33;
        buttonConstraints.weighty = 0.0;
        buttonConstraints.fill = GridBagConstraints.HORIZONTAL;
        buttonConstraints.insets = new Insets(10, 40, 20, 40); // Adjusted Insets for buttons
        panel.add(previousButton, buttonConstraints);

        JButton flipButton = new JButton("Flip");
        buttonCustom(flipButton);
        buttonConstraints.gridx = 1;
        buttonConstraints.gridy = 1;
        panel.add(flipButton, buttonConstraints);

        JButton nextButton = new JButton("Next");
        buttonCustom(nextButton);
        buttonConstraints.gridx = 2;
        buttonConstraints.gridy = 1;
        panel.add(nextButton, buttonConstraints);

        JButton addButton = new JButton("Add Flashcard");
        buttonCustom(addButton);
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 2;
        panel.add(addButton, buttonConstraints);

        JButton editButton = new JButton("Edit Flashcard");
        buttonCustom(editButton);
        buttonConstraints.gridx = 1;
        buttonConstraints.gridy = 2;
        panel.add(editButton, buttonConstraints);

        JButton deleteButton = new JButton("Delete");
        buttonCustom(deleteButton);
        buttonConstraints.gridx = 2;
        buttonConstraints.gridy = 2;
        panel.add(deleteButton, buttonConstraints);

       


        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(panel, BorderLayout.CENTER);
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
            flashcardTextArea.setText(currentFlashcard.getQuestion());
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
            
            if ( flashcardTextArea.getText().equals(currentFlashcard.getQuestion())) {
            
            	 flashcardTextArea.setText(flippedQuestion);
            } else {
            
            	 flashcardTextArea.setText(currentFlashcard.getQuestion());
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
   	         	flashcardTableModel.addRow(new Object[]{flashcard.getQuestion(), flashcard.getAnswer()});
   	            userAccount.saveFlashcards(username);
            }
        }
    }

    private void deleteFlashcard() {
        if (currentFlashcardIndex >= 0 && currentFlashcardIndex < flashcards.size()) {
            flashcards.remove(currentFlashcardIndex);
            flashcardTableModel.removeRow(currentFlashcardIndex);
            JOptionPane.showMessageDialog(frame, "Flashcard deleted successfully.", "Information", JOptionPane.INFORMATION_MESSAGE);
            if (currentFlashcardIndex >= flashcards.size()) {
                currentFlashcardIndex = flashcards.size() - 1;
            }
            updateFlashcardLabel();
            userAccount.saveFlashcards(username);
        }
    }

    private void editFlashcard() {
        if (currentFlashcardIndex >= 0 && currentFlashcardIndex < flashcards.size()) {
            Flashcard currentFlashcard = flashcards.get(currentFlashcardIndex);
            String updatedQuestion = JOptionPane.showInputDialog(frame, "Enter the updated question:", currentFlashcard.getQuestion());
            if (updatedQuestion != null && !updatedQuestion.isEmpty()) {
                String updatedAnswer = JOptionPane.showInputDialog(frame, "Enter the updated answer:", currentFlashcard.getAnswer());
                if (updatedAnswer != null && !updatedAnswer.isEmpty()) {
                	int questionColumnIndex = 0; 
                    int answerColumnIndex = 1; 
                    currentFlashcard.setQuestion(updatedQuestion);
                    flashcardTableModel.setValueAt(updatedQuestion, currentFlashcardIndex, questionColumnIndex) ;

                    currentFlashcard.setAnswer(updatedAnswer);
                    flashcardTableModel.setValueAt(updatedAnswer, currentFlashcardIndex, answerColumnIndex);
                    flashcards.set(currentFlashcardIndex, currentFlashcard);
                    
                    JOptionPane.showMessageDialog(frame, "Flashcard updated successfully.", "Information", JOptionPane.INFORMATION_MESSAGE);
                    updateFlashcardLabel(); 
                    userAccount.saveFlashcards(username);
                    
                }
            }
        }
    }
    public void buttonCustom(JButton button) {
        button.setFont(LABEL_FONT);
        button.setForeground(textColor);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBackground(buttonColor);
        button.setForeground(textColor);
    }
    
}






