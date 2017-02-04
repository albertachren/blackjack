package Blackjack;

import java.util.*;
import java.util.concurrent.TimeUnit;

//BLACKJACK


class Cards { //Card deck class
    private static final Scanner scan = new Scanner(System.in);

    private static final Integer[] deck = {2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 8, 9, 9, 9, 9, 10, 10, 10, 10, 11, 11, 11, 11};

    private final List<Integer> cards = new LinkedList<>(Arrays.asList(deck));

    //Constructor
    public Cards() {
        Collections.shuffle(cards);
    }

    void deal(List<Integer> hand, int amount, boolean auto) {
        int count = 1;
        do {
            int temp = cards.remove(0);
            if (temp == 11) {
                if (auto && (hand.stream().mapToInt(Integer::intValue).sum() + 11) > 21) {
                    temp = 1;
                } else if (auto) {
                    break;
                } else {
                    System.out.println("You got an Ace, 1 or 11?");
                }
                boolean tempW = true;
                while (tempW) {
                    switch (scan.nextInt()) {
                        case 1:
                            temp = 1;
                            tempW = false;
                        case 11:
                            tempW = false;
                        default:
                            break;
                    }
                }
            }
            hand.add(temp);
            count++;
        } while (count <= amount);
    }
}

class Hand { //Player hand class

    final List<Integer> hand = new ArrayList<>();

    public Hand() {

    }

    public String getHand() { // returns string of hand contents, formatted for output
        String cont = "";
        for (int i = 0; i < hand.size(); i++) {
            int temp;
            String templ = "|";

            if (i == hand.size() - 1) {
                templ = "";
            }

            temp = hand.get(i);
            cont += temp + templ;
        }
        return cont;
    }

    public Boolean compare(Hand target) { // compares hand with the specified target hand
        Boolean win = false;
        Integer handDiff = 21 - hand.stream().mapToInt(Integer::intValue).sum();
        Integer targetDiff = 21 - target.hand.stream().mapToInt(Integer::intValue).sum();

        if (targetDiff < 0) {
            win = true;
        } else if (handDiff < 0) {
            win = false;
        } else if (handDiff < targetDiff) {
            win = true;
        }
        return win;
    }
}

class Blackjack {

    private static final Scanner scan = new Scanner(System.in);

    private static Boolean win = false;

    public static void main(String[] args) {

        boolean restart;

        do { // do-while loop to make restarting possible
            restart = false;
            Hand player = new Hand();
            Hand dealer = new Hand();

            Cards pack = new Cards();

            pack.deal(player.hand, 2, false);
            pack.deal(dealer.hand, 1, true);

            Boolean playing = true;

            do {
                System.out.println("Your hand:" + " " + player.getHand());//getHand
                System.out.println("Dealers hand:" + " " + dealer.getHand());

                System.out.println("Hit/Stand?");
                String choice = scan.next();

                switch (choice) {
                    case "Hit":
                        pack.deal(player.hand, 1, false);
                        if (player.hand.stream().mapToInt(Integer::intValue).sum() > 21) { // checks to see if the player has gone over 21
                            win = false;
                            playing = false;
                        }
                        break;
                    case "Stand":
                        playing = false;
                        pack.deal(dealer.hand, 1, false);
                        System.out.println("Dealers hand:" + " " + dealer.getHand() + " " + "=" + " " + dealer.hand.stream().mapToInt(Integer::intValue).sum()); // prints the dealers hand and sum

                        while (dealer.hand.stream().mapToInt(Integer::intValue).sum() < 17) { // deals the dealer a card as long as his sum is under 17
                            pack.deal(dealer.hand, 1, false);
                            System.out.println("Dealers hand:" + " " + dealer.getHand() + " " + "=" + " " + dealer.hand.stream().mapToInt(Integer::intValue).sum()); // prints the dealers hand and sum
                            try {
                                TimeUnit.SECONDS.sleep(1); // delay to make it easier to understand what is happening
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        win = player.compare(dealer); // final comparison if no one has gone over 21

                        break;
                    default:
                        System.out.println("Invalid answer");
                        break;
                }
            } while (playing);

            if (win) {
                System.out.println("You win!");
            } else {
                System.out.println("You lose!");
            }
            System.out.println("Your hand:" + " " + player.getHand());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Try again? Y/N");
            String reset = scan.next();
            if (reset.equals("Y")) {
                restart = true;
            }
        } while (restart);
    }

}


