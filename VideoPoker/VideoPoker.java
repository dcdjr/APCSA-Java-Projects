import java.io.CharArrayReader;
import java.lang.reflect.Array;
import java.util.*;

public class VideoPoker {

    public static void displayHand(ArrayList<Card> hand) {
        for (int i = 0; i < hand.size(); i++) {
            System.out.println(hand.get(i).cardName());
        }
    }

    public static void displayHandHold(ArrayList<Card> hand) {
        System.out.println("0 - DRAW");
        for (int i = 0; i < hand.size(); i++){
            System.out.println((i+1) + " - " + hand.get(i).cardName() + " - NOT HELD");
        }
    }

    public static void printOptions() {
        System.out.println("1. Insert Money\n" +
                "2. View Machine Payouts\n" +
                "3. Bet\n" +
                "4. Cash Out");
    }

    public static void viewMachinePayouts() {
        System.out.println("\nRoyal Flush: 250 * bet (Jackpot bet of 5: 4000)\n" +
                "Straight Flush: 50 * bet\n" +
                "4 of a Kind: 25 * bet\n" +
                "Full House: 8 * bet\n" +
                "Flush: 5 * bet\n" +
                "Straight: 4 * bet\n" +
                "3 of a Kind: 3 * bet\n" +
                "2 Pair: 2 * bet\n" +
                "One Pair (Jacks or Better): 1 * bet\n");
    }

    public static void newHand(ArrayList<Card> deck, ArrayList<Card> hand) {
        for (int i = 0; i < 5; i++) {
            int index = (int) (deck.size() * Math.random());
            hand.add(deck.get(index));
            deck.remove(index);
        }
    }

    public static ArrayList<Card> newDeck(){
        ArrayList<Card> deck = new ArrayList<Card>();
        for (int i = 1; i <= 4; i++){
            for (int j = 1; j <= 13; j++){
                deck.add(new Card(i, j));
            }
        }
        return deck;
    }


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        ArrayList<Card> hand = new ArrayList<Card>();
        ArrayList<Card> deck = newDeck();

