public class Game {
    private Player player;
    private Dealer dealer;
    private Shoe shoe;
    private final int MAX_NUM_OF_DECKS = 3;
  
    private void playAction(string action, Hand hand) {
      switch (action) {
        case "hit": hit(hand); break;
        case "split": split(hand); break;
        case "stand pat": break; //do nothing
        case "stand": stand(); break;
        default: print("Wrong input");
      }
    }
  
    private void hit(Hand hand) {
      hand.addCard(shoe.dealCard());
    }
  
    private void stand() {
      int dealerScore = dealer.getTotalScore();
      List<Hand> hands = player.getHands();
      for (Hand hand : hands) {
        int bestScore = hand.resolveScore();
        if (playerScore == 21) {
          // blackjack, pay 3:2 of the bet
        } else if (playerScore > dealerScore) {
          // pay player equal to the bet
        } else if (playerScore < dealerScore) {
          // collect the bet from the player
        } else { // tie
          // bet goes back to player
        }
      }
    }
  
    private void split(Hand hand) {
      Cards cards = hand.getCards();
      player.addHand(new Hand(cards[0], shoe.dealCard()));
      player.addHand(new Hand(cards[1], shoe.dealCard()));
      player.removeHand(hand);
    }
  
    public Game(Player player, Dealer dealer) {
      this.player = player;
      this.dealer = dealeer;
      Shoe shoe= new Shoe(MAX_NUM_OF_DECKS);
    }
  
    public void start() {
      player.placeBet(getBetFromUI());
  
      Hand playerHand = new Hand(shoe.dealCard(), shoe.dealCard());
      player.addToHand(playerHand);
  
      Hand dealerHand = new Hand(shoe.dealCard(), shoe.dealCard());
      dealer.addToHand(dealerHand);
  
      while (true) {
        List<Hand> hands = player.getHands();
        for (Hand hand : hands) {
          string action = getUserAction(hand);
          playAction(action, hand);
          if (action.equals("stand")) {
            break;
          }
        }
      }
    }
  
    public static void main(String args[]) {
      Player player = new Player();
      Dealer dealer = new Dealer();
      Game game = new Game(player, dealer);
      game.start();
    }
}
