package assignment5;
/**This class is used to represent a straight
 * @author why
 *
 */
public class Straight extends Hand{
	/**Creates and returns an instance of the Straight class
	 * @param player
	 *              the player who is playing 
	 * @param list
	 *            the list of cards played by the player
	 */
	public Straight(CardGamePlayer player, CardList cards){
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
    	Straight a=new Straight(hand.getPlayer(),hand.getList());
		if(a.isValid()==true){
		Card top=a.getTopCard();
    	Card own=getTopCard();
    	if(own.getRank()>top.getRank()){
    		return true;
    	}else if(own.getRank()<top.getRank()){
    		return false;}
    	else if(own.getSuit()>top.getSuit()){
    		return true;
    	}else return false;}
		else return false;
    	
    }
	/**Check if this is a valid pair.
     * @return if this is a valid pair
     */
	boolean isValid(){
    	
    	if(list.size()==5&&list.getCard(0).getRank()+1==list.getCard(1).getRank()&&
    			list.getCard(0).getRank()+2==list.getCard(2).getRank()&&
    			list.getCard(0).getRank()+3==list.getCard(3).getRank()&&
    			list.getCard(0).getRank()+4==list.getCard(4).getRank()&&
    			list.getCard(0).getRank()!=0&&list.getCard(0).getRank()!=1&&
    			list.getCard(0).getRank()!=11&&list.getCard(0).getRank()!=12){
    		return true;
    	}else return false;//应该为false
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
		return "Straight";
	}
}
