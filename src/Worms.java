import javax.swing.JFrame;
public class Worms extends JFrame {

	private static final long serialVersionUID = 1L;//pffkp

	public Worms() {
        add(new AppWindow());
        setTitle("Worms");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1600, 900);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    
    public static void main(String[] args) {
        new Worms();
    }
    
}
