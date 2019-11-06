/**
 * Game class for chasing-bombs-jk577 
 * @author Jamie Knott
 * @version 2019.03.14
 */

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class Game extends JFrame{

	/**
	 * The game class fields 
	 */
	private JFrame frame;
	private JPanel leftSection, middleSection, rightSection;
	private JButton playGame, exitGame;
	private JLabel scoreLabel;
	private boolean isClicked, isPlaying, levelEasy, levelIntermediate, levelHard;
	private int playerScore;
	private Game game;
	
	
	/**
	 * Game constructor sets up the game's initial jframe
	 * as well as initialising the class fields
	 */
	public Game() {

		makeFrame();
		isClicked = false;
		isPlaying = false;
		levelEasy = true;
		levelIntermediate = false;
		levelHard = false;
		playerScore = 0;

	}

	/**
	 * Method for randomly generating a 1-9 number using .math functions
	 * @return randomLocation an random integer which will serve as a random point in an array index
	 */
	public int hideBomb() {

		int randomLocation = (int) Math.floor(Math.random() * 9);
		randomLocation = Math.round(randomLocation);

		return randomLocation; 

	}
	/**
	 * Method for reseting the game stats as well as replacing the
	 * game area with a new area (Through the Game constructor)
	 */	
	public void resetGame() {

		playerScore = 0;
		frame.setVisible(false);
		game = new Game();

	}
	/**
	 * Method for starting the actual game.
	 * Replaces the original jframe template with a dynamically active new frame.
	 * Responsible for handling onclick events with the left section jpanels 
	 * as well as determining winning conditions/difficulty levels
	 */	
	public void playGame() {

		isPlaying = true;

		if(isClicked == false && isPlaying == true) {

			frame.getContentPane().remove(leftSection);
			frame.getContentPane().remove(middleSection);
			frame.getContentPane().remove(rightSection);

			JPanel leftPanel = new JPanel();

			GridLayout grid = new GridLayout(2, 5);
			GridLayout gameStructure = new GridLayout(1, 3);

			frame.setLayout(gameStructure);

			leftPanel.setLayout(grid);
			leftPanel.setSize(333, 500);


			JPanel [] gameSquares = new JPanel[10];

			//Loop for generating and formatting the 10 game squares as well as containing event handling 
			for(int index = 0; index < gameSquares.length; index++) {
				final int squareIndex = index;

				gameSquares[index] = new JPanel();
				leftPanel.add(gameSquares[index]);
				gameSquares[index].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				gameSquares[index].setBackground(Color.BLUE);

				JPanel clickedSquare = new JPanel();
				clickedSquare.setBackground(Color.YELLOW);
				clickedSquare.setBorder(BorderFactory.createLineBorder(Color.BLACK));

				gameSquares[index].addMouseListener(new MouseAdapter(){

					JPanel bomb = gameSquares[hideBomb()];

					@Override
					public void mouseClicked(MouseEvent e) {
						if(e.getSource() == gameSquares[squareIndex] && isPlaying == true && gameSquares[squareIndex].getBackground() == Color.BLUE) {
							
							gameSquares[squareIndex].setBackground(Color.YELLOW);
							playerScore++;
							scoreLabel.setText("Your score is: " + playerScore);
							scoreLabel.setBackground(Color.BLACK);
							
							if(levelEasy == true && playerScore >= 5) {
								
								isPlaying = false; 
								scoreLabel.setText("You win! You got " + playerScore + " points.");
								
							}
							else if(levelIntermediate == true && playerScore >= 7) {
								
								isPlaying = false;
								scoreLabel.setText("You win! You got " + playerScore + " points.");
								
							}
							else if(levelHard == true && playerScore >= 9 ) {
								
								isPlaying = false;
								scoreLabel.setText("You win! You got " + playerScore + " points.");
								
							}
							
							if(e.getSource() == bomb && isPlaying == true) {

								isPlaying = false;
								bomb.setBackground(Color.RED);
								playerScore--;

								if(playerScore == 1) {

									scoreLabel.setText("You lose! You got " + playerScore + " point.");


								}
								else {

									scoreLabel.setText("You lose! You got " + playerScore + " points.");

								}
							}
						}
					}
				});

				isClicked = true;

				frame.add(leftPanel);
				frame.add(middleSection);
				frame.add(rightSection);

				frame.setVisible(true);
			}
		}
	}

	/**
	 * Method for constructing the inital jframe for the game
	 * which includes all of the components and their layout
	 */
	public void makeFrame() {

		frame = new JFrame("Avoid-the-bomb");
		frame.setSize(1000, 500);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		leftSection = new JPanel();
		middleSection = new JPanel();
		rightSection = new JPanel();

		GridLayout grid = new GridLayout(2, 5);
		GridLayout gameStructure = new GridLayout(1, 3);

		frame.setLayout(gameStructure);

		leftSection.setLayout(grid);
		leftSection.setSize(333, 500);

		JPanel [] gameSquares = new JPanel[10];

		//Loop for generating and formatting the 10 game squares into the left section panel, using array of Jpanels
		for(int index = 0; index < gameSquares.length; index++) {

			gameSquares[index] = new JPanel();
			leftSection.add(gameSquares[index]);
			gameSquares[index].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			gameSquares[index].setBackground(Color.BLUE);

		}

		Dimension buttonSize = new Dimension(300, 125);
		Font buttonFont = new Font("Arial", Font.BOLD, 23);

		playGame = new JButton("Play the game");
		playGame.addActionListener(source ->  playGame());

		exitGame  = new JButton("exit");
		exitGame.addActionListener(source -> resetGame());

		scoreLabel = new JLabel();

		middleSection.setSize(333, 500);
		middleSection.setBackground(Color.WHITE);

		playGame.setPreferredSize(buttonSize);
		exitGame.setPreferredSize(buttonSize);
		scoreLabel.setPreferredSize(buttonSize);

		playGame.setBackground(Color.GREEN);
		exitGame.setBackground(Color.RED);

		playGame.setFont(buttonFont);
		exitGame.setFont(buttonFont);
		scoreLabel.setFont(buttonFont);

		playGame.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		exitGame.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		middleSection.add(playGame);
		middleSection.add(Box.createHorizontalStrut(100));
		middleSection.add(exitGame);	
		middleSection.add(Box.createHorizontalStrut(100));
		middleSection.add(scoreLabel);
		middleSection.add(Box.createHorizontalStrut(100));

		rightSection.setSize(333, 500);
		rightSection.setBackground(Color.RED);

		JButton easy = new JButton("Easy");
		JButton intermediate = new JButton("Intermediate");
		JButton hard = new JButton("Hard");
				
		easy.setBackground(Color.ORANGE);	
		
		//Where the difficulty is set, using somewhat lengthy lambda functions
		easy.addActionListener(source -> { levelEasy = true; levelIntermediate = false; levelHard = false; easy.setBackground(Color.ORANGE); intermediate.setBackground(Color.GRAY); hard.setBackground(Color.GRAY);} );
		intermediate.addActionListener(source -> { levelEasy = false; levelIntermediate = true; levelHard = false; intermediate.setBackground(Color.ORANGE); easy.setBackground(Color.GRAY); hard.setBackground(Color.GRAY);});
		hard.addActionListener(source -> { levelEasy = false; levelIntermediate = false; levelHard = true; hard.setBackground(Color.ORANGE); easy.setBackground(Color.GRAY); intermediate.setBackground(Color.GRAY);});
				
		easy.setPreferredSize(buttonSize);
		intermediate.setPreferredSize(buttonSize);
		hard.setPreferredSize(buttonSize);

		easy.setFont(buttonFont);
		intermediate.setFont(buttonFont);
		hard.setFont(buttonFont);

		easy.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		intermediate.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		hard.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				
		intermediate.setBackground(Color.GRAY);
		hard.setBackground(Color.GRAY);

		rightSection.add(easy);
		rightSection.add(Box.createHorizontalStrut(100));
		rightSection.add(intermediate);
		rightSection.add(Box.createHorizontalStrut(100));
		rightSection.add(hard);
		rightSection.add(Box.createHorizontalStrut(100));	

		frame.add(leftSection);
		frame.add(middleSection);
		frame.add(rightSection);

		frame.setVisible(true);

	}
	
	 //Main method for the Game class
	public static void main(String[] args) {

		Game game = new Game();

	}
}
