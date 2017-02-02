package Blackjack;
import java.util.*;
import java.util.concurrent.TimeUnit;

/*FUTURE FEATURES & IDEAS
 * - variable amount of card decks (52*x)
 * - setup screen
 * - better greeephics
 * - GUI
 * - MP
 * - BLACKJACK
 * 
 * 
 * 
 */




//Card system
class Cards {
	static Scanner scan = new Scanner(System.in);
	
	static Integer[] deck = {2,2,2,2,3,3,3,3,4,4,4,4,5,5,5,5,6,6,6,6,7,7,7,7,8,8,8,8,9,9,9,9,10,10,10,10,11,11,11,11};

	List<Integer> cards = new LinkedList<Integer>(Arrays.asList(deck));
	//Constructor
	public Cards(){
		Collections.shuffle(cards);
	}
	//Diagnostic
	void print(){
		System.out.println(cards);
	}
	
	void deal(List<Integer> hand, int amount, boolean auto){
		int count = 1;
		do{
		int temp = cards.remove(0);
		if(temp == 11){
			if(auto == true && (hand.stream().mapToInt(Integer::intValue).sum() + 11) > 21){
				temp = 1;
			}else if(auto == true){
				temp = 11;
				break;
			}
			else{System.out.println("You got an Ace, 1 or 11?");}
			boolean tempW = true;
			while(tempW){
				switch(scan.nextInt()){
				case 1: temp = 1; tempW = false;
				case 11: tempW = false;
				default: break;
			}
			}
		}	
		hand.add(temp);
		
		count++;
		}while(count <= amount);
	}
}

//Hand object
class Hand{
	
	List<Integer> hand = new ArrayList<Integer> ();
	
	public Hand(){
		
	}
	
	public String getHand(){
		String cont = "";
		for(int i = 0; i < hand.size() ; i++){
			int temp = 0;
			String templ = "|";
			
			if(i == hand.size() -1 ){
				templ = "";
			}
			
			temp = (int) hand.get(i);
			cont += temp + templ;
			}
		return cont;
	}
	
	public Boolean compare(Hand target){
		Boolean win = false;
		
		Integer handDiff = 21 - hand.stream().mapToInt(Integer::intValue).sum();
		Integer targetDiff = 21 - target.hand.stream().mapToInt(Integer::intValue).sum();
		
		if(targetDiff < 0){
			win = true;
		}else if(handDiff < 0){
			win = false;
		}else if(handDiff < targetDiff){
			win = true;
		}
		return win;
	}
}

public class Blackjack {
	static Boolean win = false;
	
	static Scanner scan = new Scanner(System.in);
	static Cards pack;
	static Hand player;
	static Hand dealer;
	
	

	
	public static void main(String[] args) {
		
		Boolean restart;
		do{
		restart = false;
		player = new Hand();
		dealer = new Hand();
		
		//Generating card pack
		pack = new Cards();
		
		//Dealing cards
		pack.deal(player.hand,2, false); 
		pack.deal(dealer.hand,1, true);
		
		Boolean playing = true;
		
		do{
			System.out.println("Your hand:"+" "+ player.getHand());//getHand
			System.out.println("Dealers hand:"+" "+ dealer.getHand());
				
			System.out.println("Hit/Stand?");
			String choice = scan.next();
			
			if(choice.equals("Hit")){
					pack.deal(player.hand,1, false);
					if(player.hand.stream().mapToInt(Integer::intValue).sum() > 21){
						win = false;
						playing = false;
						continue;
					}
					
				} else if(choice.equals("Stand")){
					playing = false;
					pack.deal(dealer.hand, 1, false);
					System.out.println("Dealers hand:"+" "+ dealer.getHand()+ " " + "=" + " " + dealer.hand.stream().mapToInt(Integer::intValue).sum());
					while(dealer.hand.stream().mapToInt(Integer::intValue).sum() < 17){
						pack.deal(dealer.hand, 1, false);
						System.out.println("Dealers hand:"+" "+ dealer.getHand()+ " " + "=" + " " + dealer.hand.stream().mapToInt(Integer::intValue).sum());
						
						try {
							TimeUnit.SECONDS.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
					}
					win = player.compare(dealer);
				}else{
				System.out.println("invalid answer");
					
				}
			}while(playing);	
			
			if (win == true){
				System.out.println("You win!");
			}else{
			System.out.println("You lose!");
			}
			System.out.println("Your hand:"+" "+ player.getHand());
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Try again? Y/N");
			String reset = scan.next();
			if(reset.equals("Y")){
				restart = true;
			}
		}while(restart);
	}

}


