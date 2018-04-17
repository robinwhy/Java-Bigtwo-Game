

/**This class is used to represent a flush
 * @author why
 *
 */
public class Flush extends Hand{
	/**Creates and returns an instance of the Flush class
	 * @param player
	 *              the player who is playing 
	 * @param list
	 *            the list of cards played by the player
	 */
	public Flush(CardGamePlayer player, CardList cards){
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
    	Flush a=new Flush(hand.getPlayer(),hand.getList());
    	Straight b=new Straight(hand.getPlayer(),hand.getList());
		if(b.isValid()==true){return true;}
		if(a.isValid()==true){
		Card top=a.getTopCard();
    	Card own=getTopCard();
    	if(own.getSuit()>top.getSuit()){
    		return true;
    	}else if(own.getSuit()<top.getSuit()){
    		return false;
    	}else if(own.getRank()>top.getRank()){
    		return true;
    	}else return false;}
		else return false;
    }
	/**Check if this is a valid flush.
     * @return if this is a valid flush
     */
	boolean isValid(){
    	if(list.size()==5&&list.getCard(0).getSuit()==list.getCard(1).getSuit()&&
    	list.getCard(0).getSuit()==list.getCard(2).getSuit()&&
    	list.getCard(0).getSuit()==list.getCard(3).getSuit()&&
    	list.getCard(0).getSuit()==list.getCard(4).getSuit()){
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
		return "Flush";
	}
}
