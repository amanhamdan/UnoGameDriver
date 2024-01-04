package com.example.UnoGameDriver;

import java.util.ArrayList;
import java.util.List;

class UnoPlayer {
    private String name;
    private List<Card> hand;

    public UnoPlayer(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }

    public void addCardsToHand(List<Card> cards) {
        hand.addAll(cards);
    }
}
