package com.qa.service;

public interface DeckService {
	String getAllCards();

	String createCard(String card);

	String deleteCard(int cardId);

	String updateCard(int cardId, String card);

	String getCard(Integer id);

	String getAllCard();

	String getCardsForUser(Integer accountId);

}
