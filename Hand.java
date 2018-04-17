package assignment5;

/**This class is used to represent a hand of cards
 * @author why
 *
 */
abstract public class Hand extends CardList{
	/**Creates and returns an instance of the Hand class
	 * @param player
	 *              the player who is playing
	 * @param cards
	 *              the list of cards played by the player
	 */
	public Hand(CardGamePlayer player, CardList cards){
		this.player=player;
		this.cards=cards;
		for(int i=0;i<cards.size();i++){
			addCard(cards.getCard(i));
		}
	}
	/**Creates and returns an instance of the Hand class
	 * 
	 */
	public Hand(){}
	private CardGamePlayer player;
    private CardList cards;
	/**Return the player of this hand.
	 * @return the player of this hand.
	 */
	public CardGamePlayer getPlayer(){
    	return this.player;
    }
	/**Return the list of cards of this hand
	 * @return the list of cards of this hand
	 */
	public CardList getList(){
		return this.cards;
	}

    /**Return the top card of the hand
     * @return the top card of the hand
     */
    public Card getTopCard(){
    	
    	return cards.getCard(0);
    	
    }/**Check if this hand beats a specified hand.
     * @param hand
     *            the specified hand
     * @return whether this hand beats the specified hand.
     */
    public boolean beats(Hand hand){
    	return true;
    }
    /**Will be overridden in subclass,check if this is a valid hand.
     * @return if this is a valid hand
     */
    abstract boolean isValid();
    /**Will be overridden in subclass,return a string specifying the type of this hand.
     * @return a string specifying the type of this hand.
     */
    abstract String getType();
}
