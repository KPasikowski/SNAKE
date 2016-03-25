import java.awt.* ;
import java.util.ArrayList ;
import java.util.Collections ;
import java.awt.event.* ;
import java.io.BufferedReader;
import java.io.PrintWriter ;
import java.io.FileNotFoundException ;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.* ;

/**
 * Klasa ta odpowiedzialna jest za wyswietlanie wynikow - top 10 na kazdym poziomie zaawansowania.
 * Jest rowniez odpowiedzialna za wpisywanie nazwy gracza przed rozpoczeciem gry.
 * @author Ksawery
 */
public class WynikiLogowanie extends JDialog {
	
	TrybGry trybGry ; // referencja do obiektu trybu gry
	Opcje obiektOpcje ; // referencja do obiektu opcji
	
	JLabel podajNick = new JLabel("Podaj nick :") ;
	JTextField nazwa = new JTextField() ; // pole do wpisania nazwy gracza
	JButton OK = new JButton("Ok") ; // przycisk potwierdzajacy wpisanie nazwy
	JLabel rankingP = new JLabel("Poziom początkujący :") ;
	JLabel rankingZ = new JLabel("Poziom zaawansowany :") ;
	JLabel[] pozycjeP = new JLabel[10] ; // tablica etykiet wyswietlajacyh poszczeglone pozycje rankingu graczy poczatk.
	JLabel[] pozycjeZ = new JLabel[10] ; // -------------------------------||---------------------------------- zaawansowanych
	ArrayList<Gracz> graczePoczatkujacy = new ArrayList<Gracz>() ; // kontener przechowujacy graczy poczatkujacych
	ArrayList<Gracz> graczeZaawansowany = new ArrayList<Gracz>() ; // kontener przechowujacy graczy zaawansowanych
	int liczbaZapisanychGraczyP ; // liczba zapisanych graczy poczatkujacych w bazie 
	int liczbaZapisanychGraczyZ ; // liczba zapisanych graczy zaawansowanych w bazie
	String tmpNazwa ; // napis przechowujacy nazwe gracza po wpisaniu w polu "nazwa"
	
	
	// obiekty odpowiedzialne za zapis i odczyt plikow txt z nazwami i wynikami graczy
	PrintWriter zapisNazwyPoczatkujacy ;
	PrintWriter zapisWynikiPoczatkujacy ;
	PrintWriter zapisNazwyZaawansowany ;
	PrintWriter zapisWynikiZaawansowany ;
    BufferedReader brN1 = new BufferedReader( new FileReader(Gra.class.getClass().getResource("/wyniki/nazwy1.txt").getPath().replaceAll("%20", " ")));
    BufferedReader brW1 = new BufferedReader( new FileReader(Gra.class.getClass().getResource("/wyniki/wyniki1.txt").getPath().replaceAll("%20", " ")));
    BufferedReader brN2 = new BufferedReader( new FileReader(Gra.class.getClass().getResource("/wyniki/nazwy2.txt").getPath().replaceAll("%20", " ")));
    BufferedReader brW2 = new BufferedReader( new FileReader(Gra.class.getClass().getResource("/wyniki/wyniki2.txt").getPath().replaceAll("%20", " ")));
    
