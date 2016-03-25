import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

/**
 * Klasa ta odpowiedzialna jest za caly przebieg gry.
 * Zawiera dwie klasy wewnetrzne kAdapter i MojTimer odpowiedzialne
 * odpowiednio za odsluchiwanie zdarzen klawiatury oraz taktowanie 
 * przebiegu gry.
 * @author Ksawery
 */
public class TrybGry extends JPanel {
	
	MenuAplikacji refOkno ; // referencja do menu aplikacji
	
	Plansza plansza = new Plansza() ;
	PanelGra panelGra = new PanelGra() ;
	Snake snake = new Snake( plansza ) ;
	kAdapter keAdapter = new kAdapter() ; 
	Timer timer1 ; // timer taktujacy gre
	MojTimer timer1_task ;
	boolean start = true ;
	boolean sluchaczKlawiaturyDodany ; // zmienna sygnalizujaca ze sluchacz zdarzen klawiatury zostal juz dodany 
	
	public TrybGry( MenuAplikacji oknoAplikacji ) {
		
		setSize( 700, 412 );
		setLayout( null ) ;
		refOkno = oknoAplikacji ;
		polaczPlansze() ; 
		polaczPanelGry() ;
		ulozKomponenty() ;
	
	}
	
	/**
	 * Dodaje sluchacza zdarzen klawiatury do obiektu trybGry i OknoAplikacji 
	 */
	public void dodajSluchaczaKlawiatury() {
		refOkno.addKeyListener( keAdapter ) ;
		addKeyListener( keAdapter ) ;
		sluchaczKlawiaturyDodany = true ;
	}
	
	/** 
	 * Usuwa sluchacza zdarzen klawiatury z obiektu trybGry i OknoAplikacji 
	 */
	public void usunSluchaczaKlawiatury() {
		refOkno.removeKeyListener( keAdapter ) ;
		removeKeyListener( keAdapter ) ;
		sluchaczKlawiaturyDodany = false ;
		
	}
	
	/**
	 * Przekazuje referencje obiektow snake'a, panelu gry oraz trybu gry do obiektu planszy
	 */
	public void polaczPlansze() {
		plansza.snake = snake ;
		plansza.panelGra = panelGra ;
		plansza.trybGry = this ;
	}
	
	/** 
	 * Przekazuje referencje obiektow snake'a, planszy oraz trybu gry do obiektu panelu gry ( panelGra )
	 */
	public void polaczPanelGry() {
		panelGra.snake = snake ;
		panelGra.plansza = plansza ;
		panelGra.trybGry = this ;
	}
		
	/** 
	 * Metoda ta rysuje GUI trybu gry, skladajace sie z panelu gry i planszy 
	 */
	public void ulozKomponenty( ) {

		plansza.setBounds( 0, 0, 530, 372 ) ;
		panelGra.setBounds( 530, 0, 156, 372 ) ;
		add( plansza ) ;
		add( panelGra ) ;
		
	}
	
	/** 
	 * Metoda przygotowuje plansze, snake'a oraz GUI trybu gry do rozpoczecia gry 
	 */
	public void rozpoczecieGry() {
		refOkno.getContentPane().removeAll() ;
		refOkno.getContentPane().repaint();
		refOkno.add( this ) ;
		refOkno.setVisible( true ) ;
		plansza.przedmiot = plansza.jablko ;
		snake.snakePoczatkowaPozycja() ;
		plansza.jablko.ustalPoczatkoweWspOwocu() ;
		snake.gora = true ;
		snake.dol = false ;
		snake.prawo = false ;
		snake.lewo = false ;
		snake.wynik = 0 ;
		dodajSluchaczaKlawiatury() ;
		panelGra.poczatekGry() ;
	}
	
	/** 
	 * Metoda wywolywana po zakonczeniu gry.
	 * Zatrzymuje glowny timer, aktualizuje wyniki i przywraca przyciski do dzialania   
	 */
	public void zakonczenieGry() {
		timer1.cancel() ;
		timer1.purge() ;
		usunSluchaczaKlawiatury() ;
		start = true ;
		panelGra.koniecGry() ;
		refOkno.obiektWyniki.dodajGracza( snake.wynik ) ;
		refOkno.obiektWyniki.aktualizujWyniki() ;
		panelGra.zagrajPonownie.setEnabled( true ) ;
		panelGra.wroc.setEnabled( true ) ;
	}
	
	/** 
	 * Klasa wewnetrzna odpowiedzialna za obslugiwanaie zdarzen klawiatury
	 * @author Ksawery
	 */
	public class kAdapter extends KeyAdapter {
		
		public void keyPressed ( KeyEvent e ) {
			
			if( e.getKeyCode() == KeyEvent.VK_S && start == true ) { // wcisniecie klawisza s, zeby rozpoczac gre
				timer1 = new Timer() ;
				timer1_task = new MojTimer() ;
				if( refOkno.obiektOpcje.wybranyPoczatkujacy )
					timer1.schedule(timer1_task, 0, 50 ) ;
				else
					timer1.schedule(timer1_task, 0, 45 ) ;
				start = false ;
				panelGra.wyzerujWynik() ;
				panelGra.zagrajPonownie.setEnabled( false ) ;
				panelGra.wroc.setEnabled( false ) ;
			}
			else if( !start ) {
				if ( e.getKeyCode() == KeyEvent.VK_UP && snake.dol == false && !start ) {
					snake.gora = true ;
					snake.lewo = false ;
					snake.prawo = false ;
				}
				else if( e.getKeyCode() == KeyEvent.VK_DOWN && snake.gora == false && !start ) {
					snake.dol = true ;
					snake.lewo = false ;
					snake.prawo = false ;
				}
				else if( e.getKeyCode() == KeyEvent.VK_RIGHT && snake.lewo == false && !start ) {
					snake.prawo = true ;
					snake.gora = false ;
					snake.dol = false ;
				}
				else if( e.getKeyCode() == KeyEvent.VK_LEFT && snake.prawo == false && !start ) {
					snake.lewo = true ;
					snake.gora = false ; 
					snake.dol = false ;
				}
				usunSluchaczaKlawiatury() ; // usuwanie sluchacza, zeby nie mozna bylo zrobic dwoch ruchow w trakcie jednego taktu timera
			}
		}
	}
	
	/** 
	 * Klasa wewnetrzna, ktora taktuje przebieg gry
	 * @author Ksawery
	 */
	public class MojTimer extends TimerTask {
		public void run () {
			
			if( !sluchaczKlawiaturyDodany )
				dodajSluchaczaKlawiatury() ;
			snake.wykonajRuch() ;
			plansza.sprawdzZdobyciePrzedmiotu() ;
			plansza.sprawdzKolizje() ;
			plansza.repaint() ;
			
		}
	}
	
}
	
	
