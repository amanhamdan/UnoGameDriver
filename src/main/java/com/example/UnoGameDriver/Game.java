package com.example.UnoGameDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class Game {
    public List<UnoPlayer> players;
    public UnoDeck deck;
    public Card topCard;
    public int currentPlayerIndex;

    public Game(int numPlayers) {
        if (numPlayers < 2 || numPlayers > 10) {
            throw new IllegalArgumentException("Number of players must be between 2 and 10.");
        }

        deck = new UnoDeck();
        players = new ArrayList<>();
        initializePlayers(numPlayers);
        dealInitialCards();

        do {
            topCard = deck.drawCard();
        } while (topCard.getValue() == Value.WILD || topCard.getValue() == Value.DRAW_FOUR);

        currentPlayerIndex = 0;
    }


    private void initializePlayers(int numPlayers) {
        for (int i = 1; i <= numPlayers; i++) {
            players.add(new UnoPlayer("Player " + i));
        }
    }

    public abstract void play();

    public void dealInitialCards() {
        for (UnoPlayer player : players) {
            for (int i = 0; i < 7; i++) {
                player.addCardToHand(deck.drawCard());
            }
        }
    }

    public void displayGameStatus() {
        System.out.println("Top Card: " + topCard);

        UnoPlayer currentPlayer = players.get(currentPlayerIndex);
        System.out.print(currentPlayer.getName() + "'s Hand: ");
        displayPlayerHandWithIndices(currentPlayer.getHand());

        System.out.println("Enter the index of the card you want to play (or -1 to draw a card):");
    }

    public void displayPlayerHandWithIndices(List<Card> hand) {
        for (int i = 0; i < hand.size(); i++) {
            System.out.print("[" + i + "] " + hand.get(i) + " ");
        }
        System.out.println();
    }

    public boolean isGameOver() {
        for (UnoPlayer player : players) {
            if (player.getHand().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public void switchPlayer() {
        //implement the logic to switch players
    }

    public UnoPlayer determineWinner() {
        for (UnoPlayer player : players) {
            if (player.getHand().isEmpty()) {
                return player;
            }
        }
        return null;
    }

    public boolean hasPlayableCard(UnoPlayer player) {
        for (Card card : player.getHand()) {
            if (isValidMove(card)) {
                return true;
            }
        }
        return false;
    }

    public abstract void playTurn(UnoPlayer player, Scanner scanner);

    public abstract void handleSpecialCard(UnoPlayer player, Card card);

    public abstract Color chooseColor();

    public abstract boolean isValidMove(Card card);
}
