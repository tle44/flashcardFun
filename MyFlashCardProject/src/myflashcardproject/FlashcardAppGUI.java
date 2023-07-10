package myflashcardproject;

import java.awt.EventQueue;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;

import javax.swing.plaf.ColorUIResource;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.border.EmptyBorder;

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
	    private FlashcardSubject subject;
	    private FlashcardSubject selectedSubject;
	    private DefaultTableModel flashcardTableModel;
	    private List<Flashcard> flashcards;
	    private Flashcard selectedFlashcard;
	
		private JTextField usernameField;
		private JPasswordField passwordField;
		private JLabel passwordLabel;
		private JTable flashcardTable;
		private JFrame editFrame;

	   
	    private static final Color PRIMARY_COLOR = new Color(52, 152, 219);
	    private static final Color BACKGROUND_COLOR = new Color(246, 247, 251);
	    private static final Font LABEL_FONT = new Font("Serif", Font.PLAIN, 24);
	    private static final Font FIELD_FONT = new Font("Serif", Font.PLAIN, 20);
	    private static final Color textColor = new Color(46, 56, 86); 
	    private static final Color buttonColor = new Color(253, 205, 30);
	    

	    
	    public FlashcardAppGUI() {
	        initializeLoginScreen();
	        flashcardApp.loadAccountsFromFile();
	        
	    }

	    private void initializeLoginScreen() {
	    	frame = new JFrame();
	    	
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setLayout(null);
			frame.setSize(710, 500);
			
			frame.getContentPane().setBackground(BACKGROUND_COLOR);
			
			JLabel usernameLabel = new JLabel("Username");
			usernameLabel.setFont(LABEL_FONT);
			usernameLabel.setBounds(81, 146, 108, 42);
			usernameLabel.setForeground(textColor);
			frame.getContentPane().add(usernameLabel);
			
			passwordLabel = new JLabel("Password");
			passwordLabel.setFont(LABEL_FONT);
			passwordLabel.setBounds(81, 226, 108, 34);
			passwordLabel.setForeground(textColor);
			frame.getContentPane().add(passwordLabel);
			
			usernameField = new JTextField();
			usernameField.setBounds(229, 146, 340, 49);
			frame.getContentPane().add(usernameField);
			usernameField.setColumns(10);
			
			passwordField = new JPasswordField();
			passwordField.setColumns(10);
			passwordField.setBounds(229, 222, 340, 49);
			frame.getContentPane().add(passwordField);
			
			JButton loginButton = new JButton("Login");
			loginButton.setFont(LABEL_FONT);
			loginButton.setBounds(228, 321, 129, 49);
			buttonCustom(loginButton);
		
			frame.getContentPane().add(loginButton);
			
			JButton registerButton = new JButton("Register");
			registerButton.setFont(LABEL_FONT);
			registerButton.setBounds(440, 321, 129, 49);
			buttonCustom(registerButton);
			frame.getContentPane().add(registerButton);
			
			JLabel welcomeLabel = new JLabel("Welcome to Flashcard Fun");
			welcomeLabel.setFont(LABEL_FONT);
			welcomeLabel.setBounds(229, 41, 268, 34);
			welcomeLabel.setForeground(textColor);
			frame.getContentPane().add(welcomeLabel);
			
			loginButton.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			        String username = usernameField.getText();
			        String password = new String(passwordField.getPassword());

			        // Check if username or password is empty
			        if (username.isEmpty() || password.isEmpty()) {
			            JOptionPane.showMessageDialog(frame, "Please enter a username and password!", "Error", JOptionPane.ERROR_MESSAGE);
			            return; // Exit the method to prevent further execution
			        }

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
	                
	                // Check if username or password is empty
			        if (username.isEmpty() || password.isEmpty()) {
			            JOptionPane.showMessageDialog(frame, "Please enter a username and password for registration", "Error", JOptionPane.ERROR_MESSAGE);
			            return; // Exit the method to prevent further execution
			        }

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
	    	 frame.setSize(1650,1080);	    	
	    	 frame.setVisible(true);


	        // Create the subject list
	        subjectListModel = new DefaultListModel<>();
	        subjectList = new JList<>(subjectListModel);
	        subjectList.setCellRenderer(new DefaultListCellRenderer() {

                @Override
                public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {                
                	JLabel renderer = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    // Set the font and size of the text
                    renderer.setFont(FIELD_FONT); // Customize the font and size
                    // Set the background color based on the row index
                    if (index % 2 == 0) {
                        renderer.setBackground(BACKGROUND_COLOR);
                    } else {
                        renderer.setBackground(Color.WHITE);
                    }
                    // Set the preferred height of the renderer component
					int customHeight = 50;  // Customize the desired height
			        renderer.setPreferredSize(new Dimension(renderer.getPreferredSize().width, customHeight));
                
			        // Set the text color
			        renderer.setForeground(textColor); // Customize the desired text color
			        
					return renderer;
                }

           });
	            

	       
	       
	   

	        // Create the buttons	      
	        JButton studyButton = new JButton("Study Flashcards");
	        buttonCustom(studyButton);
	        
	        
	        JButton addButton = new JButton("Add Subject");
	        buttonCustom(addButton);
	        
	        JButton deleteButton = new JButton("Delete Subject");
	        buttonCustom(deleteButton);

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
	    public void buttonCustom2(JButton button) {
	        button.setBackground(Color.WHITE);
	        button.setForeground(buttonColor);
	    }
	    
	    public void addSubject() {
	        String subjectName = JOptionPane.showInputDialog(frame, "Enter subject name:");
	        if (subjectName != null && !subjectName.isEmpty()) {
	            subject = new FlashcardSubject(subjectName);
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
	                selectedSubject = subjectListModel.getElementAt(selectedIndex);
	                List<Flashcard> flashcards = selectedSubject.getFlashcards();

	                // Create the main frame
	                frame = new JFrame("Flashcards - " + selectedSubject.getSubjectName());
	                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	                frame.setSize(1650, 1080);

	                // Create the flashcard table model
	                flashcardTableModel = new DefaultTableModel(new Object[]{"Question", "Answer"}, 0);
	                flashcardTable = new JTable(flashcardTableModel);

	                // Customize the table appearance
	                flashcardTable.setFont(FIELD_FONT); // Customize the font and size
	                flashcardTable.setRowHeight(60); // Customize the row height
	                flashcardTable.setBackground(BACKGROUND_COLOR);
	                flashcardTable.setForeground(textColor); // Customize the text color

	                // Add flashcards to the table model
	                for (Flashcard flashcard : flashcards) {
	                    flashcardTableModel.addRow(new Object[]{flashcard.getQuestion(), flashcard.getAnswer()});
	                }

	                // Add the table to a scroll pane
	                JScrollPane scrollPane = new JScrollPane(flashcardTable);
	                // Create the buttons
	    	        JButton studyButton = new JButton("Study Flashcards");
	    	        buttonCustom(studyButton);
	    	        JButton addButton = new JButton("Add Flashcard");
	    	        buttonCustom(addButton);
	    	        JButton deleteButton = new JButton("Delete Flashcard");
	    	        buttonCustom(deleteButton);
	    	        JButton editButton = new JButton("Edit Flashcard");
	    	        buttonCustom(editButton);
	    	
	    	        // Create the panel for the buttons
	    	        JPanel buttonPanel = new JPanel();
	    	        buttonPanel.add(studyButton);
	    	        buttonPanel.add(addButton);
	    	        buttonPanel.add(deleteButton);
	    	        buttonPanel.add(editButton);

	                // Add the scroll pane to the frame
	                frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
	                frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

	                // Show the main frame
	                frame.setVisible(true);
	            

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
	        

	        editButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                editFlashcard();
	            }
	        });
	        
	        
	        studyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                studyFlashcards();
            }
        });
        
	
	        // Show the main frame
	        frame.setVisible(true);
	        
	    }     }
	
    
    public void addFlashcard(FlashcardSubject selectedSubject) {
    	String question = JOptionPane.showInputDialog(frame, "Enter the question:");
   	 	String answer = JOptionPane.showInputDialog(frame, "Enter the answer:");	 	
	   	 if (question != null && answer != null && !question.isEmpty() && !answer.isEmpty()) {
	   		
	         Flashcard flashcard = new Flashcard(question, answer);
	         selectedSubject.addFlashcard(flashcard);
	         flashcardTableModel.addRow(new Object[]{flashcard.getQuestion(), flashcard.getAnswer()});

	         userAccount.saveFlashcards(username);
	     }
    }

    public void deleteFlashcard(FlashcardSubject selectedSubject) {
    	 int selectedIndex = flashcardTable.getSelectedRow();
    	    if (selectedIndex != -1) {
    	        List<Flashcard> flashcards = selectedSubject.getFlashcards();
    	        if (selectedIndex < flashcards.size()) {
    	            Flashcard selectedFlashcard = flashcards.get(selectedIndex);
    	            selectedSubject.removeFlashcard(selectedFlashcard);
    	            flashcardTableModel.removeRow(selectedIndex);
    	            userAccount.saveFlashcards(username);
    	        }
    	    }
    }
    

    public void editFlashcard() {
        int selectedRow = flashcardTable.getSelectedRow();
        if (selectedRow != -1) {
            flashcards = selectedSubject.getFlashcards();
            if (selectedRow < flashcards.size()) {
                selectedFlashcard = flashcards.get(selectedRow);
                int questionColumnIndex = 0; 
                int answerColumnIndex = 1; 
                
                // Create a dialog window for editing
                JDialog editDialog = new JDialog();
                editDialog.setTitle("Edit Flashcard");
                editDialog.setSize(400, 200);
                editDialog.setLayout(new BorderLayout());

                // Create question label and text field
                JLabel questionLabel = new JLabel("Question:");
                JTextArea questionField = new JTextArea(selectedFlashcard.getQuestion());

                // Create answer label and text field
                JLabel answerLabel = new JLabel("Answer:");
                JTextArea answerField = new JTextArea(selectedFlashcard.getAnswer());

                // Create update button
                JButton updateButton = new JButton("Update");
                buttonCustom(updateButton);
               
                updateButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String newQuestion = questionField.getText();
                        selectedFlashcard.setQuestion(newQuestion);
                        flashcardTableModel.setValueAt(newQuestion, selectedRow, questionColumnIndex);

                        String newAnswer = answerField.getText();
                        selectedFlashcard.setAnswer(newAnswer);
                        flashcardTableModel.setValueAt(newAnswer, selectedRow, answerColumnIndex);

                        editFrame.dispose();

                        //update the flashcards list
                        flashcards.set(selectedRow, selectedFlashcard);
                        userAccount.saveFlashcards(username);
                    }
                });
 

     

                // Create the frame
                editFrame = new JFrame("Edit Frame");

                // Create the panel
                JPanel panel = new JPanel(new GridBagLayout());
                panel.setBackground(BACKGROUND_COLOR);
                // Set the desired margins
                int topMargin = 20;
                int leftMargin = 20;
                int bottomMargin = 20;
                int rightMargin = 20;

                // Create an EmptyBorder with the specified margins
                EmptyBorder border = new EmptyBorder(topMargin, leftMargin, bottomMargin, rightMargin);

                // Set the border for the panel
                panel.setBorder(border);

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 0;
                panel.add(questionLabel, gbc);
                questionLabel.setFont(LABEL_FONT);

                gbc.gridx = 1;
                gbc.gridwidth = 1;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;
                gbc.fill = GridBagConstraints.BOTH;
              
                questionField.setLineWrap(true); // Enable text wrapping
                JScrollPane questionScrollPane = new JScrollPane(questionField);
                panel.add(questionScrollPane, gbc);
                questionField.setFont(FIELD_FONT);

                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 1;
                gbc.weightx = 0.0;
                gbc.weighty = 0.0;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                panel.add(answerLabel, gbc);
                answerLabel.setFont(LABEL_FONT);

                gbc.gridx = 1;
                gbc.gridwidth = 1;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;
                gbc.fill = GridBagConstraints.BOTH;
               
                answerField.setLineWrap(true); // Enable text wrapping
                JScrollPane answerScrollPane = new JScrollPane(answerField);
                panel.add(answerScrollPane, gbc);
                answerField.setFont(FIELD_FONT);

                gbc.gridx = 1;
                gbc.gridy = 2;
                gbc.gridwidth = 2;
                gbc.weightx = 0.0;
                gbc.weighty = 0.0;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                panel.add(updateButton, gbc);
                
                editFrame.add(panel);
                editFrame.setSize(600, 400); // Set the size of the frame
                editFrame.setVisible(true);
   
            
            }
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
                FlashcardStudyScreen studyScreen = new FlashcardStudyScreen(flashcards, flashcardTableModel,userAccount, username);
                studyScreen.startStudy();
                userAccount.saveFlashcards(username);
            }
        }
    }	   
}
	    	
	   
	        
	            
	        
	        
	    
	  
	           
	

	

	        