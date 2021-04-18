public class Card {
    private SUIT suit;
    private int faceValue;
  
    public SUIT getSuit() {
      return suit;
    }
  
    public int getFaceValue() {
      return faceValue;
    }
  
    Card(SUIT suit, int faceValue) {
      this.suit = suit;
      this.faceVale = faceValue;
    }
}
