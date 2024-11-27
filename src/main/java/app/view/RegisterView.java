package app.view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import app.interface_adapter.register.RegisterController;
import app.interface_adapter.register.RegisterState;
import app.interface_adapter.register.RegisterViewModel;

public class RegisterView extends JPanel implements PropertyChangeListener {
    private static final String VIEW_NAME = "register";

    private final RegisterViewModel registerViewModel;
    private final JTextField usernameInputField;
    private final JTextField emailInputField;
    private final JPasswordField passwordInputField;
    private final JButton signupButton;
    private final JButton loginButton;
    private RegisterController registerController;

    public RegisterView(RegisterViewModel registerViewModel) {
        this.registerViewModel = registerViewModel;
        this.registerViewModel.addPropertyChangeListener(this);

        // Configure panel layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.decode("#D3D3D3")); // Light gray background
        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Add "EventureUofT" heading
        JLabel titleLabel = new JLabel("EventureUofT", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.decode("#2E4C34"));

        // Build input fields
        usernameInputField = createInputField("username");
        emailInputField = createInputField("email");
        passwordInputField = createPasswordField();

        // Create buttons
        signupButton = createButton("Sign up", evt -> handleSignupAction());
        loginButton = createButton("Log in", evt -> handleLoginAction());

        // Add listeners to input fields
        addDocumentListener(usernameInputField, () -> updateState("username"));
        addDocumentListener(emailInputField, () -> updateState("email"));
        addDocumentListener(passwordInputField, () -> updateState("password"));

        // Add components to panel
        this.add(Box.createVerticalStrut(10));
        this.add(titleLabel);
        this.add(Box.createVerticalStrut(20));
        this.add(createLabelTextPanel("username", usernameInputField));
        this.add(Box.createVerticalStrut(10));
        this.add(createLabelTextPanel("email", emailInputField));
        this.add(Box.createVerticalStrut(30));
        this.add(createLabelTextPanel("password", passwordInputField));
        this.add(Box.createVerticalStrut(50));
        this.add(createButtonPanel());
    }

    private JTextField createInputField(String placeholder) {
        JTextField inputField = new JTextField(20);
        configureInputField(inputField, placeholder);
        return inputField;
    }

    private JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField(20);
        configureInputField(passwordField, "password");
        return passwordField;
    }

    private void configureInputField(JTextComponent inputField, String placeholder) {
        inputField.setPreferredSize(new Dimension(400, 30));
        inputField.setMaximumSize(new Dimension(400, 30));
        inputField.setMinimumSize(new Dimension(400, 30));
        inputField.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputField.setForeground(Color.GRAY);
        inputField.setText(placeholder);

        // Placeholder behavior
        inputField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (inputField.getText().equals(placeholder)) {
                    inputField.setText("");
                    inputField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (inputField.getText().isEmpty()) {
                    inputField.setForeground(Color.GRAY);
                    inputField.setText(placeholder);
                }
            }
        });
    }

    private JButton createButton(String text, java.awt.event.ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 40));
        button.setMaximumSize(new Dimension(150, 40));
        button.setBackground(Color.decode("#48BF67"));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        button.addActionListener(actionListener);

        // Add hover effect (simply make bg darker)
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#2E7A46"));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#48BF67"));
            }
        });

        return button;
    }


    private JPanel createLabelTextPanel(String labelText, JTextComponent inputField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel label = new JLabel(labelText, JLabel.LEFT);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(inputField);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);

        return buttonPanel;
    }

    private void addDocumentListener(JTextComponent inputField, Runnable updateAction) {
        inputField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateAction.run();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateAction.run();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateAction.run();
            }
        });
    }

    private void updateState(String fieldType) {
        RegisterState currentState = registerViewModel.getState();
        if ("username".equals(fieldType)) {
            currentState.setUsername(usernameInputField.getText());
        } else if ("password".equals(fieldType)) {
            currentState.setPassword(new String(passwordInputField.getPassword()));
        } else if ("email".equals(fieldType)){
            currentState.setEmail(emailInputField.getText());
        }
        registerViewModel.setState(currentState);
    }

    private void handleSignupAction() {
        RegisterState currentState = registerViewModel.getState();
        registerController.execute(currentState.getUsername(), currentState.getEmail(), currentState.getPassword());
    }

    private void handleLoginAction() {
//        navigateTo("login");
        registerController.switchToLoginView();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        RegisterState state = (RegisterState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }

    public String getViewName() {
        return VIEW_NAME;
    }

    public void setRegisterController(RegisterController controller) {
        this.registerController = controller;
    }
}
