public class Hand {
    private ArrayList<BlackjackCard> cards;
  
    private List<Integer> getScores() {
      List<Integer> totals = new ArrayList();
      total.add(0);
  
      for (BlackjackCard card : cards) {
        List<Integer> newTotals = new ArrayList();
        for (int score : totals) {
          newTotals.add(card.faceValue() + score);
          if (card.faceValue() == 1) {
            newTotals.add(11 + score);
          }
        }
        totals = newTotals;
      }
      return totals;
    }
  
    public Hand(BlackjackCard c1, BlackjackCard c2) {
      this.cards = new ArrayList<BlackjackCard>();
      this.cards.add(c1);
      this.cards.add(c2);
    }
  
    public void addCard(BlackjackCard card) {
      cards.add(card);
    }
  
    // get highest score which is less than or equal to 21
    public int resolveScore() {
      List<Integer> scores = getScores();
      int bestScore = 0;
      for (int score : scores) {
        if (score <= 21 && score > bestScore) {
          bestScore = score;
        }
      }
      return bestScore;
    }
}