	/**
	 * Konstruktor przekazuje referencje do obiektu trybu gry oraz opcji
	 * oraz tworzy dwie tablice typu JLabel przechowujace informacje o dotychczasowych 
	 * wynikach
	 * @param tryb Referencja do obiektu trybu gry
	 * @param opcje Referencja do obiektu opcji
	 * @throws IOException
	 */
	public WynikiLogowanie( TrybGry tryb, Opcje opcje ) throws IOException {
		trybGry = tryb ;
		obiektOpcje = opcje ;
		
		for( int i = 0; i < 10; i++ ) {
			pozycjeP[i] = new JLabel() ;
			pozycjeP[i].setHorizontalAlignment( JLabel.CENTER ) ;
			pozycjeP[i].setVerticalAlignment( JLabel.CENTER ) ;
			pozycjeZ[i] = new JLabel() ;
			pozycjeZ[i].setHorizontalAlignment( JLabel.CENTER ) ;
			pozycjeZ[i].setVerticalAlignment( JLabel.CENTER ) ;
		}
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		rankingP.setHorizontalAlignment( JLabel.CENTER ) ;
		rankingP.setVerticalAlignment( JLabel.CENTER ) ;
		rankingZ.setHorizontalAlignment( JLabel.CENTER ) ;
		rankingZ.setVerticalAlignment( JLabel.CENTER ) ;
		podajNick.setHorizontalAlignment( JLabel.CENTER ) ;
		podajNick.setVerticalAlignment( JLabel.CENTER ) ;
		nazwa.setHorizontalAlignment( JTextField.CENTER ) ;
		OK.setHorizontalAlignment( JLabel.CENTER ) ;
		OK.setVerticalAlignment( JLabel.CENTER ) ;
		dodajSluchaczy() ;
		
		odczytZPliku() ;
		liczbaZapisanychGraczyP = graczePoczatkujacy.size() ;
		liczbaZapisanychGraczyZ = graczeZaawansowany.size() ;
	}
	
