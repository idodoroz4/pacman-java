package views;

import sun.util.resources.fi.CalendarData_fi;

import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.*;

public class LoginView extends JPanel{
    private Font _font1;
    private Font _font2;
    public LoginView() {

        _font1 = new Font("Ariel", Font.PLAIN, 20);
        _font2 = new Font("Ariel", Font.PLAIN, 25);
        JFrame frame = new JFrame("Pacman Game - Login");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension size = getToolkit().getScreenSize();
        frame.setLocation(size.width / 2, size.height / 2 );
        JPanel panel = new JPanel();

        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {

        panel.setLayout(null);
        int _xSpcae = 50;


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

        ButtonGroup _difficulty = new ButtonGroup();
        _difficulty.add(_easy);
        _difficulty.add(_hard);
        _difficulty.setSelected(_easy.getModel(),true);

        // login components
        JLabel userLabel = new JLabel("Email");
        userLabel.setFont(_font1);
        userLabel.setBounds(_xSpcae + 10, 100, 100, 40);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setFont(_font1);
        userText.setBounds(_xSpcae + 120, 100, 250, 40);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(_font1);
        passwordLabel.setBounds(_xSpcae + 10, 150, 100, 40);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setFont(_font1);
        passwordText.setBounds(_xSpcae + 120, 150, 250, 40);
        panel.add(passwordText);

        JButton loginButton = new JButton("login");
        loginButton.setFont(_font1);
        loginButton.setBounds(_xSpcae + 10, 210, 110, 25);
        panel.add(loginButton);

        JButton registerButton = new JButton("register");
        registerButton.setFont(_font1);
        registerButton.setBounds(_xSpcae + 250, 210, 110, 25);
        panel.add(registerButton);

        JButton demoButton = new JButton("Watch Demo");
        demoButton.setFont(_font1);
        demoButton.setBounds(_xSpcae + 110, 280, 180, 25);
        panel.add(demoButton);



        /*
        JLabel userLabel = new JLabel("Email");
        userLabel.setFont(new Font("Ariel", Font.PLAIN, 20));
        userLabel.setBounds(10, 10, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 10, 160, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Ariel", Font.PLAIN, 20));
        passwordLabel.setBounds(10, 40, 200, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 40, 160, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);

        JButton registerButton = new JButton("register");
        registerButton.setBounds(180, 80, 80, 25);
        panel.add(registerButton);

        */



    }

}