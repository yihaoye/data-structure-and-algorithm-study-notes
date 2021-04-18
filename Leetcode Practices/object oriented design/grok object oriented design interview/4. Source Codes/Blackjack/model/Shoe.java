public class Shoe {
    private List<BlackjackCard> cards;
    private int numberOfDecks;
  
    private void createShoe() {
      this.cards = new ArrayList<BlackjackCard>();
      for (int decks = 0; decks < numberOfDecks; decks++) {
        cards.add(new Deck().getCards());
      }
    }
  
    public Shoe(int numberOfDecks) {
      this.numberOfDecks = numberOfDecks;
      createShoe();
      shuffle();
    }
  
    public void shuffle() {
      int cardCount = cards.size();
      Random r = new Random();
      for (int i = 0; i < cardCount; i++) {
        int index = r.nextInt(cardCount-i-1);
        swap(i, index);
      }
    }
  
    public void swap(int i, int j) {
      BlackjackCard temp = cards[i];
      cards[i] = cards[j];
      cards[j] = temp;
    }
  
    // Get the next card from the shoe
    public BlackjackCard dealCard() {
      if (cards.size() == 0) {
        createShoe();
      }
      return cards.remove(0);
    }
}
