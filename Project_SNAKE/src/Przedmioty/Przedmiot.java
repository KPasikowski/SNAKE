import java.awt.Image;
import java.util.Random;

/**
 * Klasa abstrakcyjna zawierajaca dwie abstrakcyjne metody zdefiniowane
 * w kazdej klasie, ktora ja rozszerza. 
 * Zawiera rowniez informacje o aktualnym polozeniu danego przedmiotu na planszy
 * @author Ksawery
 */
public abstract class Przedmiot {
	
	int polozenieX ;
	int polozenieY ;
	Image obrazekPrzedmiotu ;
	Random losuj = new Random() ;
	int liczbaOdlamkow ;

	public Przedmiot() {
		
	}
	
	public abstract void ustalWspPrzedmiotu() ;
	public abstract int dajBonus() ;
	
}
