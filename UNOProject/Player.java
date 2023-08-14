import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> hand;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void drawCard(Card card) {
        hand.add(card);
    }

    public Card playCard(int index) {
        if (index >= 0 && index < hand.size()) {
            return hand.remove(index);
        }
        return null;
    }

    public boolean hasPlayableCard(Card topCard) {
        for (Card card : hand) {
            if (card.getColor().equals(topCard.getColor()) || card.getValue().equals(topCard.getValue())) {
                return true;
            }
        }
        return false;
    }
}
