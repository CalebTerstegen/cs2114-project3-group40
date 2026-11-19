import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;

public class GraphicalUI
    extends JFrame
{

    private static final long serialVersionUID = 1L;
    //~ Fields ................................................................
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable() {
            public void run()
            {
                try
                {
                    GraphicalUI frame = new GraphicalUI();
                    frame.setVisible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * Create the frame.
     */
    public GraphicalUI()
    {
        setBackground(new Color(170, 121, 65));
        setTitle("Craze Coffee");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 550);
        contentPane = new JPanel();
        contentPane.setForeground(new Color(0, 0, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JButton btnNewButton = new JButton("Start Game");
        btnNewButton.setBackground(new Color(170, 121, 65));
        btnNewButton.setForeground(new Color(0, 0, 0));
        btnNewButton.setFont(new Font("Avenir Next", Font.PLAIN, 27));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnNewButton.setBounds(215, 448, 212, 68);
        contentPane.add(btnNewButton);
        
        JLabel lblNewLabel = new JLabel("CRAZE COFFEE");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Georgia", Font.PLAIN, 25));
        lblNewLabel.setBounds(233, 6, 185, 43);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Welcome Barista! ");
        lblNewLabel_1.setFont(new Font("Bangla Sangam MN", Font.PLAIN, 18));
        lblNewLabel_1.setBounds(259, 122, 136, 48);
        contentPane.add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("Serve drinks and beat the clock ");
        lblNewLabel_2.setFont(new Font("Bangla Sangam MN", Font.PLAIN, 20));
        lblNewLabel_2.setBounds(193, 199, 300, 68);
        contentPane.add(lblNewLabel_2);
        
        JLabel lblNewLabel_3 = new JLabel("Three strikes and your shift is over!");
        lblNewLabel_3.setBounds(215, 279, 331, 23);
        contentPane.add(lblNewLabel_3);
    }
}
