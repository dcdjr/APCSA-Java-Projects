import java.util.*;

public class BlackjackMaster {
    // Constants for menu options
    private static final int MENU_SEE_BANKROLL = 1;
    private static final int MENU_PLAY = 2;
    private static final int MENU_QUIT = 3;

    public static void displayMenu() {
        System.out.println("1. See Bankroll");
        System.out.println("2. Play");
        System.out.println("3. Quit");
    }

    public static ArrayList<Card> newDeck() {
        ArrayList<Card> deck = new ArrayList<Card>();
        for (int n = 0; n < 4; n++) {
            for (int i = 1; i <= 4; i++) {
                for (int j = 1; j <= 13; j++) {
                    deck.add(new Card(i, j));
                }
            }
        }
        return deck;
    }

    public static void newHand(ArrayList<Card> deck, ArrayList<Card> hand) {
        int index = (int) (deck.size() * Math.random());
        hand.add(deck.get(index));
    }

    public static void hit(ArrayList<Card> deck, ArrayList<Card> hand) {
        int index = (int) (deck.size() * Math.random());
        hand.add(deck.get(index));
    }

    public static void displayHand(ArrayList<Card> hand, String playerName) {
        System.out.println(playerName + "'s Hand:");
        for (Card card : hand) {
            System.out.println(card.cardName());
        }
        System.out.println("Total value: " + calcHandValue(hand));
    }

