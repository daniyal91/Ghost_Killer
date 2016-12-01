package views;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * Main View for the program.
 *
 * @author SnapDragon
 *
 */
public class MainView extends JFrame {

    public JButton buttonNewGame;

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;

    /**
     * Constructs the MainView object.
     *
     * @param mainController The controller receiving the user inputs.
     * @roseuid 5837CC8C0229
     */
    public MainView(ActionListener mainController) {

        this.setTitle("Ghost-Killer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100, 100, 370, 230);
        this.setResizable(false);

        contentPane = new JPanel();
        contentPane.setBackground(Color.DARK_GRAY);
        contentPane.setBorder(new EmptyBorder(6, 6, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        JLabel spacer = new JLabel("                        ");
        spacer.setForeground(Color.white);
        contentPane.setLayout(new FlowLayout()); 
        contentPane.add(spacer);
        
        JLabel members = new JLabel("<html>Daniyal Rao <br> Sheikh Khaled Reza <br> Khushbu Randive <br> Hiral Bhut <br> Muhammad Taha Qureshi</html>");
        members.setForeground(Color.white);
        contentPane.setLayout(new FlowLayout()); 
        contentPane.add(members);

        this.buttonNewGame = new JButton("New game");
        this.buttonNewGame.setBounds(110, 200, 140, 23);
        this.buttonNewGame.addActionListener(mainController);
        contentPane.add(this.buttonNewGame);

    }

}
