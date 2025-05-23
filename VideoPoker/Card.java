public class Card {
    // Instance Variables
    public int suit; //1 = Heart, 2 = Diamond, 3 = Club, 4 = Spade
    public int value; //1 = Ace, 2-10 Numerical, 11 = Jack, 12 = Queen, 13 = King
    // Constructors

    // Two-Argument Constructor
    public Card(int suit, int value) {
        if (suit >= 1 && suit <= 4) {
            this.suit = suit;
        }
        if (value >= 1 && value <= 13) {
            this.value = value;
        }
    }

    // Default Constructor
    public Card() {
        suit = 4;
        value = 1;
    }

    // Method
    public String cardName() {
        String name = "";
        if (value == 1) {
            name += "Ace";
        }
        if (value >= 2 && value <= 10) {
            name += Integer.toString(value);
        }
        if (value == 11) {
            name += "Jack";
        }
        if (value == 12) {
            name += "Queen";
        }
        if (value == 13) {
            name += "King";
        }

        name += " of ";

        switch (suit) {
            case 1:
                name += "Hearts";
                break;
            case 2:
                name += "Diamonds";
                break;
            case 3:
                name += "Clubs";
                break;
            case 4:
                name += "Spades";
                break;
        }
        return name;
    }
}