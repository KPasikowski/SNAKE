import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * Klasa tworzy osobne okno z instrukcja do gry
 * @author Ksawery
 */
public class Instrukcja extends JDialog {
	
	JLabel obrazInstrukcja = new JLabel( new ImageIcon("instrukcja.png") ) ; // wczytuje obrazek png instrukcji
	
	public Instrukcja() {
		setSize( 550, 600 ) ;
		setLayout( new BorderLayout() ) ;
		setLocationRelativeTo(null) ;
		add( obrazInstrukcja ) ;
	}
	
	public void pokazInstrukcje() {
		
		setVisible( true ) ;
		
	}
	
}
