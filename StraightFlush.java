
/**This class is used to represent a straightflush
 * @author why
 *
 */
public class StraightFlush extends Hand{
	/**Creates and returns an instance of the StraightFlush class
	 * @param player
	 *              the player who is playing 
	 * @param list
	 *            the list of cards played by the player
	 */
	public StraightFlush(CardGamePlayer player, CardList cards){
		super(player,cards);
	}
	/**
	 * the player who is playing
	 */
	CardGamePlayer player=super.getPlayer();
	/**
	 * the list of cards played by the player
	 */
	CardList list=super.getList();
	/**Check if this hand beats a specified hand.
     * @param hand
     *            the specified hand
     * @return whether this hand beats the specified hand.
     */
	public boolean beats(Hand hand){
		Flush c=new Flush(hand.getPlayer(),hand.getList());
    	Straight b=new Straight(hand.getPlayer(),hand.getList());
    	FullHouse d=new FullHouse(hand.getPlayer(),hand.getList());
    	Quad e=new Quad(hand.getPlayer(),hand.getList());
    	StraightFlush a=new StraightFlush(hand.getPlayer(),hand.getList());
    	if(b.isValid()==true){return true;}
		if(c.isValid()==true){return true;}
		if(d.isValid()==true){return true;}
		if(e.isValid()==true){return true;}
		if(a.isValid()==true){
		Card top=a.getTopCard();
    	Card own=getTopCard();
    	if(own.getRank()>top.getRank()){
    		return true;
    	}
    	else if(own.getRank()<top.getRank()){return false;}
    	else if(own.getSuit()>top.getSuit()){return true;}
    	else return false;}
		else return false;
    }
	/**Check if this is a valid StraightFlush.
     * @return if this is a valid StraightFlush
     */
	boolean isValid(){
    	Flush a=new Flush(player,list);
    	Straight b=new Straight(player,list);
    	if(a.isValid()==true&&b.isValid()==true){
    		return true;
    	}else return false;
    }
	/**Return the top card of the hand
     * @return the top card of the hand
     */
	public Card getTopCard(){
    	
    	return list.getCard(4);
    }
	 /** Return a string specifying the type of this hand
		 * @return a string specifying the type of this hand
		 */
	String getType(){
		return "Straight Flush";
	}
}
