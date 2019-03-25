//include players and dealer

public class Player {
	
	private String name;
	private boolean isDealer;
	private Hand h;
	private double deposit;
	private double bet;
	private int h_win; // win = 1; lose = 0; push = 2
	
	
	//when player choose split
	private int sp_win; // win = 1; lose = 0; push = 2
	private boolean canSplit; 
	private Hand split;
	private double split_bet;
	private boolean splitcomplete;
	
	public Player(String playerName, boolean isDealer, double deposit)
	{
		this.name = playerName;
		this.isDealer = isDealer;
		this.h = new Hand();
		this.deposit = deposit;
		this.bet = 0;
		this.h_win = 3;
		this.sp_win = 3;
		this.canSplit = false;
		this.split = new Hand();
		this.split_bet = 0;
		this.splitcomplete = false;
	}

	// This method check the player's name.
	public String getName()
	{
			return this.name; 
	}

		
	//This method returns if the player is the dealer.
	public boolean isDealer(){
		return this.isDealer;
	}
	//This is about set dealer
	public void setDealer(boolean b){
		this.isDealer = b;		
	}
	
	
	
	
	// This method check the player's hand of cards.
	public Hand getHand()
	{
		return this.h; 
	}
	
	public double getBank(){
		return this.deposit;
	}
	
	public int getWinH(){
		return this.h_win;
	}
	
	public int getWinS(){
		return this.sp_win;
	}
	
	public double getBetH(){
		return this.bet;
	}
	
	
	public double getBetS(){
		return this.split_bet;
	}
	
	private boolean hitProcess(Hand inputHand, Deck deck){
		System.out.println("Do you want Hit?  Y/N");
		boolean hit = IO.readBoolean();

		while(hit == true){
			Cards c = deck.drawCard();
			c.turnFaceUp();
			inputHand.addCard(c);
			System.out.println("\t" + "Drew a " + c.toString());
			System.out.println("\t" + this.name + "'s current score: " + inputHand.getScore());
			
			if(inputHand.getScore() == 21){
				System.out.println("");	
				System.out.println( this.name + " has blackjack!");
				
				return false;
			}
			
			if(inputHand.getScore() > 21){
				System.out.println("");	
				System.out.println("Sorry,You busted.");
				return false;
			}
			System.out.println("Hit?  Y/N"); 
			hit = IO.readBoolean(); 
		}
		return true;
	}

	private boolean doubledownProcess(Hand inputHand, Deck deck){
		Cards c = deck.drawCard();
		c.turnFaceUp();
		inputHand.addCard(c);
		System.out.println("\t" + "Drew a " + c.toString());
		System.out.println("\t" + this.name + "'s current score: " + inputHand.getScore());
		
		if(inputHand.getScore() == 21){
			System.out.println( this.name + " has blackjack!");
			return false;
		}
		
		if(inputHand.getScore() > 21){
			System.out.println("You busted.");
			return false;
		}
		return true;
	}
	
	
	/*this is first round->draw two card for each player
	*
	*
	*/
	