        System.out.println("Welcome to Video Poker!");
        int money = -1;
        while (money < 0) {
            System.out.println("Insert money:");
            money = input.nextInt();
            if (money < 0) {
                System.out.println("You cannot insert a negative amount of money!");
            }
        }
        while (true) {
            System.out.println("You have " + money + " credits. What would you like to do?");
            printOptions();
            int choice = input.nextInt();
            switch (choice) {
                case 1:
                    int moneyInsert = -1;
                    while (moneyInsert < 0) {
                        System.out.println("How much money would you like to insert?");
                        moneyInsert = input.nextInt();
                        if (moneyInsert < 0) {
                            System.out.println("You cannot insert a negative amount of money!");
                        }
                    }
                    money = money + moneyInsert;
                    break;
                case 2:
                    System.out.println("The payouts for this machine are:");
                    viewMachinePayouts();
                    break;
                case 3:
                    int bet = 0;
                    while (bet > 5 || bet < 1) {
                        System.out.println("Bet how many credits (1-5)?");
                        bet = input.nextInt();
                        if (bet > 5 || bet < 1) {
                            System.out.println("You have to bet between 1 and 5 credits!");
                        }
                    }
                    System.out.println("Bet of " + bet + " credits confirmed.");
                    money -= bet;
                    System.out.println("The 5 cards in your hand are: ");
                    newHand(deck, hand);
                    displayHand(hand);
                    int selection = 10;
                    System.out.println("Select which cards to hold:");
                    displayHandHold(hand);
                    boolean status1 = false;
                    boolean status2 = false;
                    boolean status3 = false;
                    boolean status4 = false;
                    boolean status5 = false;
                    while (selection != 0) {
                        selection = input.nextInt();
                        if (selection < 0 || selection > 5) {
                            System.out.println("Please select a valid option!");
                            selection = input.nextInt();
                        } else {
                            switch (selection) {
                                case 1:
                                    status1 = !status1;
                                    break;
                                case 2:
                                    status2 = !status2;
                                    break;
                                case 3:
                                    status3 = !status3;
                                    break;
                                case 4:
                                    status4 = !status4;
                                    break;
                                case 5:
                                    status5 = !status5;
                                    break;
                            }
                            System.out.println("0 - DRAW");
                            if (status1) {
                                System.out.println("1 - " + hand.get(0).cardName() + " - HELD");
                            } else {
                                System.out.println("1 - " + hand.get(0).cardName() + " - NOT HELD");
                            }
                            if (status2) {
                                System.out.println("2 - " + hand.get(1).cardName() + " - HELD");
                            } else {
                                System.out.println("2 - " + hand.get(1).cardName() + " - NOT HELD");
                            }
                            if (status3) {
                                System.out.println("3 - " + hand.get(2).cardName() + " - HELD");
                            } else {
                                System.out.println("3 - " + hand.get(2).cardName() + " - NOT HELD");
                            }
                            if (status4) {
                                System.out.println("4 - " + hand.get(3).cardName() + " - HELD");
                            } else {
                                System.out.println("4 - " + hand.get(3).cardName() + " - NOT HELD");
                            }
                            if (status5) {
                                System.out.println("5 - " + hand.get(4).cardName() + " - HELD");
                            } else {
                                System.out.println("5 - " + hand.get(4).cardName() + " - NOT HELD");
                            }
                        }
                    }
                    int cardHoldCount = 0;
                    if (!status1) {
                        hand.remove(0);
                        hand.add(0, deck.get((int)(deck.size() * Math.random())));
                        cardHoldCount++;
                    }
                    if (!status2) {
                        hand.remove(1);
                        hand.add(1, deck.get((int)(deck.size() * Math.random())));
                        cardHoldCount++;
                    }
                    if (!status3) {
                        hand.remove(2);
                        hand.add(2, deck.get((int)(deck.size() * Math.random())));
                        cardHoldCount++;
                    }
                    if (!status4) {
                        hand.remove(3);
                        hand.add(3, deck.get((int)(deck.size() * Math.random())));
                        cardHoldCount++;
                    }
                    if (!status5) {
                        hand.remove(4);
                        hand.add(4, deck.get((int)(deck.size() * Math.random())));
                        cardHoldCount++;
                    }
                    System.out.println("Drawing " + cardHoldCount + " new cards... Your new hand is:");
                    displayHand(hand);
                    String handResult = "";
                    int payout = 0;
                    if (royalFlush(hand)) {
                        handResult = "Royal Flush";
                        payout = 250 * bet;
                    } else if (straightFlush(hand))  {
                        handResult = "Straight Flush";
                        payout = 50 * bet;
                    } else if (fourOfAKind(hand)) {
                        handResult = "Four Of A Kind";
                        payout = 25 * bet;
                    } else if (fullHouse(hand)) {
                        handResult = "Full House";
                        payout = 8 * bet;
                    } else if (flush(hand)) {
                        handResult = "Flush";
                        payout = 5 * bet;
                    } else if (straight(hand)) {
                        handResult = "Straight";
                        payout = 4 * bet;
                    } else if (threeOfAKind(hand)) {
                        handResult = "Three Of A Kind";
                        payout = 3 * bet;
                    } else if (twoPair(hand)) {
                        handResult = "Two Pair";
                        payout = 2 * bet;
                    } else if (onePair(hand)) {
                        handResult = "One Pair";
                        if (isJacksOrBetter(hand)) {
                            payout = bet;
                        }
                    } else {
                        handResult = "No Hand";
                    }

                    System.out.println("Hand Result: " + handResult + ". Payout: " + payout + " credits");
                    money += payout;
                    hand.clear();
                    break;
                case 4:
                    System.out.println("Printing voucher for " + money + " credits. Thanks for playing!");
                    System.exit(0);
            }
        }
    }

    // Royal Flush

    public static boolean royalFlush(ArrayList<Card> hand) {
        sortByValueAceHigh(hand);
        return hand.get(0).value == 10 && hand.get(1).value == 11 && hand.get(2).value == 12 && hand.get(3).value == 13 && hand.get(4).value == 1;
    }

    // Straight Flush

    public static boolean straightFlush(ArrayList<Card> hand) {
        boolean isFlush = false;
        int firstSuit = hand.get(0).suit;
        int counter = 0;
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).suit == firstSuit) {
                counter++;
            }
        }
        if (counter == 5)  {
            isFlush = true;
        }
        sortByValueAceLow(hand);
        for (int i = 0; i < hand.size()-1; i++) {
            if (((hand.get(i+1).value) - hand.get(i).value) != 1) {
                return false;
            }
        }
        return isFlush;
    }

    // Four Of A Kind

    public static boolean fourOfAKind(ArrayList<Card> hand) {
        boolean option1 = hand.get(0).value == hand.get(1).value && hand.get(1).value == hand.get(2).value && hand.get(2).value == hand.get(3).value;
        boolean option2 = hand.get(1).value == hand.get(2).value && hand.get(2).value == hand.get(3).value && hand.get(3).value == hand.get(4).value;
        return option1 || option2;
    }

    // Full House

    public static boolean fullHouse(ArrayList<Card> hand) {
        boolean option1 = hand.get(0).value == (hand.get(1).value) && hand.get(1).value == (hand.get(2).value) &&  hand.get(3).value == (hand.get(4).value);
        boolean option2 = hand.get(0).value == (hand.get(1).value) &&  hand.get(2).value == (hand.get(3).value) && hand.get(3).value == (hand.get(4).value);
        return option1 || option2;
    }

    // Flush

    public static boolean flush(ArrayList<Card> hand) {
        int firstSuit = hand.get(0).suit;
        int counter = 0;
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).suit == firstSuit) {
                counter++;
            }
        }
        return (counter == 5);
    }

    // Straight

    public static boolean straight(ArrayList<Card> hand) {
        for (int i = 0; i < hand.size()-1; i++) {
            if (((hand.get(i+1).value) - hand.get(i).value) != 1) {
                return false;
            }
        }
        return true;
    }

    // Three Of A Kind

    public static boolean threeOfAKind(ArrayList<Card> hand) {
        boolean option1 = hand.get(0).value == hand.get(1).value && hand.get(1).value == (hand.get(2).value);
        boolean option2 = hand.get(1).value == hand.get(2).value && hand.get(2).value == (hand.get(3).value);
        boolean option3 = hand.get(2).value == hand.get(3).value && hand.get(3).value == (hand.get(4).value);
        return option1 || option2 || option3;
    }

    // Two Pair

    public static boolean twoPair(ArrayList<Card> hand) {
        boolean option1 = hand.get(0).value == (hand.get(1).value) && hand.get(2).value == (hand.get(3).value);
        boolean option2 = hand.get(1).value == (hand.get(2).value) && hand.get(3).value == (hand.get(4).value);
        boolean option3 = hand.get(0).value == (hand.get(1).value) &&  hand.get(3).value == (hand.get(4).value);
        return option1 || option2 || option3;
    }

    // One Pair
    public static boolean onePair(ArrayList<Card> hand) {
        boolean option1 = hand.get(0).value == hand.get(1).value;
        boolean option2 = hand.get(1).value == hand.get(2).value;
        boolean option3 = hand.get(2).value == hand.get(3).value;
        boolean option4 = hand.get(3).value == hand.get(4).value;
        return option1 || option2 || option3 || option4;
    }

    public static boolean isJacksOrBetter(ArrayList<Card> hand) {
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).value >= 11) {
                return true;
            }
        }
        return false;
    }

    // Bubble Sort Methods

    public static ArrayList<Card> sortByValueAceLow(ArrayList<Card> hand) {
        for (int i = 0; i < hand.size(); i++) {
            for (int j = 0; j < hand.size() - 1; j++) {
                if (hand.get(j).value > hand.get(j + 1).value) {
                    Card temp = hand.get(j + 1);
                    hand.set(j + 1, hand.get(j));
                    hand.set(j, temp);
                }
            }
        }
        return hand;
    }

    public static ArrayList<Card> sortByValueAceHigh(ArrayList<Card> hand) {
        for (int a = 0; a < hand.size(); a++) {
            if (hand.get(a).value == 1) {
                hand.get(a).value = 14;
            }
        }
        for (int i = 0; i < hand.size(); i++) {
            for (int j = 0; j < hand.size() - 1; j++) {
                if (hand.get(j).value > hand.get(j + 1).value) {
                    Card temp = hand.get(j + 1);
                    hand.set(j + 1, hand.get(j));
                    hand.set(j, temp);
                }
            }
        }
        for (int a = 0; a < hand.size(); a++) {
            if (hand.get(a).value == 14) {
                hand.get(a).value = 1;
            }
        }
        return hand;
    }
}