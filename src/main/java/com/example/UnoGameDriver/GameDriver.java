package com.example.UnoGameDriver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.List;


public class GameDriver {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the number of players (2-10): ");
		int numPlayers = scanner.nextInt();

		UnoGame unoGame = new UnoGame(numPlayers);
		unoGame.play();
	}

}