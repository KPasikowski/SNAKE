import java.util.Random;

import javax.swing.ImageIcon;

/**
 * Klasa reprezentujaca mala bombe.
 * Zawiera informacje o karze punktowej ( w procentach ) jaka daje mala bomba
 * @author Ksawery
 */
public class MalaBomba extends Przedmiot {
	
	int karaMalaBomba = 10 ; // kara w procentach jaka daje mala bomba 

	public MalaBomba() {
		
		ImageIcon mb = new ImageIcon(this.getClass().getResource("/obrazki/malaBomba.jpg")) ; // wczytywanie obrazka malej bomby
		obrazekPrzedmiotu = mb.getImage() ;
		liczbaOdlamkow = 50 ;
		
	}
	
	/**
	 * Metoda losuje wspolrzedne malej bomby.
	 * Moze ona sie pojawic w kazdym miejscu na planszy
	 * @see Przedmiot#ustalWspPrzedmiotu()
	 */
	public void ustalWspPrzedmiotu() {
		polozenieX = ( losuj.nextInt( 37 ) + 1 ) * 10 ;
		polozenieY = ( losuj.nextInt( 21 ) + 1 ) * 10 ;
		
	}

	/**
	 * Metoda zwraca kare procentowa, jaka daje mala bomba
	 * @see Przedmiot#dajBonus()
	 */
	public int dajBonus() {
		return karaMalaBomba ;
	}

}
