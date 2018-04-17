package assignment5;

/**This class is used to represent a BigTwo card
 * @author why
 *
 */
public class BigTwoCard extends Card{
	/**Creates and returns an instance of the BigTwoCard class
	 * @param suit
	 *            suit of the card
	 * @param rank
	 *            rank of the card
	 */
	public BigTwoCard(int suit ,int rank){
		super(suit,rank);
	}
	/**compares this card with the specified card for order.
	 * @param card
	 *            the specified card to compare with
	 * @return a negative integer, zero, or a positive integer as this card is less than, equal to, or greater than the specified card
	 */
	public int compareTo(BigTwoCard card){
		if (this.rank > card.rank) {
			return 1;
		} else if (this.rank < card.rank) {
			return -1;
		} else if (this.suit > card.suit) {
			return 1;
		} else if (this.suit < card.suit) {
			return -1;
		} else {
			return 0;
		}
	}

}
