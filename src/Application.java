import java.awt.EventQueue;
import javax.swing.JFrame;

public class Application extends JFrame {

	public Application() {

        initUI();
    }
	
	private void initUI() {

		add(new Board());
        
        setSize(800, 600);
        setResizable(false);
        
        setTitle("Asteroids");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		EventQueue.invokeLater(() -> {
            Application ex = new Application();
            ex.setVisible(true);
        });
	}

}
