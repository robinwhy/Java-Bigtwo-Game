package assignment5;

import java.util.ArrayList;

import java.io.*; import java.net.*; 
import javax.swing.JOptionPane;
/**a constructor for creating a Big Two client, create 4 players and add them to the list of players, 
 * create a Big Two table which builds the GUI for the game and handles user actions, make a connection 
 * to the game server by calling the makeConnection() method from the NetworkGame interface.
 * @author why
 *
 */
public class BigTwoClient implements CardGame, NetworkGame{
	public BigTwoClient(){
		playerName=JOptionPane.showInputDialog("Please enter your name:");
		while(true){
			if(playerName!=null){break;}
		}
		CardGamePlayer player1=new CardGamePlayer();
		CardGamePlayer player2=new CardGamePlayer();
		CardGamePlayer player3=new CardGamePlayer();
		CardGamePlayer player4=new CardGamePlayer();
		playerList=new ArrayList<CardGamePlayer>();
		playerList.add(player1);
		playerList.add(player2);
		playerList.add(player3);
		playerList.add(player4);	
		makeConnection();
		table=new BigTwoTable(this);
		table.setActivePlayer(playerID);
	}
	private boolean first = true;
	private boolean connected;
	private int numOfPlayers;
	private Deck deck;
    private ArrayList<CardGamePlayer> playerList;
    private ArrayList<Hand> handsOnTable=new ArrayList<Hand>();
    private int playerID;
    private String playerName;
    private String serverIP;
    private int serverPort;
    private Socket sock;
    private ObjectOutputStream oos;
    private int currentIdx;
    private BigTwoTable table;
    private int winner=0;
    /**
     * show whether it is the first move of the game 
     */
    boolean begin;
    /**
     * show whether the last move is illegal or not, its value will be changed in void start(Deck deck)
     */
    boolean isLastmoveIllegal=false;
    /**
     * count the number of consecutive passes, if three players all pass their turn, then the next player must play 
     */
    int countpass;
    /**return the deck of cards being used
     * @return the deck of cards being used
     */
    public Deck getDeck(){
        return this.deck;	
    }
   
    /**return the number of players
     * @return the number of players
     */
    public int getNumOfPlayers(){
    	return playerList.size();
    }
    /**a method for checking if the game ends.
     * @return true if the game ends, otherwise false
     */
    public boolean endOfGame(){
    	if(playerList.get(0).getNumOfCards()==0||playerList.get(1).getNumOfCards()==0
    			||playerList.get(2).getNumOfCards()==0||playerList.get(3).getNumOfCards()==0){
    		return true;
    	}
    	return false;
    }
    /**return the list of players
     * @return the list of players
     */
    public ArrayList<CardGamePlayer> getPlayerList(){
    	return this.playerList;
    }
    /**return the list of hands played on the table.
     * @return the list of hands played on the table.
     */
    public ArrayList<Hand> getHandsOnTable(){
    	return this.handsOnTable;
    }
    /**return the index of the current player.
     * @return the index of the current player.
     */
    public int getCurrentIdx(){
    	return this.currentIdx;	
    }
   
    /**start the game with a (shuffled) deck of cards supplied as the argument and implement the Big Two game logics.
     * @param deck
     *            the shuffled deck
     */
    public void start(Deck deck){
    	begin=true;
    	playerList.get(0).removeAllCards();
    	playerList.get(1).removeAllCards();
    	playerList.get(2).removeAllCards();
    	playerList.get(3).removeAllCards();
    	handsOnTable=new ArrayList<Hand>();
    	table.clearMsgArea();
    	for(int i=0;i<13;i++){
    		playerList.get(0).addCard(deck.getCard(i));
    	}playerList.get(0).sortCardsInHand();
    	for(int i=13;i<26;i++){
    		playerList.get(1).addCard(deck.getCard(i));
    	}playerList.get(1).sortCardsInHand();
    	for(int i=26;i<39;i++){
    		playerList.get(2).addCard(deck.getCard(i));
    	}playerList.get(2).sortCardsInHand();
    	for(int i=39;i<52;i++){
    		playerList.get(3).addCard(deck.getCard(i));
    	}playerList.get(3).sortCardsInHand();
    	for(int i=0;i<4;i++){
    		Card diamond3=new Card(0,2);
    		if(playerList.get(i).getCardsInHand().contains(diamond3)){
    			currentIdx=i;
    			table.setActivePlayer(playerID);
    			break;
    		}	
    	}table.repaint();
    	table.printMsg("All players set, game starts.");
		
    	table.printMsg(playerList.get(currentIdx).getName() + "'s turn:");
    }
    /**a method for making a move by a player with the specified playerID 
     * using the cards specified by the list of indices, create a CardGameMessage object 
     * and send it to game server
     */
    public void makeMove(int playerID, int[] cardIdx){
    	CardGameMessage move = new CardGameMessage(CardGameMessage.MOVE, playerID, cardIdx);
		sendMessage(move);
    }
    
