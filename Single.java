

/**This class is used to represent a single
 * @author why
 *
 */
public class Single extends Hand {
	
	/**
	 * the player who is playing
	 */
	CardGamePlayer player=super.getPlayer();
	/**
	 * the list of cards played by the player
	 */
	CardList list=super.getList();
	
	/**Creates and returns an instance of the Single class
	 * @param player
	 *              the player who is playing 
	 * @param list
	 *            the list of cards played by the player
	 */
	public Single(CardGamePlayer player,CardList list){
		super(player,list);
	}
	/** Return a string specifying the type of this hand
	 * @return a string specifying the type of this hand
	 */
	String getType(){
		return "Single";
	}
	/**Check if this hand beats a specified hand.
     * @param hand
     *            the specified hand
     * @return whether this hand beats the specified hand.
     */
	public boolean beats(Hand hand){
		Single a=new Single(hand.getPlayer(),hand.getList());
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
    			
    	else return false;}
		
    /**Check if this is a valid single.
     * @return if this is a valid single
     */
    boolean isValid(){
    	if(list.size()==1){
    		return true;
    	}else return false;
    }
}
