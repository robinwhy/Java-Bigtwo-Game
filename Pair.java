
/**This class is used to represent a pair
 * @author why
 *
 */
public class Pair extends Hand{
	/**Creates and returns an instance of the Pair class
	 * @param player
	 *              the player who is playing 
	 * @param list
	 *            the list of cards played by the player
	 */
	public Pair(CardGamePlayer player, CardList cards){
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
    	Pair a=new Pair(hand.getPlayer(),hand.getList());
		if(a.isValid()==true){
		Card top=a.getTopCard();
    	Card own=getTopCard();
    	if(own.getRank()>top.getRank()){
    		return true;
    	}else if(own.getRank()<top.getRank()){
    		return false;
    	}else if(own.getSuit()>top.getSuit()){
    		return true;
    	}else return false;}
		else return false;
    }
	/**Check if this is a valid pair.
     * @return if this is a valid pair
     */
	boolean isValid(){
    	if(list.size()==2&&list.getCard(0).getRank()==list.getCard(1).getRank()){
    		return true;
    	}else return false;
    }
	/**Return the top card of the hand
     * @return the top card of the hand
     */
    public Card getTopCard(){
    	if(list.getCard(0).getSuit()>list.getCard(1).getSuit()){
    		return list.getCard(0);
    	}else return list.getCard(1);
    }
    /** Return a string specifying the type of this hand
	 * @return a string specifying the type of this hand
	 */
	String getType(){
		return "Pair";
	}
}