    /**a method for checking a move made by a player and make corresponding actions
     */
    public void checkMove(int playerID, int[] cardIdx){
    	if(countpass==3||begin){
    		if(cardIdx==null){
    			table.printMsg("Not a legal move!!!");
    		}
    		else{ Hand hand=composeHand(playerList.get(playerID),playerList.get(playerID).play(cardIdx));
    			if(hand==null){
    				table.printMsg("Not a legal move!!!");   			
    		}
    		    else{
    		    	table.printMsg("{"+hand.getType()+"}");
    		    	table.printMsg(hand.toString());
        		    CardList removablecards=hand.getList();
        		    playerList.get(playerID).removeCards(removablecards);
        		    handsOnTable.add(hand);
        		    begin=false;
        		    currentIdx=(currentIdx+1)%4;
        		  
        		    isLastmoveIllegal=false;
        		    countpass=0;
    		}}
    	}
    	else {
    		if(cardIdx==null){
    		table.printMsg("{pass}");
    		currentIdx=(currentIdx+1)%4;
    		
    		isLastmoveIllegal=false;
    		countpass++;
    	}
    	else{Hand hand=composeHand(playerList.get(playerID),playerList.get(playerID).play(cardIdx));
    	     if(hand==null|| hand.beats(handsOnTable.get(handsOnTable.size()-1))==false	){
    	    	 table.printMsg("Not a legal move!!!");
    	    	 isLastmoveIllegal=true;
    	    	 countpass=0;
    	     }else{
    	    	 table.printMsg("{"+hand.getType()+"}");
    	    	 table.printMsg(hand.toString());
    	    	 CardList removablecards=hand.getList();
    	    	 playerList.get(playerID).removeCards(removablecards);
    	    	 handsOnTable.add(hand);
    	    	 currentIdx=(currentIdx+1)%4;
    	    	
    	    	 isLastmoveIllegal=false;
    	    	 countpass=0;}
    	}}
    	if(!endOfGame()){
    		table.printMsg(playerList.get(currentIdx).getName() + "'s turn:");
			table.resetSelected();
			table.repaint();
    	}
    	if(endOfGame()){
    		table.printMsg("Game ends");
    		table.disable();
    		if(playerList.get(0).getNumOfCards()==0){ winner=0;}
    		if(playerList.get(1).getNumOfCards()==0){ winner=1;}
    		if(playerList.get(2).getNumOfCards()==0){ winner=2;}
    		if(playerList.get(3).getNumOfCards()==0){ winner=3;}
    		for(int j=0;j<4;j++){
    			if(j==winner){
    				table.printMsg(playerList.get(j).getName()+" wins the game.");
    			}else{
    				table.printMsg(playerList.get(j).getName()+" has "+
    			playerList.get(j).getNumOfCards()+" cards in hand.");
    			}
    		}JOptionPane.showMessageDialog(null, "Game ends");
			CardGameMessage ready = new CardGameMessage(CardGameMessage.READY, -1, null);
			sendMessage(ready);
			return;
    	}table.repaint();
    }
    /**getting the playerID (i.e., index) of the local player.
     * @return the playerID (i.e., index) of the local player.
     */
    public int getPlayerID(){
    	return this.playerID;
    }
    /**setting the playerID (i.e., index) of the local player.
     * @param playerID
     *            the playerID (i.e., index) of the local player
     */
    public void setPlayerID(int playerID){
    	this.playerID=playerID;
    }
    /**getting the name of the local player.
     * @return the name of the local player.
     */
    public String getPlayerName(){
    	return this.playerName;
    }
    /**setting the name of the local player.
     * @param playerName
     *            the name of the local player
     */
    public void setPlayerName(String playerName){
    	this.playerName=playerName;
    }
    /**getting the IP address of the game server.
     * @return the IP address of the game server.
     */
    public String getServerIP() {
    	return this.serverIP;
    }
    /**setting the IP address of the game server.
     * @param serverIP
     *            the IP address of the game server.
     */
    public void setServerIP(String serverIP){
    	this.serverIP=serverIP;
    }
    /**getting the TCP port of the game server.
     * @return the TCP port of the game server.
     */
    public int getServerPort(){
    	return this.serverPort;
    }
    /**setting the TCP port of the game server.
     * @param serverPort
     *            the TCP port of the game server.
     */
    public void setServerPort(int serverPort){
    	this.serverPort=serverPort;
    }
   /**making a socket connection with the game server.
    * 
    */
    public synchronized void makeConnection(){
		if(!connected){
			try{
				sock = new Socket("localhost",2396);
				oos = new ObjectOutputStream(sock.getOutputStream());
				Thread readMsgThread = new Thread(new ServerHandler());
				readMsgThread.start();
				CardGameMessage join = new CardGameMessage(CardGameMessage.JOIN, -1, playerName);
				sendMessage(join);
				CardGameMessage ready = new CardGameMessage(CardGameMessage.READY, -1, null);
				sendMessage(ready);
				connected = true;
			}catch(Exception e){
				e.printStackTrace();
			};
		}else{
			table.printMsg(playerName + " has already connected to the server.");
		}			
	}
	
