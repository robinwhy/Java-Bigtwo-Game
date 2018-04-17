import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/** build a GUI for the Big Two card game and handle all user actions, implements CardGameTable
 * @author why
 *
 */
public class BigTwoTable implements CardGameTable{
	/**a constructor for creating a BigTwoTable
	 * @param game is a reference to a card game associates with this table.
	 */
	BigTwoTable(BigTwoClient game){
		this.game=game;
		frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.add(connect);menu.add(quit);
		bar.add(menu);
		frame.setMenuBar(bar);
		connect.addActionListener(new ConnectMenuItemListener());
		quit.addActionListener(new QuitMenuItemListener());
		msgArea=new JTextArea(20,40);
		msgArea.setEditable(false);
		JScrollPane scroll=new JScrollPane(msgArea);
		JPanel textpanel=new JPanel();
		textpanel.setLayout(new BoxLayout(textpanel, BoxLayout.Y_AXIS));
		textpanel.add(scroll);
		chatArea=new JTextArea(20, 40);
		chatArea.setEditable(false);
		JScrollPane scrollchat=new JScrollPane(chatArea);
		textpanel.add(scrollchat);
		frame.add(textpanel, BorderLayout.EAST);
		JLabel messagelabel=new JLabel("Message: ");
		messagelabel.setForeground(Color.WHITE);
		message=new JTextField(30);
		message.addActionListener(new ChatListener());
		playButton = new JButton("Play");
		playButton.addActionListener(new PlayButtonListener());
		passButton = new JButton("Pass");
		passButton.addActionListener(new PassButtonListener());
		JPanel buttons=new JPanel();
		buttons.add(playButton);
		buttons.add(passButton);
		buttons.add(messagelabel);
		buttons.add(message);
		buttons.setBackground(Color.DARK_GRAY);
		frame.add(buttons,BorderLayout.SOUTH);
		bigTwoPanel=new BigTwoPanel();
		frame.add(bigTwoPanel,BorderLayout.CENTER);
		char[] suit = {'d','c','h','s'};
		char[] rank = {'a','2','3', '4', '5', '6', '7', '8', '9', 't', 'j', 'q', 'k'};
		avatars=new Image[4];
		ImageIcon temp=new ImageIcon("……");
		temp.setImage(temp.getImage().getScaledInstance(55, 70,  
                Image.SCALE_DEFAULT));  
		avatars[0]=temp.getImage();
		temp=new ImageIcon("……");
		temp.setImage(temp.getImage().getScaledInstance(55, 70,  
                Image.SCALE_DEFAULT));  
		avatars[1]=temp.getImage();
		temp=new ImageIcon("……");
		temp.setImage(temp.getImage().getScaledInstance(55, 70,  
                Image.SCALE_DEFAULT));  
		avatars[2]=temp.getImage();
		temp=new ImageIcon("……");
		temp.setImage(temp.getImage().getScaledInstance(55, 70,  
                Image.SCALE_DEFAULT));  
		avatars[3]=temp.getImage();
		cardBackImage=new ImageIcon("……").getImage();
		cardImages=new Image[4][13];
		for(int i = 0; i < suit.length; i++){
			for(int j = 0; j < rank.length; j++){
				cardImages[i][j] = new ImageIcon("……"+rank[j]+suit[i]+".gif").getImage();
			};
		};
		frame.setSize(1200, 900);
		frame.setVisible(true);
	}
	private BigTwoClient game;
	private boolean []selected=new boolean[13];
	private int activePlayer;
	private JFrame frame;
	private JPanel bigTwoPanel;
	private JButton playButton;
	private JButton passButton;
	private MenuBar bar=new MenuBar();
	private Menu menu=new Menu("Game");
	private MenuItem connect=new MenuItem("Connect");
	private MenuItem quit=new MenuItem("Quit");
	private JTextArea msgArea;
	private JTextArea chatArea;
	private JTextField message;
	private Image[][] cardImages;
	private Image cardBackImage;
	private Image[] avatars;
	 /**a method for setting the index of the active player 
     * @param activePlayer
     *                    the index of active player (i.e. the current player)
     */
	public void setActivePlayer(int activePlayer){
		this.activePlayer=activePlayer;
	}
	/**return an array of indices of the cards selected.
     * @return an array of indices of the cards selected.
     */
	public int[] getSelected(){
		int[] cardIdx = null;
		int count = 0;
		for (int j = 0; j < selected.length; j++) {
			if (selected[j]) {
				count++;
			}
		}
		if (count != 0) {
			cardIdx = new int[count];
			count = 0;
			for (int j = 0; j < selected.length; j++) {
				if (selected[j]) {
					cardIdx[count] = j;
					count++;
				}
			}
		}
		return cardIdx;
	}
	/**reset the list of selected cards.
     */
	public void resetSelected(){
		selected=new boolean[13];
		for(int i=0;i<13;i++){selected[i]=false;}
	}
	/**
     * repaint the GUI.
     */
	public void repaint(){
		if(game.getCurrentIdx() != activePlayer){
			disable();
		}else{
			enable();
		}
		frame.repaint();
	}
	 /**print the specified string to the message area of the GUI.
     * @param msg
     *            the specified string
     */
	public void printMsg(String msg){
		msgArea.append(msg+"\n");
	}
	public void printChat(String msg){
		chatArea.append(msg+"\n");
	}
	/**
     * clear the message area of the GUI.
     */
	public void clearMsgArea(){
		msgArea.setText("");
	}
	/**
     * resetting the GUI (the list of selected cards, clear the message area, enable user interactions)
     */
	public void reset(){
		resetSelected();
		clearMsgArea();
		chatArea.setText("");
		enable();
	}
	/**
     * enabling user interactions with the GUI (enable the “Play” button and “Pass” button, 
     * enable the BigTwoPanel for selection of cards through mouse clicks.)
     */
	public void enable(){
		playButton.setEnabled(true);
		passButton.setEnabled(true);
		Component[] com=bigTwoPanel.getComponents();
		for(int i=0;i<com.length;i++){
			com[i].setEnabled(true);
		}
	}
	/**
     * disable user interactions with the GUI (disable the “Play” button and “Pass” button, 
     * disable the BigTwoPanel for selection of cards through mouse clicks.)
     */
	public void disable(){
		playButton.setEnabled(false);
		passButton.setEnabled(false);
		Component[] com=bigTwoPanel.getComponents();
		for(int i=0;i<com.length;i++){
			com[i].setEnabled(false);
		}
	}
	/**an inner class that extends the JPanel class and implements the MouseListener interface
	 * Overrides the paintComponent() method inherited from the JPanel class to draw the card game table. 
	 * Implements the mouseClicked() method from the MouseListener interface to handle mouse click events.
	 * @author why
	 *
	 */
	class BigTwoPanel extends JPanel implements MouseListener{
		BigTwoPanel(){
			this.setVisible(true);
			this.addMouseListener(this);
			}
		
