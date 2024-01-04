package com.example.UnoGameDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class UnoGame extends Game {

    private Card topCard;
    private int currentPlayerIndex;
    private boolean reverseOrder = false;

    public UnoGame(int numPlayers) {
        super(numPlayers);

        do {
            topCard = deck.drawCard();
        } while (topCard.getValue() == Value.WILD || topCard.getValue() == Value.DRAW_FOUR);
    }


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

    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (!isGameOver()) {
            UnoPlayer currentPlayer = players.get(currentPlayerIndex);

            //to skip the winners
            if (currentPlayer.getHand().isEmpty()) {
                switchPlayer();
                continue;
            }

            displayGameStatus();
            playTurn(currentPlayer, scanner);

            //remove if player won
            if (currentPlayer.getHand().isEmpty()) {
                System.out.println(currentPlayer.getName() + " wins!");
                players.remove(currentPlayer);
            }

            switchPlayer();
        }

        System.out.println("Game over!");
        if (!players.isEmpty()) {
            UnoPlayer winner = determineWinner();
            System.out.println(winner.getName() + " wins!");
        }

        scanner.close();
    }

    public void playTurn(UnoPlayer player, Scanner scanner) {
        if (!hasPlayableCard(player)) {
            System.out.println(player.getName() + " has no playable cards. Drawing a card.");
            player.addCardToHand(deck.drawCard());
            return;
        }

        System.out.println(player.getName() + "'s turn. Enter the index of the card you want to play (or -1 to draw a card):");
        int index = scanner.nextInt();


        if (index == -1) {
            player.addCardToHand(deck.drawCard());
            System.out.println(player.getName() + " draws a card.");
        } else {
            Card selectedCard = player.getHand().get(index);
            if (isValidMove(selectedCard)) {
                player.getHand().remove(index);
                handleSpecialCard(player, selectedCard);
                topCard = selectedCard;
            } else {
                System.out.println("Invalid move. Try again.");
                playTurn(player, scanner);
            }

        }
    }

    public void handleSpecialCard(UnoPlayer player, Card card) {
        switch (card.getValue()) {
            case WILD:
            case DRAW_FOUR:
                handleWildCard(player, card);
                break;
            case DRAW_TWO:
                handleDrawTwoCard(player, card);
                break;
            case SKIP:
                handleSkipCard(player, card);
                break;
            case REVERSE:
                handleReverseCard(player, card);
                break;
            default:

        }
    }

    private void handleWildCard(UnoPlayer player, Card card) {
        if (card.getValue() == Value.WILD) {
            Color chosenColor = chooseColor();
            card.setColor(chosenColor);
            topCard = card;
            System.out.println(player.getName() + " chooses the color: " + chosenColor);
        } else if (card.getValue() == Value.DRAW_FOUR) {
            handleDrawFourCard(player, card);
        }
    }

    private void handleDrawFourCard(UnoPlayer player, Card card) {
        Color chosenColor = chooseColor();
        card.setColor(chosenColor);
        topCard = card;
        UnoPlayer nextPlayer = getNextPlayer();
        List<Card> drawFourCards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            drawFourCards.add(deck.drawCard());
        }
        nextPlayer.addCardsToHand(drawFourCards);
        System.out.println(nextPlayer.getName() + " draws four cards.");
    }


    private void handleDrawTwoCard(UnoPlayer player, Card card) {
        UnoPlayer nextPlayer = getNextPlayer();
        nextPlayer.addCardToHand(deck.drawCard());
        nextPlayer.addCardToHand(deck.drawCard());
        System.out.println(nextPlayer.getName() + " draws two cards.");
    }

    private void handleSkipCard(UnoPlayer player, Card card) {
        UnoPlayer nextPlayer = getNextPlayer();
        System.out.println(player.getName() + " skips " + nextPlayer.getName() + "'s turn.");

        switchPlayer();
    }

    private void handleReverseCard(UnoPlayer player, Card card) {
        reverseOrder = !reverseOrder;
        System.out.println(player.getName() + " reverses the order of play.");

    }


    public Color chooseColor() {
        System.out.println("Choose the new color:");
        System.out.println("1. RED\n2. YELLOW\n3. GREEN\n4. BLUE");
        int choice = new Scanner(System.in).nextInt();
        switch (choice) {
            case 1:
                return Color.RED;
            case 2:
                return Color.YELLOW;
            case 3:
                return Color.GREEN;
            case 4:
                return Color.BLUE;
            default:
                return Color.RED;
        }
    }


    public boolean hasPlayableCard(UnoPlayer player) {
        for (Card card : player.getHand()) {
            if (isValidMove(card)) {
                return true;
            }
        }
        return false;
    }

    private UnoPlayer getNextPlayer() {
        int nextIndex;
        if (reverseOrder) {
            nextIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
        } else {
            nextIndex = (currentPlayerIndex + 1) % players.size();
        }
        return players.get(nextIndex);
    }

    public void switchPlayer() {
        if (reverseOrder) {
            currentPlayerIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
        } else {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }
    }



    public boolean isValidMove(Card card) {
        return card.getColor() == topCard.getColor() || card.getValue() == topCard.getValue() ||
                card.getColor() == null;
    }

    public UnoPlayer determineWinner() {
        for (UnoPlayer player : players) {
            if (player.getHand().isEmpty()) {
                return player;
            }
        }
        return null;
    }

}
