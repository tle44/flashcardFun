package myflashcardproject;

import java.awt.EventQueue;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.JPasswordField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FlashcardAppGUI {


	    public JFrame frame;
	    private DefaultListModel<FlashcardSubject> subjectListModel;
	    private JList<FlashcardSubject> subjectList;
	    private DefaultListModel<Flashcard> flashcardListModel;
	    private JList<Flashcard> flashcardList;
	    private String username;
	    private String password;
	    private FlashcardApp flashcardApp = new FlashcardApp(); 
	    private UserAccount userAccount;
	    

	    
	    public FlashcardAppGUI() {
	        initializeLoginScreen();
	        flashcardApp.loadAccountsFromFile();
	        
	    }

	    private void initializeLoginScreen() {
	    	frame = new JFrame("Flashcard App - Login");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(400, 300);
	        frame.getContentPane().setLayout(new BorderLayout());

	        JPanel loginPanel = new JPanel();

	        JLabel usernameLabel = new JLabel("Username:");
	        usernameLabel.setBounds(152, 35, 66, 16);
	        JLabel passwordLabel = new JLabel("Password:");
	        passwordLabel.setBounds(155, 101, 63, 16);
	        JTextField usernameField = new JTextField(25);
	        usernameField.setBounds(6, 63, 388, 26);
	        JPasswordField passwordField = new JPasswordField(20);
	        passwordField.setBounds(6, 129, 388, 26);

	        JButton loginButton = new JButton("Login");
	        loginButton.setBounds(104, 167, 79, 29);
	        JButton registerButton = new JButton("Register");
	        registerButton.setBounds(207, 167, 95, 29);

	        loginButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                String username = usernameField.getText();
	                String password = new String(passwordField.getPassword());

	                // Call the authentication method to validate user credentials
	                	UserAccount user = authenticateUser(username, password);

	                if (user != null) {
	                    JOptionPane.showMessageDialog(frame, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
	                    frame.dispose();
	                    //load flashcard data from user account
	                    userAccount.loadFlashcards(username);
	                    launchSubjectScreen();
	                } else {
	                    JOptionPane.showMessageDialog(frame, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
	                }
	                
	                flashcardApp.loadAccountsFromFile();
	            }
	        });

	        registerButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                username = usernameField.getText();
	                password = new String(passwordField.getPassword());

	                // Call the registration method to create a new user account
	                boolean registrationSuccess = registerUser(username, password);

	                if (registrationSuccess) {
	                    // Registration successful
	                    JOptionPane.showMessageDialog(frame, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
	                } else {
	                    // Registration failed
	                    JOptionPane.showMessageDialog(frame, "Registration failed! This account already exist", "Error", JOptionPane.ERROR_MESSAGE);
	                }
	                
	                
	                flashcardApp.loadAccountsFromFile();
	            }
	        });
	        loginPanel.setLayout(null);
	        loginPanel.add(usernameLabel);
	        loginPanel.add(usernameField);
	        loginPanel.add(passwordLabel);
	        loginPanel.add(passwordField);
	        loginPanel.add(loginButton);
	        loginPanel.add(registerButton);
	        frame.getContentPane().add(loginPanel, BorderLayout.CENTER);
	        

	    }
	    
	        

	    private UserAccount authenticateUser(String username, String password) {
	    	if (flashcardApp.accountExistsInFile(username)&& (flashcardApp.getAccount(username)).authenticate(password)) {
	    		userAccount = flashcardApp.getAccount(username);
	    		return userAccount;
	    	}
	        else {
	            return null;
	        }
	    }

	    private boolean registerUser(String username, String password) {
	    	if(flashcardApp.accountExistsInFile(username)){
	    		return false;
	    	}else {
	    		userAccount = new UserAccount(username, password);
	    		flashcardApp.addAccount(userAccount);
	    		flashcardApp.saveAccountsToFile();
	    		return true;
	    	}
	    }  
	    
	    
	    public void launchSubjectScreen() {
	        // Create the main frame
	        frame = new JFrame("Flashcard App");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(500, 400);

	        // Create the subject list
	        subjectListModel = new DefaultListModel<>();
	        subjectList = new JList<>(subjectListModel);

	        // Create the buttons	      
	        JButton studyButton = new JButton("Study Flashcards");
	        JButton addButton = new JButton("Add Subject");
	        JButton deleteButton = new JButton("Delete Subject");

	        // Create the panel for the buttons
	        JPanel buttonPanel = new JPanel();
	        buttonPanel.add(studyButton);
	        buttonPanel.add(addButton);
	        buttonPanel.add(deleteButton);
	   

	        // Add components to the main frame
	        frame.getContentPane().setLayout(new BorderLayout());
	        frame.getContentPane().add(new JScrollPane(subjectList), BorderLayout.CENTER);
	        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

	        // Register action listeners for the buttons
	        addButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                addSubject();
	            }
	        });

	        deleteButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                deleteSubject();
	            }
	        });
	        
	        studyButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                studyFlashcards();
	            }
	        });
	        
	     // Register action listener for subject selection	        
	        subjectList.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                if (e.getClickCount() == 2) {
	                    displaySubjectFlashcards();
	                }
	            }
	        });

	        // Load the flashcard subjects
	        loadSubjects();

	        // Show the main frame
	        frame.setVisible(true);
	        
	    }
	    
	    public void addSubject() {
	        String subjectName = JOptionPane.showInputDialog(frame, "Enter subject name:");
	        if (subjectName != null && !subjectName.isEmpty()) {
	            FlashcardSubject subject = new FlashcardSubject(subjectName);
	            userAccount.addSubject(subject);
	            subjectListModel.addElement(subject);
                userAccount.saveFlashcards(username);
	        }
	    }

	    public void deleteSubject() {
	        int selectedIndex = subjectList.getSelectedIndex();
	        if (selectedIndex != -1) {
	            FlashcardSubject selectedSubject = subjectListModel.getElementAt(selectedIndex);
	            userAccount.removeSubject(selectedSubject);
	            subjectListModel.remove(selectedIndex);
	            userAccount.saveFlashcards(username);
	        }
	    }
	    
	    public void loadSubjects() {
	        List<FlashcardSubject> subjects;
	        subjects = userAccount.getAllSubjects();
	        for (FlashcardSubject subject : subjects) {
	            subjectListModel.addElement(subject);
	        }
	    }  
	    //=============================================    
	    public void displaySubjectFlashcards() {
	    	int selectedIndex = subjectList.getSelectedIndex();
	        if (selectedIndex != -1) {
	            FlashcardSubject selectedSubject = subjectListModel.getElementAt(selectedIndex);
	            List<Flashcard> flashcards = selectedSubject.getFlashcards();
	            
		    // Create the main frame
	        frame = new JFrame("Flashcards - " + selectedSubject.getSubjectName());
	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        frame.setSize(600, 500);
	
	        // Create the flashcard list
	        flashcardListModel = new DefaultListModel<>();
	        flashcardList = new JList<>(flashcardListModel);
		    flashcardList.setCellRenderer(new ListCellRenderer<Flashcard>() {
	            @Override
	            public Component getListCellRendererComponent(JList<? extends Flashcard> list, Flashcard value, int index, boolean isSelected, boolean cellHasFocus) {
	                JTextArea label = new JTextArea();
	                label.setText("Question: " + value.getQuestion() + "\nAnswer: " + value.getAnswer());

	                // Set the color of each line based on the index
	                if (index % 2 == 0) {
	                    label.setBackground(Color.WHITE);
	                } else {
	                    label.setBackground(Color.LIGHT_GRAY);
	                }

	                // Set the selection background color
	                if (isSelected) {
	                    label.setBackground(list.getSelectionBackground());
	                }

	                // Set a fixed cell width
	                int fixedCellWidth = 400; 

	                // Truncate text with ellipsis if it goes beyond the fixed cell width
	                FontMetrics fontMetrics = label.getFontMetrics(label.getFont());
	                String question = value.getQuestion();
	                String answer = value.getAnswer();
	                String truncatedQuestion = getTruncatedText(question, fontMetrics, fixedCellWidth);
	                String truncatedAnswer = getTruncatedText(answer, fontMetrics, fixedCellWidth);
	                label.setText("Question: " + truncatedQuestion + "\nAnswer: " + truncatedAnswer);

	                // Set the preferred size with increased height
	                int preferredHeight = 40; 
	                label.setPreferredSize(new Dimension(fixedCellWidth, preferredHeight));
	                
	                return label;
	            }
	            // Helper method to truncate text with ellipsis
	            private String getTruncatedText(String text, FontMetrics fontMetrics, int maxWidth) {
	                if (fontMetrics.stringWidth(text) <= maxWidth) {
	                    return text;
	                }
	                StringBuilder truncatedText = new StringBuilder(text);
	                int ellipsisWidth = fontMetrics.stringWidth("...");
	                while (fontMetrics.stringWidth(truncatedText.toString()) + ellipsisWidth > maxWidth) {
	                    truncatedText.deleteCharAt(truncatedText.length() - 1);
	                }
	                return truncatedText.append("...").toString();
	            }
	        });
		    
		    

	        // Create the buttons
	        JButton studyButton = new JButton("Study");
	        JButton addButton = new JButton("Add");
	        JButton deleteButton = new JButton("Delete");
	
	        // Create the panel for the buttons
	        JPanel buttonPanel = new JPanel();
	        buttonPanel.add(studyButton);
	        buttonPanel.add(addButton);
	        buttonPanel.add(deleteButton);
	   
	
	        // Add components to the main frame
	        frame.getContentPane().setLayout(new BorderLayout());
	        frame.getContentPane().add(new JScrollPane(flashcardList), BorderLayout.CENTER);
	        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	 
	        // Register action listeners for the buttons
	        addButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                addFlashcard(selectedSubject);
	            }
	        });
	
	        deleteButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                deleteFlashcard(selectedSubject);
	            }
	        });
	        
	     // Register action listener for flashcard selection	        
	        flashcardList.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                if (e.getClickCount() == 2) {
	                    editFlashcard();
	                }
	            }
	        });
	        
	        
	        studyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                studyFlashcards();
            }
        });
        
	        
	        // Load the flashcard subjects
	        loadFlashcards(selectedSubject);
	
	        // Show the main frame
	        frame.setVisible(true);
	        
	    }     
	}
    
    public void addFlashcard(FlashcardSubject selectedSubject) {
    	String question = JOptionPane.showInputDialog(frame, "Enter the question:");
   	 	String answer = JOptionPane.showInputDialog(frame, "Enter the answer:");	 	
	   	 if (question != null && answer != null && !question.isEmpty() && !answer.isEmpty()) {
	         Flashcard flashcard = new Flashcard(question, answer);
	         selectedSubject.addFlashcard(flashcard);
	         flashcardListModel.addElement(flashcard);
	         userAccount.saveFlashcards(username);
	     }
    }

    public void deleteFlashcard(FlashcardSubject selectedSubject) {
        int selectedIndex = flashcardList.getSelectedIndex();
        if (selectedIndex != -1) {
            Flashcard selectedFlashcard = flashcardListModel.getElementAt(selectedIndex);
            selectedSubject.removeFlashcard(selectedFlashcard);
            flashcardListModel.remove(selectedIndex);
            userAccount.saveFlashcards(username);
        }
    }
    
    public void editFlashcard() {
        int selectedIndex = flashcardList.getSelectedIndex();
        if (selectedIndex != -1) {
            Flashcard selectedFlashcard = flashcardListModel.getElementAt(selectedIndex);
            String newQuestion = JOptionPane.showInputDialog(frame, "Enter the new question:", selectedFlashcard.getQuestion());
            String newAnswer = JOptionPane.showInputDialog(frame, "Enter the new answer:", selectedFlashcard.getAnswer());

            if (newQuestion != null && newAnswer != null && !newQuestion.isEmpty() && !newAnswer.isEmpty()) {
                selectedFlashcard.setQuestion(newQuestion);
                selectedFlashcard.setAnswer(newAnswer);
                flashcardListModel.setElementAt(selectedFlashcard, selectedIndex);
                userAccount.saveFlashcards(username);
            }
        }
    }
    
    public void loadFlashcards(FlashcardSubject selectedSubject) {
        List<Flashcard> flashcards;
        flashcards = selectedSubject.getFlashcards();
        for (Flashcard flashcard : flashcards) {
            flashcardListModel.addElement(flashcard);
            userAccount.saveFlashcards(username);
        }
    }  
	    

	    
    public void studyFlashcards() {
        int selectedIndex = subjectList.getSelectedIndex();
        if (selectedIndex != -1) {
            FlashcardSubject selectedSubject = subjectListModel.getElementAt(selectedIndex);
            List<Flashcard> flashcards = selectedSubject.getFlashcards();

            if (flashcards.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No flashcards available for the selected subject.", "Information", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Create a flashcard study screen and pass the flashcards to it
                FlashcardStudyScreen studyScreen = new FlashcardStudyScreen(flashcards, flashcardListModel);
                studyScreen.startStudy();
                userAccount.saveFlashcards(username);
            }
        }
    }

	   
	   
}  
	    
	    
	   
	        
	            
	        
	        
	    
	  
	           
	

	

	        