
public class Hand {
	private Cards[] hand;

	public Hand() {
		this.hand = new Cards[52];
		for (int i = 0; i < hand.length; i++) {
			hand[i] = null;
		}
	}
	
	public int getNumberOfCards() {
		int notempty = 0;
		for (int i = 0; i < hand.length; i++) {
			if (hand[i] != null) {
				notempty++;
			}
		}

		return notempty;
	}

	public Cards getCard(int index) {
		return hand[index];
	}

	public void addCard(Cards newcard) {//draw card
		for (int i = 0; i < this.hand.length; i++) {
			if (hand[i] == null) {
				hand[i] = newcard;
				break;
			}
		}
	}
	public int getScore() {
		int total = 0;

		for (int i = 0; i < hand.length; i++) {
			if (hand[i] != null) {
				total = total + hand[i].getValue();
			}
		}

		boolean ace = false;
		for (int i = 0; i < hand.length; i++) {
			if (hand[i] != null) {
				if (hand[i].getFace() == 1) {
					ace = true;
				}
			}
		}

		if (ace == true && total + 10 <= 21) {
			total = total + 10;
		}

		return total;
	}
	public String toStringPlayer() {
		String x = "";

		for (int i = 0; i < this.hand.length; i++) {
			if (hand[i] != null) {
				x = "\t" + x + "\n" + "\t" + hand[i].toString();
			}
		}

		return x;
	}
	public void discardAll() {

		for (int i = 0; i < hand.length; i++) {
			hand[i] = null;
		}

	}
	public String toStringDealer() {
		String x = "";

		x = "\t" + hand[0].toString() + "\n" + "\t" + "A face down card.";

		return x;	
		}
	}
