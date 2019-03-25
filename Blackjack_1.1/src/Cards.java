// This class represents one playing card.
public class Cards
{
	//card suits
	public static final int SPADES   = 0;
	public static final int HEARTS   = 1;
	public static final int CLUBS    = 2;
	public static final int DIAMONDS = 3;

	// Card faces
	public static final int ACE      = 1;
	public static final int TWO      = 2;
	public static final int THREE    = 3;
	public static final int FOUR     = 4;
	public static final int FIVE     = 5;
	public static final int SIX      = 6;
	public static final int SEVEN    = 7;
	public static final int EIGHT    = 8;
	public static final int NINE     = 9;
	public static final int TEN      = 10;
	public static final int JACK     = 11;
	public static final int QUEEN    = 12;
	public static final int KING     = 13;

	//define fields.
	private int suit;
	private int face;
	
	
	private boolean faceup;


	// This constructor builds a card with the given suit and face
	public Cards(int cardSuit, int cardFace)
	{
		this.suit = cardSuit;
		this.face = cardFace;
		this.faceup = false;
	}

	// This method check the suit 
	public int getSuit()
	{
		return this.suit; 
	}
	
	// This method check the face.
	public int getFace()
	{
		return this.face;
	}
	
	// This method check the numerical value of this card
	public int getValue()
	{
		int value;
		
		if( this.face >= 10 )
			value = 10;
		else 
			value = this.face;
		
		
		return value;
	}

	// front of the card should be visible.
	public boolean isFaceUp()
	{
		return this.faceup; 
	}

	//  the front of the card should be visible.
	public void turnFaceUp()
	{
		this.faceup = true;
	}
	
	// only the back of the card should be visible.
	public void turnFaceDown()
	{
		this.faceup = false;
	}


	public String toString(){
		String cardname = "";
				
		String suitname = "";
		if(this.suit == 0)
			suitname = "♠️";
		if(this.suit ==1)
			suitname = "♥️";
		if(this.suit ==2)
			suitname = "♠️";
		if(this.suit ==3)
			suitname = "♦️";
		
		if(this.face == 1 || this.face > 10){
			String facename = "";
			if(this.face == 1)
				facename = "Ace";
			if(this.face == 11)
				facename = "Jack";
			if(this.face == 12)
				facename = "Queen";
			if(this.face == 13)
				facename = "King";
			
			cardname = facename + " --" + suitname;
		}
		
		else if(this.face != 1 && this.face <= 10)
			cardname = this.face + " -- " + suitname;
			
			
			
		return cardname;
	}
}