    public static int calcHandValue(ArrayList<Card> hand) {
        int sum = 0;
        int numAces = 0;
        for (Card card : hand) {
            int value = card.getValue();
            if (value == 1) {
                numAces++;
            }
            sum += Math.min(value, 10);
        }

        while (numAces > 0 && sum + 10 <= 21) {
            // Using the Ace as 11 won't cause bust
            sum += 10;
            numAces--;
        }

        if (sum > 21) {
            return -1; // Bust
        }
        return sum;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int bankroll = 1000; // Initial bankroll
        int choice = 0;
        ArrayList<Card> dealerHand = new ArrayList<>();
        ArrayList<Card> playerHand = new ArrayList<>();
        ArrayList<Card> deck = newDeck();
        System.out.println("Welcome to Blackjack Master!");

        while (choice != MENU_QUIT) {
            playerHand.clear();
            dealerHand.clear();

            System.out.println("Choose an option:");
            displayMenu();
            choice = input.nextInt();

            if (choice == MENU_SEE_BANKROLL) {
                System.out.println("You currently have $" + bankroll + " in your bankroll.");
            } else if (choice == MENU_PLAY) {
                System.out.println("How much would you like to bet?");
                int bet = input.nextInt();
                while (bet > bankroll || bet == 0) {
                    if (bet > bankroll) {
                        System.out.println("Insufficient funds!");
                        System.out.println("How much would you like to bet?");
                        bet = input.nextInt();
                    } else {
                        System.out.println("You cannot bet 0 dollars!");
                        System.out.println("How much would you like to bet?");
                        bet = input.nextInt();
                    }
                }
                bankroll -= bet;

                // Deal initial hands
                newHand(deck, dealerHand);
                newHand(deck, playerHand);
                newHand(deck, playerHand); // Deal the second card for splitting

                // Display initial hands
                displayHand(dealerHand, "Dealer");
                displayHand(playerHand, "Player");

                // Check for blackjack
                if (calcHandValue(playerHand) == 21) {
                    System.out.println("Blackjack! You win 1.5 times your bet!");
                    bankroll += (int) (1.5 * bet);
                    
                }

                boolean split = false;
// Player's turn
                while (true) {
                    int playerValue = calcHandValue(playerHand);
                    if (playerValue == -1) {
                        System.out.println("Bust! You lose.");
                        break;
                    }
                    System.out.println("1 = Hit, 2 = Stand, 3 = Double Down, 4 = Split");
                    int playerChoice = input.nextInt();
                    if (playerChoice == 1) {
                        hit(deck, playerHand);
                        displayHand(playerHand, "Player");
                        if (calcHandValue(playerHand) == 21) {
                            System.out.println("You got 21!");
                            break;
                        }
                    } else if (playerChoice == 2) {
                        break;
                    } else if (playerChoice == 3) {
                        if (playerHand.size() != 2) {
                            System.out.println("You can only double down on your first two cards!");
                            continue;
                        }
                        int doubleDownBet = bet * 2;
                        if (doubleDownBet > bankroll) {
                            System.out.println("Insufficient funds to double down!");
                            continue;
                        }
                        if (calcHandValue(playerHand) == 21) {
                            System.out.println("You cannot double down when you have a blackjack!");
                            continue;
                        }
                        bet = doubleDownBet;
                        bankroll -= bet; // Reduce bankroll by the doubled bet
                        hit(deck, playerHand);
                        displayHand(playerHand, "Your");
                        if (calcHandValue(playerHand) == -1) {
                            System.out.println("Bust! You lose.");
                            break;
                        }
                    } else if (playerChoice == 4) {
                        if (playerHand.size() != 2 || playerHand.get(0).getValue() != playerHand.get(1).getValue()) {
                            System.out.println("You can only split when you have two cards of the same rank!");
                            continue;
                        }
                        // Split the hand
                        ArrayList<Card> secondHand = new ArrayList<>();
                        secondHand.add(playerHand.remove(1)); // Move the second card to the new hand
                        // Deal a new card to each hand
                        newHand(deck, playerHand);
                        newHand(deck, secondHand);
                        // Play out each hand individually
                        System.out.println("First hand:");
                        displayHand(playerHand, "Your");
                        while (true) {
                            int playerValue1 = calcHandValue(playerHand);
                            if (playerValue1 == -1) {
                                System.out.println("Bust! You lose on the first hand.");
                                break;
                            }
                            System.out.println("1 = Hit, 2 = Stand");
                            int playerChoice1 = input.nextInt();
                            if (playerChoice1 == 1) {
                                hit(deck, playerHand);
                                displayHand(playerHand, "Your");
                                if (calcHandValue(playerHand) == 21) {
                                    System.out.println("You got 21 on the first hand!");
                                    break;
                                }
                            } else if (playerChoice1 == 2) {
                                break;
                            }
                        }
                        System.out.println("Second hand:");
                        displayHand(secondHand, "Your");
                        while (true) {
                            int playerValue2 = calcHandValue(secondHand);
                            if (playerValue2 == -1) {
                                System.out.println("Bust! You lose on the second hand.");
                                break;
                            }
                            System.out.println("1 = Hit, 2 = Stand");
                            int playerChoice2 = input.nextInt();
                            if (playerChoice2 == 1) {
                                hit(deck, secondHand);
                                displayHand(secondHand, "Your");
                                if (calcHandValue(secondHand) == 21) {
                                    System.out.println("You got 21 on the second hand!");
                                    break;
                                }
                            } else if (playerChoice2 == 2) {
                                break;
                            }
                        }
                        // After both hands are played, determine winnings and losses for each hand
                        int playerValue1 = calcHandValue(playerHand);
                        int playerValue2 = calcHandValue(secondHand);
                        if ((playerValue1 > 21 && playerValue2 > 21) || (playerValue1 == playerValue2)) {
                            System.out.println("Both hands lose.");
                        } else if (playerValue1 > 21 || (playerValue2 <= 21 && playerValue2 > playerValue1)) {
                            System.out.println("Second hand wins.");
                            bankroll += bet;
                        } else if (playerValue2 > 21 || (playerValue1 <= 21 && playerValue1 > playerValue2)) {
                            System.out.println("First hand wins.");
                            bankroll += bet;
                        }
                        break;
                    }
                }



                // Dealer's turn
                if (calcHandValue(playerHand) <= 21) {
                    while (calcHandValue(dealerHand) < 17) {
                        hit(deck, dealerHand);
                    }
                    displayHand(dealerHand, "Dealer");

                    // Determine the winner
                    int dealerValue = calcHandValue(dealerHand);
                    int playerValue = calcHandValue(playerHand);
                    if (playerValue > 21 || (dealerValue <= 21 && dealerValue > playerValue)) {
                        System.out.println("You lose.");
                    } else if (playerValue == dealerValue) {
                        System.out.println("It's a tie.");
                        bankroll += bet;
                    } else {
                        System.out.println("You win!");
                        bankroll += 2 * bet;
                    }
                }
            } else if (choice == MENU_QUIT) {
                System.out.println("Exiting game.");
                break;
            } else {
                System.out.println("Invalid choice. Please choose again.");
            }
        }
        input.close();
    }
}