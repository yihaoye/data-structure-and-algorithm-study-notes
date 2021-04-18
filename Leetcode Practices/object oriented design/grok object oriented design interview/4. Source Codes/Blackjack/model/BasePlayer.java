public abstract class BasePlayer {
    private String id;
    private String passwords;
    private double balance;
    private AccountStatus status;
    private Person person;
    private List<Hand> hands;
  
    public boolean resetPassword();
  
    public List<Hand> getHands() {
      return hands;
    }
  
    public void addHand(Hand hand) {
      return hands.add(hand);
    }
  
    public void removeHand(Hand hand) {
      return hands.remove(hand);
    }
}

public class Player extends BasePlayer {
    private int bet;
    private int totalCash;
  
    public Player(Hand hand) {
      this.hands = new ArrayList<Hand>();
      this.hands.add(hand);
    }
}
