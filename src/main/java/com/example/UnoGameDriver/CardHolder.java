package com.example.UnoGameDriver;

interface CardHolder {
    Card drawWildCard(Color color, Value value);
    void initializeDeck();
    void shuffleDeck();
    Card drawCard();
}
