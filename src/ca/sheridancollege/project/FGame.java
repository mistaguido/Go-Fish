/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.sheridancollege.project;

/**
 *
 * @author REEEE
 */
import java.util.*;

public class FGame extends Game {
    static ArrayList<FPlayer> playerList = new ArrayList();
    static Scanner sc = new Scanner(System.in);

    public FGame(String name) {
        super(name);
    }

    @Override
    public void play() {

        ArrayList<FCard> reserveList = new ArrayList();
        for (Suit suit : Suit.values()) {
            for (Value value : Value.values()) {
                FCard card = new FCard(suit, value);
                reserveList.add(card);
                System.out.println(card.toString());
            }
        }
        ReserveDeck reserveDeck = new ReserveDeck(reserveList);

        //players
        System.out.println("Enter number of players (more than 2, less than 52): ");
        int noOfPlayers = askForNoPlayer();
        //players
        System.out.println("Enter player names");
        for (int i = 0; i < noOfPlayers; i++) {
            String playerName = askForName();
            playerList.add(new FPlayer(playerName, new Deck(new ArrayList())));
        }

        reserveDeck.distributeToPlayers(playerList, noOfPlayers);

        //print out player cards
        for (int i = 0; i < noOfPlayers; i++) {
            Deck testDeck = playerList.get(i).getPlayerDeck();
            int deckLength = testDeck.getSize();
            System.out.println(deckLength);
            System.out.println(testDeck.toString());
        }

        System.out.println("Game will begin now");

        Books book = new Books();
        int books = 0;

        do {
            for (FPlayer ply : playerList) {
                System.out.println(ply.getPlayerID() + "'s turn");
                int plyIndex = askForPlayerIndex(playerList);
                FCard guessCard = new FCard(askSuit(), askRank());
                Deck plyDeck = playerList.get(plyIndex).getPlayerDeck();
                for(FCard card : plyDeck.getCards()){
                    //match
                    if (guessCard == card){
                        book.getBooks(ply.getPlayerDeck());
                        System.out.println("Match");
                    }
                }
            }
            books = book.getNoBooks();
        } while (books <= 13);
        
        declareWinner();
    }

    public Value askRank() {
        try {
            System.out.println("What ranks?");
            Value ranks[] = new Value[13];
            int choice = sc.nextInt();
            int i = 0;
            for(Value value : Value.values()){
                ranks[i]=value;
                i++;
            }
            
            for(int j = 0; j < ranks.length; j++){
                System.out.println("["+j+"]" + ranks[i].toString());
            }
            
            if (choice == (int) choice){
                if (choice >= 0 && choice < ranks.length){
                    return ranks[choice];
                }
            }
            return askRank();
        } catch (NumberFormatException e) {
            return askRank();
        }
    }

    public Suit askSuit() {
        try {
            System.out.println("Which suit?\n[0] Clubs\n[1] Diamonds\n[2] Hearts\n[3] Spades");
            int choice = sc.nextInt();
            switch (choice) {
                case 0:
                    return Suit.CLUBS;
                case 1:
                    return Suit.DIAMONDS;
                case 2:
                    return Suit.HEARTS;
                case 3:
                    return Suit.SPADES;
                default:
                    return askSuit();
            }
        } catch (NumberFormatException e) {
            return askSuit();
        }
    }

    public int askForPlayerIndex(ArrayList<FPlayer> playerList) {
        try {
            System.out.println("Which player do you choose?");
            for (int i = 0; i < playerList.size(); i++) {
                System.out.println("[" + i + "]" + playerList.get(i).getPlayerID());
            }
            int choice = Integer.parseInt(sc.nextLine());
            if (choice == (int) choice) {
                if (choice > 0 && choice < playerList.size()) {
                    return choice;
                }
            }
            return askForPlayerIndex(playerList);
        } catch (NumberFormatException e) {
            return askForPlayerIndex(playerList);
        }
    }

    public int askForNoPlayer() {
        try {
            int x = Integer.parseInt(sc.nextLine());
            if (x == (int) x) {
                if (x > 1) {
                    if (x > 52) {
                        System.out.println("Too many players");
                        return askForNoPlayer();
                    } else {
                        return x;
                    }
                }
            }
            return askForNoPlayer();
        } catch (NumberFormatException e) {
            return askForNoPlayer();
        }
    }

    public String askForName() {
        try {
            String nam = sc.nextLine();
            return nam;
        } catch (Exception e) {
            return askForName();
        }
    }

    @Override
    public void declareWinner() {
        int highestScore=0;
        String plyID="";
        for(FPlayer ply : playerList){
            if (ply.getScore() > highestScore){
                highestScore = ply.getScore();
                plyID=ply.getPlayerID();
            }
        }
        System.out.println("Player " + plyID + " wins with " + highestScore + " books");
        System.exit(0);
    }
}