	/** Parse the message received from the server into commands for the client to operate
	 * @param message
	 *            the message received from the game server
	 */
	public synchronized void parseMessage(GameMessage message){
		int type = message.getType();
		switch(type){
			case 0:{		
				String[] names = (String[]) message.getData();
				playerID = message.getPlayerID();
				for(int i = 0; i < 4; i++){
					playerList.get(i).setName(names[i]);
				};
				break;
			}
			case 1:{	
				int id = message.getPlayerID();
				String name = (String) message.getData();
				playerList.get(id).setName(name);
				break;
			}
			case 2:{		
				table.printMsg("The server is full! Cannot join the game now.");
				break;
			}
			case 3:{		
				int id = message.getPlayerID();
				String ip = (String) message.getData();
				table.printMsg(playerList.get(id).getName() + " (" + ip + ") has left the game.");
				numOfPlayers--;
				playerList.get(id).setName(null);
				table.repaint();
				table.disable();
				CardGameMessage ready = new CardGameMessage(CardGameMessage.READY, -1, null);
				sendMessage(ready);
				break;
			}
			case 4:{		
				int id = message.getPlayerID();
				table.printMsg(playerList.get(id).getName() + " is ready.");
				numOfPlayers++;
				break;
			}
			case 5:{	
				BigTwoDeck deck = (BigTwoDeck) message.getData();
				start(deck);
				break;
			}
			case 6:{		
				int id = message.getPlayerID();
				int[] cards = (int[]) message.getData();
				checkMove(id, cards);
				break;
			}
			case 7:{		
				String msg = (String) message.getData();
				table.printChat(msg);
				break;
			}
		}
	}
	
	/**sending the specified message to the game server. 
	 * @param message
	 *          the specified message
	 */
	public void sendMessage(GameMessage message){
		try{
			oos.writeObject(message);
			oos.flush();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**an inner class that implements the Runnable interface.
	 * @author why
	 *
	 */
	public class ServerHandler implements Runnable{
		CardGameMessage msg;
		ObjectInputStream streamReader;	
		public ServerHandler(){
			try{
				streamReader = new ObjectInputStream(sock.getInputStream());
			}catch(Exception ex){
				ex.printStackTrace();				
			}
		}	
		public synchronized void run(){
			try{
				while((msg = (CardGameMessage) streamReader.readObject()) != null){
					parseMessage(msg);
					try{
						Thread.sleep(300);
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
    /**a method for creating an instance of BigTwoClient.
     * @param args
     */
    public static void main(String[] args){
    	BigTwoClient game=new BigTwoClient();
    }   
    /**return a valid hand from the specified list of cards of the player. Return null if no valid hand can be composed from the specified list of cards.
     * @param player 
     *              the player who is playing
     * @param cards
     *              the list of cards played by the player
     * @return
     */
    public static Hand composeHand(CardGamePlayer player, CardList cards){
    	cards.sort();
    	Single a=new Single(player,cards);   
    	Pair b=new Pair(player,cards);    	
    	Triple c=new Triple(player,cards);  
    	StraightFlush d=new StraightFlush(player,cards);
    	Straight e=new Straight(player,cards);
    	Flush f=new Flush(player,cards); 
    	FullHouse g=new FullHouse(player,cards);    
    	Quad h=new Quad(player,cards);
    	if(a.isValid()==true){return a;}
    	else if(b.isValid()==true){return b;}
    	else if(c.isValid()==true){return c;}
    	else if(d.isValid()==true){return d;}
    	else if(e.isValid()==true){return e;}
    	else if(f.isValid()==true){return f;}
    	else if(g.isValid()==true){return g;}
    	else if(h.isValid()==true){return h;}
    	else return null;
    }
}