	public void firstRound(Deck deck)    
	{
		if(this.isDealer == true){
			Cards c1 = deck.drawCard();
			c1.turnFaceUp();
			Cards c2 = deck.drawCard();
			c2.turnFaceDown();
			h.addCard(c1); 
			h.addCard(c2); 
			
			System.out.println("Dealer: " + this.name);
			System.out.println( h.toStringDealer());
			System.out.println();
			
		}
		
		else if(this.isDealer == false){
			System.out.println("Player: " + this.name);
			System.out.println("Your deposit is: " + this.deposit);
			System.out.println("Place your bet:");
			
			//make bets
			this.bet = IO.readInt();
			
			//check bet maximun
			while(this.bet > this.deposit){
				System.out.println("Your maximun bet can only be: " + this.deposit);
				System.out.println("Place your bet again:");
				this.bet = IO.readInt();
			}
			
			
			Cards c1 = deck.drawCard();
			Cards c2 = deck.drawCard();
			c1.turnFaceUp();
			c2.turnFaceUp();
			h.addCard(c1); 
			h.addCard(c2); 
			if(h.getCard(0).getValue() == h.getCard(1).getValue()){
				this.canSplit = true;
			}
			
			System.out.println(h.toStringPlayer());
			System.out.println();
			
		}
	
	}

	
	/* this method about the playround  
	 * 
	 * when the player choose to split
	 * busts
	 * 
	 * if player is not dealer-> player can choose split and double down
	 * if player choose unsplit->player can choose double down or not 
	 * if player choose split->player can choose double down the first hand or second hand
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	public void playRound(Deck deck) 
	{
		int totalscore = h.getScore();
		System.out.println(this.name + " play round:");
		System.out.println(h.toStringPlayer());
		System.out.println("");
		System.out.println("\t" + this.name + "'s current score: " + h.getScore());
		System.out.println("");
		
		//if the player is not dealer
		if(this.isDealer == false){
			
			if(canSplit == false){
				// regular hit/stand.  not double down.
				boolean doubledown = false;
				if (this.deposit > this.bet){
					System.out.println("Do you want to double down?  Your current bet is:" +this.bet+"     Y/N");
					doubledown = IO.readBoolean(); 
				}

				if(doubledown == false){
					if(!hitProcess(this.h, deck)){return;}
				}
			
				if(doubledown == true){
					if(this.deposit < 2*this.bet){
						System.out.println("Your deposit is not enough.");
						System.out.println("You only have "+(this.deposit - this.bet)+" left, you should all in");
						this.bet = this.deposit;
					}
					else{
						this.bet = 2*(this.bet);
					}
					if(!doubledownProcess(this.h, deck)){return;}
				} // double down process
			} ////can not split
			
			splitcomplete = false;
			boolean doSplit = false;
			if(canSplit == true){
				System.out.println("Do you want to split?    Y/N");
				doSplit = IO.readBoolean();
				
				if(doSplit == true){
					this.split_bet = this.bet/2;//3.8 9:16
					this.bet = this.bet-this.split_bet;

					this.split.addCard(h.getCard(1));
					Cards temp = h.getCard(0);
					h.discardAll();
					h.addCard(temp);
					split.addCard(deck.drawCard());
					h.addCard(deck.drawCard());
					System.out.println(h.toStringPlayer());
					System.out.println("\t" +"This hand's current score: " + h.getScore());
					System.out.println("");
					System.out.println(split.toStringPlayer());
					System.out.println("\t" +"This hand's current score: " + split.getScore());
					////splits hand into two.  h and split.
					
					//first hand 
					boolean doubledown = false;
					if (this.deposit > this.bet + this.split_bet){
						System.out.println("Do you want to double down first hand?  Your first hand current bet is:" +this.bet+"     Y/N");
						doubledown = IO.readBoolean(); 
					}
					if(doubledown == false){
						if(!hitProcess(this.h, deck)){}
					}// regular hit/stand.  not double down.
					
					if(doubledown == true){
						if(this.deposit < 2*this.bet + this.split_bet){
							System.out.println("Your deposit is not enough.");
							System.out.println("You only have "+(this.deposit - this.bet - this.split_bet)+" left, you should all in");
							this.bet = this.deposit - this.split_bet;
						}
						else{
							this.bet = 2*(this.bet);
						}
						if(!doubledownProcess(this.h, deck)){}
					} // double down process
					////for hand 'h'
					
					
					//second hand
					doubledown = false;
					if (this.deposit > this.bet + this.split_bet){
						System.out.println("Would you like to double down the second hand?  Your second hand current bet is:" + this.split_bet+"     Y/N");
						doubledown = IO.readBoolean(); 
					}
					
					if(doubledown == false){
						if(!hitProcess(this.split, deck)){return;}
					}// regular hit/stand.  not double down.
					
					if(doubledown == true){
						if(this.deposit < this.bet + 2*this.split_bet){
							System.out.println("Your deposit is not enough.");
							System.out.println("You only have "+(this.deposit - this.bet - this.split_bet)+" left, you should all in");
							this.split_bet = this.deposit - this.bet;
						}
						else{
							this.split_bet = 2*(this.split_bet);
						}
						if(!doubledownProcess(this.split, deck)){return;}
						splitcomplete = true;
					} // double down process
					////for hand 'split'
					canSplit = false;
				}//end of split, do split.
				
				if(doSplit == false){
					boolean doubledown = false;
					if (this.deposit > this.bet){
						System.out.println("Do you want to double down?  Your current bet is:" +this.bet+"     Y/N");
						doubledown = IO.readBoolean(); 
					}

					if(doubledown == false){
						if(!hitProcess(this.h, deck)){return;}
					}
					if(doubledown == true){
						if(this.deposit < 2*this.bet){
							System.out.println("Your deposit is not enough.");
							System.out.println("You should all in your deposit. Your deposit is:"+this.deposit);
							this.bet = this.deposit;
						}
						else{
							this.bet = 2*(this.bet);
						}
						if(!doubledownProcess(this.h, deck)){return;}
					} // double down process
						
				}//end of unsplit
			}//end of canSplit
			
			
		}
		
		if(this.isDealer == true){//draw card until totalsocore greater than 17, 
			System.out.println("DEALER-->");
			while(totalscore < 17){
				Cards c = deck.drawCard();
				c.turnFaceUp();
				h.addCard(c);
				System.out.println("\t" + "Drew a " + c.toString());
				System.out.println("\t" + this.name + "'s current score: " + h.getScore());
				
				if(h.getScore() > 21)
					System.out.println("Dealer busts!");
				if(h.getScore() == 21)
					System.out.println("Dealer has blackjack!");
				if(h.getScore() >= 17)
					return;			
			}
			
		}//end of Playerround
			
		return;					
	}
	
	/* this method about the final playround  
	 * print the game result for player (player is not dealer)
	 * 
	 * 
	 * 
	 */
	
