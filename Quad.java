
/**This class is used to represent a quad
 * @author why
 *
 */
public class Quad extends Hand{
	/**Creates and returns an instance of the Quad class
	 * @param player
	 *              the player who is playing 
	 * @param list
	 *            the list of cards played by the player
	 */
	public Quad(CardGamePlayer player, CardList cards){
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
    	Quad a=new Quad(hand.getPlayer(),hand.getList());
    	if(b.isValid()==true){return true;}
		if(c.isValid()==true){return true;}
		if(d.isValid()==true){return true;}
		if(a.isValid()==true){
		Card top=a.getTopCard();
    	Card own=getTopCard();
    	if(own.getRank()>top.getRank()){
    		return true;
    	}else 
    	return false;}
		else return false;
    }
	/**Check if this is a valid quad.
     * @return if this is a valid quad
     */
	boolean isValid(){
    	
    	if(list.size()==5&&((list.getCard(0).getRank()==list.getCard(1).getRank()&&
    	list.getCard(0).getRank()==list.getCard(2).getRank()&&
    	list.getCard(0).getRank()==list.getCard(3).getRank()&&
    	list.getCard(0).getRank()!=list.getCard(4).getRank())||
    			(list.getCard(0).getRank()!=list.getCard(1).getRank()&&
    	    	list.getCard(1).getRank()==list.getCard(2).getRank()&&
    	    	list.getCard(1).getRank()==list.getCard(3).getRank()&&
    	    	list.getCard(1).getRank()==list.getCard(4).getRank()))
    	){
    		return true;
    	}else return false;
    }
	/**Return the top card of the hand
     * @return the top card of the hand
     */
	public Card getTopCard(){
    	
    	if(list.getCard(0).getRank()==list.getCard(1).getRank()){
    		return list.getCard(3);
    	}else
    	return list.getCard(4);
    }
	/** Return a string specifying the type of this hand
	 * @return a string specifying the type of this hand
	 */
	String getType(){
		return "Quad";
	}
}
