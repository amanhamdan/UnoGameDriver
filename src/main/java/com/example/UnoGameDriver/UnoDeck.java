package com.example.UnoGameDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class UnoDeck implements CardHolder {
    public List<Card> cards;

    public UnoDeck() {
        cards = new ArrayList<>();
        initializeDeck();
        shuffleDeck();
    }


    public Card drawWildCard(Color color, Value value) {
        return new Card(color, value);
    }

    public void initializeDeck() {
        for (Color color : Color.values()) {
            if (color != null) { // Exclude null color for WILD and DRAW_FOUR
                for (Value value : Value.values()) {
                    if (value != Value.ZERO) {
                        if (value == Value.WILD || value == Value.DRAW_FOUR) {
                            cards.add(new Card(null, value));
                        } else {
                            cards.add(new Card(color, value));
                            cards.add(new Card(color, value));
                        }
                    }
                }
            }
        }
    }

    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        return cards.remove(0);
    }

}