	/**
	 * Metoda dodaje sluchacza zdarzen klawiatury do przycisku i pola JTextfield
	 */
	private void dodajSluchaczy() {
		OK.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				if( nazwa.getText().equals("") ) {
					
				}
				else {
					if( !czyJestTakiGracz( nazwa.getText() ) ) { // sprawdza czy jest juz gracz o takiej nazwie w bazie
						if( nazwa.getText().length() >= 15 ) // jesli nazwa jest wieksza niz 15 znakow to skraca do 15
							tmpNazwa = nazwa.getText().substring(0,15) + "... - "; 
						else
							tmpNazwa = nazwa.getText() + " - " ;
						nazwa.setText("") ;
						wyczyscOkno() ;
						dispose() ;
						trybGry.rozpoczecieGry() ;
					}
					else {
						JDialog blad = new JDialog() ; // jesli jest taki gracz to generuje okno z odpowiednim napisem
						blad.setSize( 250, 100 ) ;
						blad.add( new JLabel( " Jest juz taki gracz na liscie wynikow ") ) ;
						blad.setLocationRelativeTo(null) ;
						blad.setVisible( true ) ;
					}
				}
			}
		}) ;
	}
	
	/** 
	 * Metoda dodaje gracza do listy graczow poczatkujacych lub zaawansowanych ( zaleznie )
	 * po ukonczeniu gry
	 * @param wyn Uzyskany wynik przez gracza
	 */
	public void dodajGracza( int wyn ) {
		Gracz gracz = new Gracz() ;
		gracz.nazwa = tmpNazwa ;
		gracz.wynik = wyn ;
		if( obiektOpcje.wybranyPoczatkujacy ) {
			graczePoczatkujacy.add( gracz ) ;
			liczbaZapisanychGraczyP++ ;
		}
		else {
			graczeZaawansowany.add( gracz ) ;
			liczbaZapisanychGraczyZ++ ;
		}
	}
	
	/**
	 * Metoda sortuje listy graczy wzgledem uzyskanego wyniku ( malejaco ).
	 * Jesli na liscie znajduje sie 11 graczy to usuwany jest ostatni
	 * ( z najgorszym wynikiem ).
	 */
	public void aktualizujWyniki(){
		Collections.sort( graczePoczatkujacy ) ;
			if( graczePoczatkujacy.size() == 11 ){
				graczePoczatkujacy.remove( 10 ) ;
				liczbaZapisanychGraczyP-- ;
			}
		Collections.sort( graczeZaawansowany ) ;
			if( graczeZaawansowany.size() == 11 ){
				graczeZaawansowany.remove( 10 ) ;
				liczbaZapisanychGraczyZ-- ;
			}
	}	
	
	/**
	 * Buduje GUI okna dialogowego w przypadku logowania do gry, a nastepnie je wyswietla
	 */
	public void logowanie() {
		setSize( 200, 200 ) ;
		setLayout( new GridLayout( 3, 1 )) ;
		setLocationRelativeTo( null ) ;
		addWindowListener( new WindowAdapter() {
			public void windowClosing( WindowEvent e ) {
				wyczyscOkno() ;
				e.getWindow().dispose();
			}
		});
		add( podajNick ) ;
		add( nazwa ) ;
		add( OK ) ;
		setVisible( true ) ;
	}
	
	/**
	 * Sprawdza czy na liscie graczy jest juz gracz o takie samej nazwie
	 * jak wpisana przez uzytkownika
	 * @param nazwa
	 * @return Zwraca prawde jesli jest taki gracz, falsz w przeciwnym wypadku
	 */
	private boolean czyJestTakiGracz( String nazwa ) {
		if( obiektOpcje.wybranyPoczatkujacy ) { // jesli jest wybrany poziom poczatkujacy to szuka w bazie graczy poczatk.
			for( int i = 0; i < liczbaZapisanychGraczyP; i++ ) {
				if( graczePoczatkujacy.get(i).nazwa.equals( nazwa + " - " ) )
					return true ;
			}
			return false ;
		}
		else { // w przeciwnym wypadku szuka w bazie  graczy zaawansowanych
			for( int i = 0; i < liczbaZapisanychGraczyZ; i++ ) {
				if( graczeZaawansowany.get(i).nazwa.equals( nazwa + " - " ) )
					return true ;
			}
			return false ;
		}
	}
	
	/**
	 * Metoda buduje GUI okna dialogowego, ktore wyswietla top 10 najlepszych 
	 * dotychczasowych wynikow na obu poziomach zaawansowania
	 */
	public void wynikiWyswietl() {
		setSize( 500, 330 ) ;
		setLayout( new FlowLayout(FlowLayout.CENTER, 70, 10) ) ;
		JPanel panel1 = new JPanel() ; // panel wyswietlajacy ranking graczy poczatkujacych
		JPanel panel2 = new JPanel() ; // panel wyswietlajacy ranking graczy zaawansowanych
		panel1.setLayout( new GridLayout( 11, 1, 10, 10 ) ) ;
		panel2.setLayout( new GridLayout( 11, 1, 10, 10 ) ) ;
		setLocationRelativeTo(null) ;
		addWindowListener( new WindowAdapter() { // dodaje sluchacza zdarzen do okna dialogowego
			public void windowClosing( WindowEvent e ) { // funkcja oblsugujaca zdarzenie zamkniecia okna 
				wyczyscOkno() ;
				e.getWindow().dispose();
			}
		});
		panel1.add( rankingP ) ; // dodawanie etykiet do paneli 
		panel2.add( rankingZ ) ;
		for( int i = 1; i < liczbaZapisanychGraczyP + 1 ; i++) {
			pozycjeP[i-1].setText( i + ". " + graczePoczatkujacy.get( i-1 ).nazwa + " " + graczePoczatkujacy.get( i-1 ).wynik  ) ;
			panel1.add( pozycjeP[i-1] ) ;
		}
		for( int i = 1; i < liczbaZapisanychGraczyZ + 1 ; i++) {
			pozycjeZ[i-1].setText( i + ". " + graczeZaawansowany.get( i-1 ).nazwa + " " + graczeZaawansowany.get( i-1 ).wynik  ) ;
			panel2.add( pozycjeZ[i-1] ) ;
		}
		add( panel1 ) ; // dodawanie paneli do okna dialogowego
		add( panel2 ) ;
		setVisible( true ) ;
		
	}
	
	/**
	 * Zdejmuje wszystkie komponenty z okna dialogowego
	 */
	public void wyczyscOkno() {
		getContentPane().removeAll() ;
	}
	
	/** Metoda ta jest odpowiedzialna za odczyt top 10 graczy na obu poziomach
	 * zaawansowania zaraz po wlaczeniu aplikacji.
	 * Odczyt jest przeprowadzany przy pomocy klasy BufferedReader, ktorej obiekty sa
	 * polaczone z odpowiednimi plikami txt przechowujacymi dane o graczach.
	 * @throws IOException
	 */
	private void odczytZPliku() throws IOException {
		String tmpNazwa ;
		String tmpWynik ;
		int wynik ;
		int indexP = 0 ;
		int indexZ = 0 ;
		
			while( true ){ // odczyt plikow z graczami poczatkujacymi

				tmpNazwa = brN1.readLine() ; // czyta pojedyncza linie z pliku z nazwami
				tmpWynik = brW1.readLine() ; // czyta pojedyncza linie z pliku z wynikami
				if( tmpNazwa == null) 
					break;
				wynik = Integer.parseInt( tmpWynik ) ; // parsowanie liczby
				graczePoczatkujacy.add( new Gracz() ) ; // dodanie gracza do bazy
				graczePoczatkujacy.get(indexP).nazwa = tmpNazwa ; // przypisanie nazwy nowo dodanemu graczowi
				graczePoczatkujacy.get(indexP).wynik = wynik ; // przypisanie wyniku
				indexP++ ;
			}
		
			while( true ){ // odczyt plikow z graczami zaawansowanymi

				tmpNazwa = brN2.readLine() ; // czyta pojedyncza linie z pliku z nazwami
				tmpWynik = brW2.readLine() ; // czyta pojedyncza linie z pliku z wynikami
				if( tmpNazwa == null) 
					break;
				wynik = Integer.parseInt( tmpWynik ) ; // parsowanie liczby
				graczeZaawansowany.add( new Gracz() ) ; // dodanie gracza do bazy
				graczeZaawansowany.get(indexZ).nazwa = tmpNazwa ; // przypisanie nazwy nowo dodanemu graczowi
				graczeZaawansowany.get(indexZ).wynik = wynik ; // przypisanie wyniku
				indexZ++ ;
			}
		
	}
	
	/**
	 * Metoda ta jest odpowiedzialna za zapis danych o top 10 graczy ( ich nazwa i wynik ) do odpowiednich
	 * plikow txt. Odbywa sie to za pomoca klasy PrintWriter.
	 * Metoda jest wywolywana w trakcie zamykania okna calej aplikacji
	 * @throws FileNotFoundException
	 */
	public void zapisDoPliku() throws FileNotFoundException {
		
		zapisNazwyPoczatkujacy = new PrintWriter( Gra.class.getClass().getResource("/wyniki/nazwy1.txt").getPath().replaceAll("%20", " ")) ;
		zapisNazwyZaawansowany = new PrintWriter( Gra.class.getClass().getResource("/wyniki/nazwy2.txt").getPath().replaceAll("%20", " ")) ;
		zapisWynikiPoczatkujacy = new PrintWriter( Gra.class.getClass().getResource("/wyniki/wyniki1.txt").getPath().replaceAll("%20", " ")) ;
		zapisWynikiZaawansowany = new PrintWriter( Gra.class.getClass().getResource("/wyniki/wyniki2.txt").getPath().replaceAll("%20", " ")) ;
		
		
			for( int i = 0; i < liczbaZapisanychGraczyP; i++ ) { // zapis do plikow danych graczy poczatkujacych
				if( i == liczbaZapisanychGraczyP - 1 ) {
					zapisNazwyPoczatkujacy.print( graczePoczatkujacy.get(i).nazwa ) ;
					zapisWynikiPoczatkujacy.print( graczePoczatkujacy.get(i).wynik ) ;
				}
				else {
					zapisNazwyPoczatkujacy.println( graczePoczatkujacy.get(i).nazwa ) ;
					zapisWynikiPoczatkujacy.println( graczePoczatkujacy.get(i).wynik ) ;
				}
			}
		zapisNazwyPoczatkujacy.close() ;
		zapisWynikiPoczatkujacy.close() ;
		
			
			for( int i = 0; i < liczbaZapisanychGraczyZ; i++ ) { // zapis do plikow danych graczy zaawansowanych
				if( i == liczbaZapisanychGraczyZ - 1 ) {
					zapisNazwyZaawansowany.print( graczeZaawansowany.get(i).nazwa ) ;
					zapisWynikiZaawansowany.print( graczeZaawansowany.get(i).wynik ) ;
				}
				else {
					zapisNazwyZaawansowany.println( graczeZaawansowany.get(i).nazwa ) ;
					zapisWynikiZaawansowany.println( graczeZaawansowany.get(i).wynik ) ;
				}
			}
		zapisNazwyZaawansowany.close() ;
		zapisWynikiZaawansowany.close() ;
		
	}
	
}
	
