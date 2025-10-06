import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.SecureRandom;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

public class passwordGUI {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Password Generator");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        // Create a top panel for "Password length"
        JPanel lenPanel = new JPanel();
        lenPanel.setLayout(new BoxLayout(lenPanel, BoxLayout.X_AXIS));
        lenPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        lenPanel.add(new JLabel("Password length:"));
        lenPanel.add(Box.createRigidArea(new Dimension(5, 0))); // A small space
        JTextField lenField = new JTextField(5);
        lenField.setMaximumSize(new Dimension(60, 25));
        lenPanel.add(lenField);

        // Create a new panel to hold the "Include characters" label and the checkboxes
        JPanel includePanel = new JPanel();
        includePanel.setLayout(new BoxLayout(includePanel, BoxLayout.Y_AXIS));
        includePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Add the label
        JLabel includeLabel = new JLabel("Include characters:");
        includeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        includePanel.add(includeLabel);
        
        // Add a small vertical space
        includePanel.add(Box.createRigidArea(new Dimension(0, 10))); 

        // The checkbox panel from your original code
        JPanel checkPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        checkPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JCheckBox upperBox = new JCheckBox("Uppercase");
        JCheckBox lowerBox = new JCheckBox("Lowercase");
        JCheckBox numberBox = new JCheckBox("Numbers");
        JCheckBox specialBox = new JCheckBox("Special");
        checkPanel.add(upperBox);
        checkPanel.add(lowerBox);
        checkPanel.add(numberBox);
        checkPanel.add(specialBox);
        
        // Add the checkbox panel to the new include panel
        includePanel.add(checkPanel);

        // Add the new, nested panels to the frame
        frame.add(Box.createRigidArea(new Dimension(0, 10))); // Add space at the top
        frame.add(lenPanel);
        frame.add(Box.createRigidArea(new Dimension(0, 10))); // Space between panels
        frame.add(includePanel);
        frame.add(Box.createRigidArea(new Dimension(0, 10))); // Space between panels

        JButton generateBtn = new JButton("Generate password");
        generateBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel resultLabel = new JLabel("password: ");
        resultLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel strengthLabel = new JLabel("Strength");
        strengthLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        frame.add(generateBtn);
        frame.add(Box.createRigidArea(new Dimension(0, 10)));
        frame.add(resultLabel);
        frame.add(strengthLabel);

       
        SecureRandom random = new SecureRandom();
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String special = "!@#$%^&*()-_=+[]{}|;:,.<>?";
        
        // Add a DocumentListener to the text field
        lenField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                checkLength();
            }
            public void removeUpdate(DocumentEvent e) {
            }
            public void insertUpdate(DocumentEvent e) {
                checkLength();
            }

            private void checkLength() {
                try {
                    int length = Integer.parseInt(lenField.getText());
                    if (length <= 0) {
                        JOptionPane.showMessageDialog(frame, "Password length must be greater than 0!");
                    }
                } catch (NumberFormatException ex) {
                    // This will catch non-numeric input immediately
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number!");
                }
            }
        });

     
     // Now, the ActionListener only needs to handle the password generation
     generateBtn.addActionListener(e -> {
         int length;
         try {
             length = Integer.parseInt(lenField.getText());
         } catch (NumberFormatException ex) {
             JOptionPane.showMessageDialog(frame, "Please enter a valid number!");
             return;
         }

         if (length <= 0) {
             // This is a safety check in case the user types then immediately clicks generate
             JOptionPane.showMessageDialog(frame, "Password length must be greater than 0!");
             return;
         }
            
            StringBuilder allChars = new StringBuilder();
            List<Character> passwordChars = new ArrayList<>();
            boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;
            int score = 0;

            if (upperBox.isSelected()) { allChars.append(upper); passwordChars.add(upper.charAt(random.nextInt(upper.length()))); }
            if (lowerBox.isSelected()) { allChars.append(lower); passwordChars.add(lower.charAt(random.nextInt(lower.length()))); }
            if (numberBox.isSelected()) { allChars.append(numbers); passwordChars.add(numbers.charAt(random.nextInt(numbers.length()))); }
            if (specialBox.isSelected()) { allChars.append(special); passwordChars.add(special.charAt(random.nextInt(special.length()))); }

            if (allChars.length() == 0) {
                JOptionPane.showMessageDialog(frame, "Select at least one category!");
                return;
            }

            if (length < passwordChars.size()) length = passwordChars.size();

            while (passwordChars.size() < length) {
                passwordChars.add(allChars.charAt(random.nextInt(allChars.length())));
            }
            Collections.shuffle(passwordChars, random);

            StringBuilder password = new StringBuilder();
            for (char c : passwordChars) password.append(c);

            // Strength check
            if (length >= 6) score++;
            if (length >= 9) score++;
            for (char c : passwordChars) {
                if (Character.isUpperCase(c)) hasUpper = true;
                else if (Character.isLowerCase(c)) hasLower = true;
                else if (Character.isDigit(c)) hasDigit = true;
                else hasSpecial = true;
            }
            if (hasUpper) score++;
            if (hasLower) score++;
            if (hasDigit) score++;
            if (hasSpecial) score++;

            resultLabel.setText("Password: " + password);
            if (score <= 3) strengthLabel.setText("Strength: Weak");
            else if (score < 6) strengthLabel.setText("Strength: Medium");
            else strengthLabel.setText("Strength: Strong");
        });

        frame.setVisible(true);
    }
}