import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

    public class UNOGame {
    private List<Player> players;
    private List<Card> deck;
    private Card topCard;
    private int currentPlayerIndex;


    public UNOGame(String[] playerNames) {
        players = new ArrayList<>();

        // create players
        for (String name : playerNames) {
            players.add(new Player(name));
        }

        initializeDeck(); //method for initialize the deck
        shuffleDeck(); //method for shuffling the deck
        dealInitialCards(); //method for deal initial cards
        setTopCard(); //method for setting the card that's on the top



        currentPlayerIndex = 0; //the game starts with first index player, (#1)
    }

    private void initializeDeck() { //initialize deck storing all the cards
        deck = new ArrayList<>();
        String[] colors = {"Blue", "Yellow", "Green", "Red"};
        String[] values = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

        for (String color : colors) { //for loop which creates all the possible combination of cards
            for (String value : values) {
                deck.add(new Card(color, value));
                if (!value.equals("0")) {
                    deck.add(new Card(color, value));
                }
            }
        }
    }

    // shuffle the deck
     private void shuffleDeck() { //Fisher-Yates shuffle algorithm from GeeksforGeeks on https://www.geeksforgeeks.org/shuffle-a-given-array-using-fisher-yates-shuffle-algorithm/
        Random rand = new Random();
        for (int i = deck.size() - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            Card temp = deck.get(i);
            deck.set(i, deck.get(j));
            deck.set(j, temp);

             }


    }

    private void dealInitialCards() { //gives initial card to the players
        for (Player player : players) {
            for (int i = 0; i < 7; i++) {
                player.drawCard(deck.remove(0));
            }
        }
    }

    private void setTopCard() { //sets the card on the top and removes it from the deck
        topCard = deck.remove(0);
    }

    private boolean isGameOver() { //if a player hand is empty, game is over
        for (Player player : players) {
            if (player.getHand().isEmpty()) {
                return true;
            }
        }
        return false;
    }

        private void announceWinner() { //method for announcing the winner
            Player winner = null;
                int minHandSize = Integer.MAX_VALUE;

            for (Player player : players) {       //for for find the player with the smallest hand size
                if (player.getHand().size() < minHandSize) {
                winner = player;
                minHandSize = player.getHand().size();
            }
        }

        System.out.println("\nCongratulations! The game is over.");
        System.out.println("The winner is: " + winner.getName());
        System.out.println("Thanks for playing, come back soon...");
    }

    public void startGame() { //game starts
        System.out.println("The game has started! Take into consideration the following");
        System.out.println("Below, the card that is on the top will be displayed");
        System.out.println("Pick a card that matches the color or the number of the card on the top");
        System.out.println("For picking a card, input the index for the card that you want to play");
        System.out.println("Ej. If your deck is [Green 1, Blue 7, Blue 7, Blue 2, Blue 8, Green 6, Red 4]");
        System.out.println("Type 0 for playing Green 1, 1 for playing Blue 7, and so on...");
        System.out.println("Have fun !");


        System.out.println("The card on the top: " + topCard);

           while (!isGameOver()) {
                Player currentPlayer = players.get(currentPlayerIndex);

            System.out.println("\n" + currentPlayer.getName() + "'s turn");
            System.out.println("The card on the top: " + topCard);
            System.out.println("Your deck is: " + currentPlayer.getHand());
            if (currentPlayer.hasPlayableCard(topCard)) {
                   System.out.println("Enter the card index you want to play (remember that the index starts at 0):"); //if player has a playable card
                   Scanner scanner = new Scanner(System.in);
                                int cardIndex = scanner.nextInt() ;

                     if (cardIndex >= 0 && cardIndex < currentPlayer.getHand().size()) { //check if the card is valid
                    Card playedCard = currentPlayer.playCard(cardIndex);

                           if (playedCard.getColor().equals(topCard.getColor())    // check if the played card matches the top card's color or value
                            || playedCard.getValue().equals(topCard.getValue())) {
                                    topCard = playedCard;
                             } else {
                            System.out.println("Invalid move.");
                         }
                } else {
                    System.out.println("Invalid index.");
                          }
              } else {
                     Card drawnCard = deck.remove(0); //draws a card when there are not playable cards
                      currentPlayer.drawCard(drawnCard);
                    System.out.println(currentPlayer.getName() + " fished a card: " + drawnCard);
            }

            if (currentPlayer.getHand().size() == 1) { //if a player has only one card left
                System.out.println(currentPlayer.getName() + " says UNO!");
             }

                    currentPlayerIndex = (currentPlayerIndex + 1) % players.size(); //gives turn to the next player
          }

        announceWinner();
                   }
    }
