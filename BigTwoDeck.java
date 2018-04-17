package assignment5;

/**This class is used to represent a BigTwo deck
 * @author why
 *
 */
public class BigTwoDeck extends Deck {
	/**
	 * Creates and returns an instance of the BigTwoDeck class
	 */
	public BigTwoDeck(){
		initialize();
	}
	/** 
	 * Initialize the deck of cards.
	 */
	public void initialize() {
		removeAllCards();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				BigTwoCard card = new BigTwoCard(i, j);
				addCard(card);
			}
		}
	}
	/**
	 * Sorts the list according to the order of the cards.
	 */
	public void sort(){
		for(int i=0;i<size()-1;i++){
    		for(int j=i+1;j<size();j++){
			if(getCard(i).compareTo(getCard(j))==1){
    			Card tmp=getCard(i);
    			setCard(i,getCard(j));
    			setCard(j,tmp);	
    		}}
	}}

}