	public void finalRound(int dealerScore) 
	{
		if(this.isDealer == false){
			
			System.out.print(this.name + ":  ");
			
			if(splitcomplete == false){
				if(h.getScore() > 21){
					System.out.println("");
					System.out.println("You lose because you busted!");
					this.deposit = this.deposit - this.bet;
					System.out.println("\t" + this.name + "'s Current bank value: " + this.deposit);
					
					this.h_win = 0;
				}
				
				else if(dealerScore > 21){
					System.out.println("");
					System.out.println("You win!  The Dealer busted!");
					this.deposit = this.deposit + this.bet;
					System.out.println("\t" +this.name + "'s Current bank value: " + this.deposit);
					
					this.h_win= 1;
				}
				
				else if( h.getScore() < dealerScore){
					System.out.println("");
					System.out.println("You lose because the Dealer beat you!");
					this.deposit = this.deposit - this.bet;
					System.out.println("\t" +this.name + "'s Current bank value: " + this.deposit);
					
					this.h_win = 0;
	
				}
				else if( h.getScore() > dealerScore){
					System.out.println("");
					System.out.println("You win!  You beat the Dealer!");
					this.deposit = this.deposit + this.bet;
					System.out.println("\t" +this.name + "'s Current bank value: " + this.deposit);
					
					this.h_win = 1;
				}
				
				else if( h.getScore() == dealerScore){
					System.out.println("");
					System.out.println("You pushed because you tied the Dealer.");
					System.out.println("\t" +this.name + "'s Current bank value: " + this.deposit);
					
					this.h_win = 2;
				}	
			}//no split
			
			if(splitcomplete==true){
				System.out.println("\t" +"First hand: ");
				if(h.getScore() > 21){
					System.out.println("");
					System.out.println("You lose because you busted!");
					this.deposit = this.deposit - this.bet;
					System.out.println("\t" + this.name + "'s Current bank value: " + this.deposit);
					
					this.h_win = 0;
				}
				
				else if(dealerScore > 21){
					System.out.println("");
					System.out.println("You win!  The Dealer busted!");
					this.deposit = this.deposit + this.bet;
					System.out.println("\t" +this.name + "'s Current bank value: " + this.deposit);
					
					this.h_win = 1;
				}
				
				else if( h.getScore() < dealerScore){
					System.out.println("");
					System.out.println("You lose because the Dealer beat you!");
					this.deposit = this.deposit - this.bet;
					System.out.println("\t" +this.name + "'s Current bank value: " + this.deposit);
					
					this.h_win = 0;
	
				}
				else if( h.getScore() > dealerScore){
					System.out.println("");
					System.out.println("You win!  You beat the Dealer!");
					this.deposit = this.deposit + this.bet;
					System.out.println("\t" +this.name + "'s Current bank value: " + this.deposit);
					
					this.h_win = 1;
				}
				
				else if( h.getScore() == dealerScore){
					System.out.println("");
					System.out.println("You pushed because you tied the Dealer.");
					System.out.println("\t" +this.name + "'s Current bank value: " + this.deposit);
					
					this.h_win = 2;
				}	
				////results for first hand 'h'
				
				System.out.println("\t" +"Second hand: ");
				if(split.getScore() > 21){
					System.out.println("");
					System.out.println("You lose because busted!");
					this.deposit = this.deposit - this.split_bet;
					System.out.println("\t" + this.name + "'s Current bank value: " + this.deposit);
					
					this.sp_win = 0;
				}
				
				else if(dealerScore > 21){
					System.out.println("");
					System.out.println("You win!  The Dealer busted!");
					this.deposit = this.deposit + this.split_bet;
					System.out.println("\t" +this.name + "'s Current bank value: " + this.deposit);
					
					this.sp_win = 1;
				}
				
				else if( split.getScore() < dealerScore){
					System.out.println("");
					System.out.println("You lose because the Dealer beat you!");
					this.deposit = this.deposit - this.split_bet;
					System.out.println("\t" +this.name + "'s Current bank value: " + this.deposit);
					
					this.sp_win = 0;
	
				}
				else if( split.getScore() > dealerScore){
					System.out.println("");
					System.out.println("You win!  You beat the Dealer!");
					this.deposit = this.deposit + this.split_bet;
					System.out.println("\t" +this.name + "'s Current bank value: " + this.deposit);
					
					this.sp_win = 1;
				}
				
				else if( split.getScore() == dealerScore){
					
					System.out.println("");
					System.out.println("You pushed because you tied the Dealer.");
					System.out.println("\t" +this.name + "'s Current bank value: " + this.deposit);
					
					this.sp_win = 2;
				}	
				////results for second hand 'split'
				
				
			}//did split
			
			
			
			
			
		}//isdealer false
		
		
		h.discardAll();
		split.discardAll();
		
		return; 
		
		
	}

	public void finishRoundDealer(double totalbets){
		this.deposit = this.deposit + totalbets;
		System.out.println("\t"+ this.name + "'s Current bank value: " + this.deposit);
	}
}
