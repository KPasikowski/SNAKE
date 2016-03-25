import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


/**
 * Klasa reprezentujaca panel gry znajdujacy sie obok planszy.
 * Zawiera metody zwiazane z wyswietlaniem wyniku oraz
 * obsluga przyciskow "wroc" i "zagraj ponownie"
 * @author Ksawery
 */
public class PanelGra extends JPanel {
	
	Snake snake ; // referencja do obiektu snake'a
	Plansza plansza ; // referencja do obiektu planszy
	TrybGry trybGry ; // referencja do obiektu trybu gry
	
	JButton wroc = new JButton( "wroc" ) ; // przycisk powodujacy powrot do menu aplikacji
	JButton zagrajPonownie = new JButton( "zagraj ponownie" ) ; // przycisk uruchamiajacy ponowna gre 
	JLabel wynikGraczaNapis = new JLabel() ; // etykieta wyswietlajaca napis zalezny od fazy gry tj. poczatek, w trakcie, koniec gry
	JLabel wynikGraczaLiczba = new JLabel() ; // etykieta wyswietlajaca aktualny wynik gracza, badz napis ile zdobyl pkt po ukonczeniu gry
	
	public  PanelGra() {
		zbudujPanel() ;
		dodajSluchaczy() ;
	}
	
	/**
	 * Metoda ustawia menagera rozkladu panelu oraz dodaje do panelu
	 * wszystkie komponenty 
	 */
	private void zbudujPanel() {
		setLayout( new GridLayout( 4, 1, 10, 20 ) ) ;
		wynikGraczaNapis.setHorizontalAlignment( JLabel.CENTER ) ;
		wynikGraczaNapis.setVerticalAlignment( JLabel.CENTER ) ;
		wynikGraczaLiczba.setHorizontalAlignment( JLabel.CENTER ) ;
		wynikGraczaLiczba.setVerticalAlignment( JLabel.CENTER ) ;
		add( wynikGraczaNapis ) ;
		add( wynikGraczaLiczba ) ;
		add( zagrajPonownie ) ;
		add( wroc ) ;
	}
	
	/**
	 * Metoda dodaje sluchacza zdarzen akcji do przyciskow "wroc" i "zagraj ponownie" 
	 */
	private void dodajSluchaczy() {
		zagrajPonownie.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				poczatekGry() ;
				trybGry.rozpoczecieGry() ;	
			}
		}) ;
		wroc.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				trybGry.refOkno.wroc() ;
			}
		}) ;
	}
	
	/**
	 * Metoda wyswietla instrukcje jak rozpoczac gre na samym poczatku gry 
	 */
	public void poczatekGry() {
		wynikGraczaNapis.setText("<html>&nbsp Naciśnij s, żeby<br> rozpocząć...</html>") ;
		wynikGraczaLiczba.setText("") ;
	}
	
	/**
	 * Metoda odpowiedzialna za prawidlowe wyswietlenie aktualnego wyniku gracza
	 * @param wynik Parametr okreslajacy aktualny wynik gracza
	 */
	public void wyswietlWynik( int wynik) {
		wynikGraczaLiczba.setText(Integer.toString( wynik )) ; 	
	}
	
	/**
	 * Metoda wyswietlajaca informacje o tym, ze gra zostala zakonczona 
	 * oraz o zdobytym przez gracza wyniku
	 */
	public void koniecGry() {
		wynikGraczaNapis.setText("Koniec Gry !") ;
		wynikGraczaLiczba.setText("Zdobyłeś " + Integer.toString(snake.wynik) + " punktów" ) ;
	}
	
	/**
	 * Metoda wywolywana przy rozpoczeciu gry.
	 * Wyswietla poczatkowy wynik gracza
	 */
	public void wyzerujWynik() {
		wynikGraczaNapis.setText("Twój wynik :") ;
		wynikGraczaLiczba.setText("0") ;
	}

}
