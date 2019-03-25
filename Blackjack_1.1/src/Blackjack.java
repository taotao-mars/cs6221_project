import java.util.Arrays; 
public class Blackjack {

	public static void main(String[] args) {
		System.out.println("How many players? (Not including dealer)  Enter number:");
		int numofplayers = IO.readInt(); 
		System.out.println("");
		numofplayers = numofplayers + 1;// play+dealer
		boolean isdealer = false;
		
		Player[] players = new Player [numofplayers];
		
		//find the each player name and ask deposit
		for(int i=1; i<numofplayers+1;i++){
			
			System.out.println("Name of player "+ i +" :" );
			String name = IO.readString();
			System.out.println("Is player " + name + " the dealer?    Y/N");
			boolean dealer = IO.readBoolean();
			System.out.println("What is " + name + " 's starting deposit value?  Double in dollars:");
			double deposit = IO.readDouble();
					
			
			if(dealer == true && isdealer == true){//to check the dealer is the only one 
				System.out.println("Only one dealer allowed!  Make " + name + " dealer instead?     Y/N");
				boolean isnewdealer = IO.readBoolean();
				
				if( isnewdealer == true){
					for(int j=0; j<players.length;j++){
						if(players[j] != null){
							if(players[j].isDealer() == true){
								players[j].setDealer(false);
							}
						}
					}	
				}
				if( isnewdealer == false){
					dealer = false;
				}	
			}
			
			if(dealer == true && isdealer == false){
				isdealer = true;
			} //declares that a dealer exists so far.
			
			System.out.println("");
			
			players[i-1] = new Player(name, dealer, deposit);
		}

		// check whether there is dealer or not, if not,set last on as dealer
		if(isdealer == false){
			players[players.length-1].setDealer(true);
			System.out.println("No dealer, force " + players[players.length-1].getName()+" as dealer.");
			System.out.println("");
		}
		
		
		
		System.out.println("How many decks would you like to play with?");
		int num_decks = IO.readInt();
		System.out.println("");
		
		Deck d = new Deck(num_decks);
		d.shuffle();
		//create deck and shuffle.
		
		boolean dealerlose = false;
		boolean quit = false;
		while(quit == false){	
			players = playRound(players, d);
			
			for(int i=0; i<players.length;i++){
				if(players[i].isDealer()==true){
					if(players[i].getBank() <= 0){
						System.out.println("");
						System.out.println("");
						System.out.println("GAME OVER!  Dealer's bank has run out! players win!");
						dealerlose = true;
					}
				}
			}

			if(players.length == 0 || (players.length ==1 && players[0].isDealer())){
				System.out.println("all players lose");
				break;
			}
	
			if(dealerlose != true){
				
				System.out.println("-------------------------------------------");
				System.out.println("Do you want to quit?   Y/N"); 
				quit = IO.readBoolean();
				//play round, ask to quit after round is over.
				
				boolean changeDealer = false;
				String newdealer = "";
				if( quit == false){
					System.out.println("Change dealer?");
					changeDealer = IO.readBoolean();
					if(changeDealer == true){
						System.out.println("Who is the new dealer?");
						newdealer = IO.readString();
						
						for(int i=0; i<players.length;i++){
							players[i].setDealer(false);
						}
						
						for(int i=0; i<players.length;i++){
							if(players[i].getName().equals(newdealer)){
								players[i].setDealer(true);
							}	
						}
					}	
				}
			}
			//if not quitting, change dealer?
			//ask who new dealer is.  
			//set new dealer.
			else if(dealerlose == true){
				break;
			}		
		}
		//play rounds until user decides to quit.
	}	
		
