import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Hangman {

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		
		beginningText();
		
		//Initializing Game Variables
		String word = wordList();
		String lineState = initLines(word);
		Integer numParts = 0;
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		//0 means that game is ongoing, 1 means that player has won, 2 means that player has lost
		Integer gameState = 0;
		
		boolean gameLoop = true;
		
		while (gameLoop){
			printText("\nEnter a command: ");
			String command = reader.nextLine();
			
			//Quit the game
			if (command.equals("2")){
				gameLoop = false;
				reader.close();
				printText("Thanks for playing!");
			
			//Gets the game status
			}else if (command.equals("1")){
				getStatus(numParts, letters, lineState);
			//Checks if command is a valid letter and if it is only one letter
			}else if (command.length() > 0){
				if (getAlpha(command) && command.length() == 1){
					lineState = updateLineState(command, word, lineState);
					letters = updateLetters(command, letters);
					numParts = updateNumParts(command, word, numParts);
					gameState = checkGame(numParts, lineState);
					
					if (gameState > 0){
						//Player has won
						if (gameState == 1){
							printText("\nYOU WON!");
						//Player has lost
						}else if (gameState == 2){
							printText("\nYOU LOST");
						}
						printText("Play Again? ('y' for YES)");
						String playAgain = reader.nextLine();
						
						if (playAgain.toLowerCase().equals("y")){
							//Resets game variables to prepare for a new game
							word = wordList();
							lineState = initLines(word);
							numParts = 0;
							letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
							gameState = 0;
							
							beginningText();
						}else{
							gameLoop = false;
							reader.close();
							printText("Thanks for playing!");
						}
					}
				}
			}else{
				printText("Invalid Command");
			}
		}
	}
	
	public static void beginningText(){
		printText("Welcome to Hangman!");
		printText("-Enter '1' to get game status-");
		printText("-Enter '2' to quit-");
	}
	
	public static Integer checkGame(Integer numParts, String lineState){
		Integer gameState = 0;
		if (numParts > 4){
			gameState = 2;
		}else if(lineState.indexOf("_") == -1){
			gameState = 1;
		}
		return gameState;
	}
	
	public static String updateLineState(String letter, String word, String lineState){
		printText("You picked the letter: " + letter);
		String newLineState =  lineState;
		letter = letter.toUpperCase();		
		int index = word.indexOf(letter);
		while (index >= 0){
			//Testing stuff
			//printText(Integer.toString(index));
			//printText(Integer.toString(newLineState.length()));
			
			//Index is times 2 because there are spaces between each blank so that puts it in the right spot
			newLineState = replace(newLineState, index*2, letter.charAt(0));
			index = word.indexOf(letter.toUpperCase(), index + 1);
		}
		
		printText(newLineState);
		return newLineState;
	}
	
	public static String updateLetters(String letter, String letters){
		letter = letter.toUpperCase();
		if (letters.indexOf(letter) > -1){
			letters = replace(letters, letters.indexOf(letter), "-".charAt(0));
		}else{
			printText(letter + " has already been used.");
		}
		return letters;
	}
	
	public static Integer updateNumParts(String letter, String word, Integer numParts){
		letter = letter.toUpperCase();
		if (word.indexOf(letter.charAt(0)) > -1){
			printText("Letter Found!");
		}else{
			printText("Letter NOT Found!");
			numParts ++;
			printMan(numParts);
		}
		
		return numParts;
	}
	
	public static String replace(String str, int index, char replace){
		char[] strChars = str.toCharArray();
		strChars[index] = replace;
				
		return String.valueOf(strChars);
	}
	
	public static String initLines(String word){
		String lines = "";
		for (int x = 0; x < word.length(); x ++){
			//This is to not put a space at the end
			if (x == word.length() - 1){
				lines += "_"; 
			}else{
				lines += "_ ";
			}
		}
		
		return lines;
	}
	
	public static String wordList(){
		ArrayList<String> words = new ArrayList<String>();
		
		words.add("BANANA");
		words.add("PUPPY");
		words.add("COMPUTER");
		words.add("POTATO");
		words.add("WATER");
		words.add("HYDROGEN");
		words.add("AVACADO");
		
		//Shuffles the list
		Collections.shuffle(words);
				
		return words.get(0);
	}
	
	//Checks if first character in string is a letter
	public static boolean getAlpha(String word){
		return Character.isLetter(word.charAt(0));
	}
	
	public static void printText(String text){
		System.out.println(text);
	}
	
	//Gets game status by displaying hangman state and available letters
	public static void getStatus(Integer numParts, String letters, String lineState){
		printText("-GAME STATE-");
		printMan(numParts);
		printText(lineState);
		printText("Available letters: " + letters);
		printText("");
	}
	
	public static void lettersFound(){}
	
	//This produces the image of the hangman depending on the amount of parts he has
	public static void printMan(Integer numParts){
		printText("____");
		if (numParts > 0){
			printText("|  O");
			
			if (numParts > 1){
				if (numParts == 2){
					printText("| -|");
				}else if (numParts > 2){
					printText("| -|-");
				}
				if (numParts  > 3){
					if (numParts == 4){
						printText("| /");
					}else if (numParts > 4){
						printText("|  /\\");
					}
				}else{
					printText("|");
				}
			}else{
				printText("|");
				printText("|");
			}
		}else{
			printText("|");
			printText("|");
			printText("|");
		}
		
		printText("A");
	}
}
