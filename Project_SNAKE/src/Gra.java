
import java.io.IOException;
import javax.swing.* ;

// zrobic atak bomb, duzo bomb na mapie przez 5 sekund 
/**
 * Glowna klasa programu, tworzy okno aplikacji i ustawia jego rozmiar
 * @author Ksawery
 */
public class Gra {
	
	public Gra() throws IOException {
		MenuAplikacji okno = new MenuAplikacji() ;
		okno.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
		okno.setSize( 685, 395 ) ;
		okno.setVisible( true ) ;
		
	}
	
	public static void main(String[] args) throws IOException{
		 new Gra() ;
	}

}
