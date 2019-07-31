package com.qa.persistence.repository;

public interface DeckRepositry {
	final String SUCCESS = "Card_Operation passed";
	final String FAILURE = "Card_Operation failed";

	String getAllCards();

	String createCard(String card);

	String deleteCard(int cardNumber);

	String updateCard(int cardNumber, String card);

	String getCardsForUser(Integer id);

	String getCard(Integer id);
}
