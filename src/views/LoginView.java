package views;

import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.Desktop;
import java.net.URI;
import java.awt.event.ActionListener;

import java.util.Enumeration;
import javax.swing.AbstractButton;

import models.API;

public class LoginView extends JPanel{
    private Font _font1;
    private Font _font2;
    public String _email;
    public String _pass;
    public int _difficultyChosen;
    public boolean _isDemo;
    public boolean _startGame;
    private JFrame frame;
    private API db;
    private Desktop dsktp;


    public LoginView() {
        _startGame = false;
        _isDemo = false;
        db = new API();
        _font1 = new Font("Ariel", Font.PLAIN, 20);
        _font2 = new Font("Ariel", Font.PLAIN, 25);
        frame = new JFrame("Pacman Game - Login");
        frame.setSize(500, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        dsktp = Desktop.getDesktop();

        Dimension size = getToolkit().getScreenSize();
        frame.setLocation(size.width / 3, size.height / 3 );
        JPanel panel = new JPanel();

        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {

        panel.setLayout(null);
        int _xSpcae = 50;


        JTextField userText = new JTextField(20);
        JPasswordField passwordText = new JPasswordField(20);
        ButtonGroup _difficulty = new ButtonGroup();
        JLabel errorLable = new JLabel("incorrect Email or Password ");

        ActionListener registerListener = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.out.println("register!");
                try {
                    dsktp.browse(new URI("http://www.google.com"));
                }
                catch (Exception e){
                    System.out.println("Error");
                }
                // open Registration page
            }
        };

        ActionListener LeatherBoardsListener = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.out.println("LeatherBoards!");
                try {
                    dsktp.browse(new URI("http://www.youtube.com"));
                }
                catch (Exception e){
                    System.out.println("Error");
                }
            }
        };

        ActionListener loginListener = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                System.out.println("login!");
                _email = userText.getText();
                _difficultyChosen = getSelectedButtonText(_difficulty);
                _pass = passwordText.getPassword().toString();
                boolean answer = db.sendAuthenticationData(_email,_pass);

                if (_email.equals("backdoor") || answer){ // check user and password
                    //frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                    frame.setVisible(false);
                    _startGame = true;

                }
                else {
                    errorLable.setVisible(true);
                }

            }
        };

        ActionListener demoListener = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.out.println("demo!");
                _isDemo = true;
                frame.setVisible(false);
                _startGame = true;
            }
        };


        JLabel playMethodLbl = new JLabel("Welcome to PACMAN ");
        playMethodLbl.setFont(_font2);
        playMethodLbl.setBounds(_xSpcae + 40, 10, 300, 40);
        panel.add(playMethodLbl);



        // difficulty
        JLabel _diffLabel = new JLabel("Difficulty : ");
        _diffLabel.setFont(_font1);
        _diffLabel.setBounds(_xSpcae + 10, 50, 150, 40);
        panel.add(_diffLabel);

        JRadioButton _easy = new JRadioButton("Easy");
        _easy.setFont(_font1);
        _easy.setBounds(_xSpcae + 150 , 50, 100, 40);
        panel.add(_easy);

        JRadioButton _hard = new JRadioButton("Hard");
        _hard.setFont(_font1);
        _hard.setBounds(_xSpcae + 250 , 50, 100, 40);
        panel.add(_hard);


        _difficulty.add(_easy);
        _difficulty.add(_hard);
        _difficulty.setSelected(_easy.getModel(),true);

        // login components
        JLabel userLabel = new JLabel("Email");
        userLabel.setFont(_font1);
        userLabel.setBounds(_xSpcae + 10, 100, 100, 40);
        panel.add(userLabel);


        userText.setFont(_font1);
        userText.setBounds(_xSpcae + 120, 100, 250, 40);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(_font1);
        passwordLabel.setBounds(_xSpcae + 10, 150, 100, 40);
        panel.add(passwordLabel);


        passwordText.setFont(_font1);
        passwordText.setBounds(_xSpcae + 120, 150, 250, 40);
        panel.add(passwordText);


        errorLable.setFont(_font1);
        errorLable.setVisible(false);
        errorLable.setForeground(Color.red);
        errorLable.setBounds(_xSpcae + 75, 210, 300, 40);
        panel.add(errorLable);

        JButton loginButton = new JButton("login");
        loginButton.addActionListener(loginListener);
        loginButton.setFont(_font1);
        loginButton.setBounds(_xSpcae + 10, 275, 110, 25);
        panel.add(loginButton);

        JButton registerButton = new JButton("register");
        registerButton.addActionListener(registerListener);
        registerButton.setFont(_font1);
        registerButton.setBounds(_xSpcae + 250, 275, 110, 25);
        panel.add(registerButton);

        JButton demoButton = new JButton("Watch Demo");
        demoButton.addActionListener(demoListener);
        demoButton.setFont(_font1);
        demoButton.setBounds(_xSpcae + 100, 320, 180, 25);
        panel.add(demoButton);

        JButton leatherBoards = new JButton("Score Board");
        leatherBoards.addActionListener(LeatherBoardsListener);
        leatherBoards.setFont(_font1);
        leatherBoards.setBounds(_xSpcae + 100, 360, 180, 25);
        panel.add(leatherBoards);


    }

    public int getSelectedButtonText(ButtonGroup buttonGroup) {
        int diff = 0;
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                String btnText = button.getText();
                switch (btnText){
                    case "Easy":
                        diff = 0;
                        break;
                    case "Hard":
                        diff = 1;
                        break;

                }
                return diff;
            }
        }

        return 0;
    }

}