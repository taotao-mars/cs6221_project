//import java.util.Random;

public class Deck {
	private Cards[] d;
	
	 //builds a deck of 52 cards.
		public Deck(int num_decks)
		{
			
			this.d = new Cards [52*num_decks]; 
			int x=0;
			
			for(int k=0; k<num_decks;k++){ //for each deck
				
				for(int i=0; i<4;i++){ // for each suit	
					for(int j=1; j<14; j++){ //for each face
						Cards temp = new Cards(i,j);
						this.d[x] = temp;
						x++;
					}
				}
				
			}
		}
		
		// This method shuffles the deck (randomizes the array of cards).
		public void shuffle()
		{
			Cards temp = null;
			int randomi=0;
			
			for(int i=0; i<d.length;i++){
				randomi = (int) ( Math.random()*51 );
				temp = d[randomi];
				d[randomi] = d[i];
				d[i] = temp;
			
			}	//swap the card with another card
		}
		
		// This method takes the top card off the deck and returns it.
		public Cards drawCard()
		{
			Cards draw = null;
			
			for(int i=0; i<this.d.length;i++){
				if(this.d[i] != null){
					draw = this.d[i];
					this.d[i] = null;
					break;
				}
			}
			
			return draw; 
		}
		// This method returns the number of cards left in the deck.
		public int getSize()
		{
			int unempty = 0;
			
			for(int i=0; i<this.d.length;i++){
				if(this.d[i] != null){
					unempty++;
				}
			}
			
			return unempty;
		}

}
