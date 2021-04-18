public class Deck {
    private List<BlackjackCard> cards;
    private Date creationDate;
  
    public Deck() {
      this.creationDate = new Date();
      this.cards = new ArrayList<BlackjackCard>();
      for (int value = 1; value <= 13; value++) {
        for (SUIT suit : SUIT.values()) {
          this.cards.add(new BlackjackCard(suit, value));
        }
      }
    }
  
    public List<BlackjackCard> getCards() {
      return cards;
    }
}