		/**draw the card game table
	     * @param g
	     *            graphics to draw the table
	     */
		public void paintComponent(Graphics g) {
			Graphics2D g2d=(Graphics2D) g;
			int fWidth = this.getWidth();
			int	fHeight = this.getHeight();
			int aHeight = avatars[0].getHeight(this);
			int cWidth = cardBackImage.getWidth(this);
			int cHeight = cardBackImage.getHeight(this);
			g2d.setColor(new Color(0,112,26));
			g2d.fillRect(0, 0, fWidth, fHeight);
			int player = activePlayer;
			int numOfCards = game.getPlayerList().get(activePlayer).getNumOfCards();
			int cardLocationX = fWidth/2 - numOfCards/2*cWidth/2 - cWidth/2;
			int cardLocationY = fHeight-aHeight-2*cHeight-10;
			String playerName = game.getPlayerName();
			for(int i = 0; i < numOfCards; i++){
				int suit = game.getPlayerList().get(player).getCardsInHand().getCard(i).getSuit();
				int rank = game.getPlayerList().get(player).getCardsInHand().getCard(i).getRank();
				if(!selected[i]){
					g2d.drawImage(cardImages[suit][rank], cardLocationX, cardLocationY, this);
				}else{
					g2d.drawImage(cardImages[suit][rank], cardLocationX, cardLocationY-10, this);
				}
				cardLocationX += cWidth/2;
			}
			g2d.drawImage(avatars[player], 5,cardLocationY+20, this);
			if(player ==  game.getCurrentIdx()){g2d.setColor(Color.YELLOW);};
			g2d.drawString(playerName, 5, cardLocationY);
			g2d.setColor(Color.WHITE);	

			player = activePlayer+1>3? activePlayer-3:activePlayer+1;
			if(game.getPlayerList().get(player).getName() != null){
				numOfCards = game.getPlayerList().get(player).getNumOfCards();
				cardLocationX = fWidth/2 - numOfCards/2*cWidth/2 - cWidth/2;
				cardLocationY = cardLocationY-cHeight-20;
				playerName = game.getPlayerList().get(player).getName();			
				for(int i = 0; i < numOfCards; i++){
					g2d.drawImage(cardBackImage, cardLocationX, cardLocationY, this);
					cardLocationX += cWidth/2;
				}
				g2d.drawImage(avatars[player], 5, cardLocationY+20,  this);
				if(player ==  game.getCurrentIdx()){g2d.setColor(Color.YELLOW);};
				g2d.drawString(playerName, 5, cardLocationY);
				g2d.setColor(Color.WHITE);
			}
	
			player = activePlayer+2>3? activePlayer+2-4:activePlayer+2;
			if(game.getPlayerList().get(player).getName() != null){
				numOfCards = game.getPlayerList().get(player).getNumOfCards();
				cardLocationX = fWidth/2 - numOfCards/2*cWidth/2 - cWidth/2;
				cardLocationY =cardLocationY-20-cHeight;
				playerName = game.getPlayerList().get(player).getName();			
				for(int i = 0; i < numOfCards; i++){
					g2d.drawImage(cardBackImage, cardLocationX, cardLocationY, this);
					cardLocationX += cWidth/2;
				}	
				g2d.drawImage(avatars[player], 5, cardLocationY+20,  this);
				if(player ==  game.getCurrentIdx()){g2d.setColor(Color.YELLOW);};
				g2d.drawString(playerName, 5, cardLocationY-10);
				g2d.setColor(Color.WHITE);
			}	
		
			player = activePlayer+3>3? activePlayer+3-4:activePlayer+3;
			if(game.getPlayerList().get(player).getName() != null){
				numOfCards = game.getPlayerList().get(player).getNumOfCards();
				cardLocationX = fWidth/2 - numOfCards/2*cWidth/2 - cWidth/2;
				cardLocationY = cardLocationY-20-cHeight;
				playerName = game.getPlayerList().get(player).getName();					
				for(int i = 0; i < numOfCards; i++){				
					g2d.drawImage(cardBackImage, cardLocationX, cardLocationY, this);
					cardLocationX += cWidth/2;
				}
				g2d.drawImage(avatars[player],5, cardLocationY+20, this);
				if(player ==  game.getCurrentIdx()){g2d.setColor(Color.YELLOW);};
				g2d.drawString(playerName, 5, cardLocationY);
				g2d.setColor(Color.WHITE);
			}

			if(game.getHandsOnTable().size()-1 >= 0){
				Hand lastHand = game.getHandsOnTable().get(game.getHandsOnTable().size()-1);
				numOfCards = lastHand.size();
				cardLocationX = fWidth/2 - numOfCards/2*cWidth/2 - cWidth/2;
				cardLocationY = fHeight - cHeight-20;
				playerName = lastHand.getPlayer().getName();
				for(int i = 0; i < numOfCards; i++){
					int suit = lastHand.getCard(i).getSuit();
					int rank = lastHand.getCard(i).getRank();
					g2d.drawImage(cardImages[suit][rank], cardLocationX, cardLocationY, this);
					cardLocationX += cWidth/2;
				}
				g2d.drawString( "Played by: " + playerName, 5,cardLocationY);
			}
		}
	     /**overrides the mouseClicked method to determine whether mouse is on a certain card
	     * @param e
	     *            mouse event
	     */
		public void mouseClicked(MouseEvent e){
			if(e.getButton() == MouseEvent.BUTTON1){
			int xm = e.getX();
			int ym = e.getY();
			int numOfCards = game.getPlayerList().get(activePlayer).getNumOfCards();
			int fWidth = this.getWidth();
			int	fHeight = this.getHeight();
			int aHeight = avatars[0].getHeight(this);
			int cWidth = cardBackImage.getWidth(this);
			int cHeight = cardBackImage.getHeight(this);
			int cardLocationX = fWidth/2 - numOfCards/2*cWidth/2 - cWidth/2;
			int cardLocationY = fHeight-aHeight-2*cHeight-10;
			for(int i = numOfCards-1; i >= 0; i--){
				if(xm > cardLocationX+i*cWidth/2 && xm < cardLocationX+(i+2)*cWidth/2){
					if(selected[i]){
						if(ym > cardLocationY-10 && ym < cardLocationY+cHeight-10){
							selected[i] = false;
							break;
						}else if(ym > cardLocationY+cHeight-10 && ym < cardLocationY+cHeight){
							if(i != 0 && !selected[i-1]){
								selected[i-1] = true;
								break;
							}
						}
					}else{
						if(ym > cardLocationY && ym < cardLocationY+cHeight){
							selected[i] = true;
							break;
						}
					}
				}
			}
			}frame.repaint();
		}
		public void mouseEntered(MouseEvent e){}
		public void mouseReleased(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mousePressed(MouseEvent e){}
	}
	