		// This method executes an single round of play
		//	- Create and shuffle a deck of cards.
		//	- Start the round (deal cards) for each player, then the dealer.
		//	- Allow each player to play, then the dealer.
		//	- Finish the round for each player.
		public static Player[] playRound(Player[] players,Deck d)
		{
			
			for(int i=0; i<players.length;i++){
				if(players[i].isDealer() == true){
					Player temp = players[i];
					players[i] = players[players.length-1];
					players[players.length-1] = temp;
					break;
				}		
			} // puts dealer as last player. search and replace.		
			for(int i=0; i<players.length;i++){
				players[i].firstRound(d);
				System.out.println("----------------------------------------------");
				System.out.println("");
			}
			//players + dealer start round. Card dealt.
			
			
			
			for(int i=0; i<players.length;i++){
				System.out.println("It's " + players[i].getName() + "'s turn");
				if(players[i].isDealer() == false)
					Hint(players[i]);
				players[i].playRound(d);
				System.out.println("----------------------------------------------");
				System.out.println("");
			}
			//players + dealer play round. Hit or stand.
			
			
			
			int dealerhand = 0;
			for(int i=0; i<players.length;i++){
				if(players[i].isDealer() == true){
					dealerhand = players[i].getHand().getScore();
				}	
			}
			//get dealer's hand value.
			
			
			double totalbets = 0;
			int removeIndex[] = new int[players.length];
			int removeNumber = 0;
			for(int i=0; i<players.length;i++){
				players[i].finalRound(dealerhand);
				if(players[i].getWinH() == 1)
					totalbets = totalbets - players[i].getBetH();
				if(players[i].getWinH() == 0)
					totalbets = totalbets + players[i].getBetH();
				
				if(players[i].getWinS() == 1)
					totalbets = totalbets - players[i].getBetS();
				if(players[i].getWinS() == 0)
					totalbets = totalbets + players[i].getBetS();
			
				if(players[i].isDealer() == true){
					System.out.println(players[i].getName() + "(Dealer):  ");
					players[i].finishRoundDealer(totalbets);
				}
				else{
					if (players[i].getBank() <=0){
						removeIndex[i] = 1;
						removeNumber+=1;
						System.out.println("\t" + players[i].getName()+" doesn't have money, removed");						
					}
				}// remove no money users
			}
			if (removeNumber != 0){
				Player[] newPlayers = new Player [players.length - removeNumber];
				int count = 0;
				for(int i=0; i<removeIndex.length;i++){
					if(removeIndex[i]!=1){
						newPlayers[i - count] = players[i];
						//System.out.println(players[i].getName);						
					}
					else{
						count +=1;
					}
				}
				players = Arrays.copyOf(newPlayers, newPlayers.length);
			}
			return players;
			//players finish round.  Declare victories and losses based on dealer's score.		
		}
		
		


		public static void Hint(Player p){
			
			System.out.println("Would you like a hint?    Y/N");
			boolean takehint = IO.readBoolean();
			
			if(takehint == true){
				int score = p.getHand().getScore();
				
			      switch(score)
			      {
			         case 21 :
						System.out.println("You have blackjack!"); 
			            break;
			         case 20 :
			        	 System.out.println("There is about a 92 % chance that you will bust if you hit."); 
				            break;

			         case 19 :
			            System.out.println("There is about a 85 % chance that you will bust if you hit.");
			            break;
			         case 18 :
			            System.out.println("There is about a 77 % chance that you will bust if you hit.");
			            break;
			         case 17 :
			            System.out.println("There is about a 69 % chance that you will bust if you hit.");
			            break;
			         case 16 :
				        System.out.println("There is about a 62 % chance that you will bust if you hit.");
				        break;
			         case 15 :
				        System.out.println("There is about a 54 % chance that you will bust if you hit.");
				        break;
			         case 14 :
				        System.out.println("There is about a 46 % chance that you will bust if you hit.");
				        break;
			         case 13 :
				        System.out.println("There is about a 38 % chance that you will bust if you hit.");
				        break;
			         case 12 :
				        System.out.println("There is about a 31 % chance that you will bust if you hit.");
				        break;
			         default:
			             System.out.println("There is about a 0 % chance that you will bust if you hit.");
			      }	
	}

}
}