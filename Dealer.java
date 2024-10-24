public class Dealer extends Person {
    public Card hiddenCard;

    public void dealHand(Deck deck) {
        hiddenCard = deck.getNextCard(); 
        hand.add(hiddenCard);
        Card visibleCard = deck.getNextCard(); 
        hand.add(visibleCard);
        calculateHandValue();
    }

    public void draw(Deck deck) {
        if (!currentTurn) {
            return;
        }

        while (handValue < 17) {
            Card next = deck.getNextCard();
            hand.add(next);
            calculateHandValue();
            System.out.println("Dealer draws: " + next.value + "-" + next.type);
        }
    }

    public void revealHiddenCard() {
        System.out.println("Dealer reveals hidden card: " + hiddenCard.value + "-" + hiddenCard.type);
    }
}