	class ChatListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			String msg = message.getText();
			CardGameMessage chatMessage = new CardGameMessage(7, -1, msg);
			game.sendMessage(chatMessage);
			message.setText("");

		}
	}
	/**an inner class that implements the ActionListener interface.
	 * Implements the actionPerformed() method from the ActionListener 
	 * interface to handle button-click events to play
	 * @author why
	 *
	 */
	class PlayButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event){	
			game.makeMove(activePlayer,getSelected());
			
		}
	}
	/**an inner class that implements the ActionListener interface. 
	 * Implements the actionPerformed() method from the ActionListener interface 
	 * to handle button-click events to pass
	 * @author why
	 *
	 */
	class PassButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			
			resetSelected();
			game.makeMove(activePlayer,null);
		}
	}
	/**an inner class that implements the ActionListener interface. 
	 * Implements the actionPerformed() method from the ActionListener 
	 * interface to establish a connection to the game server.
	 * @author why
	 *
	 */
	class ConnectMenuItemListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
	    	game.makeConnection();
			
		}
	}
	/**an inner class that implements the ActionListener interface.
	 *  Implements the actionPerformed() method from the ActionListener 
	 *  interface to handle menu-item-click events to quit the game
	 * @author why
	 *
	 */
	class QuitMenuItemListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			System.exit(0);
		}
	}
}
