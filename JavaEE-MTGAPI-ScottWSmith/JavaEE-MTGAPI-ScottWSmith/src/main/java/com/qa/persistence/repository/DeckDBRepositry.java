package com.qa.persistence.repository;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.qa.persistence.domain.Deck;
import com.qa.util.JSONUtil;

@Default
@Transactional(value = TxType.SUPPORTS)
public class DeckDBRepositry implements DeckRepositry {

	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	@Inject
	private JSONUtil json;

	public String getAllCards() {
		TypedQuery<Deck> query = em.createQuery("SELECT a FROM Deck a", Deck.class);
		return json.getJSONForObject(query.getResultList());
	}

	public String getCardsForUser(Integer id) {
		TypedQuery<Deck> query = em.createQuery("SELECT a FROM Deck a where a.account_ID = '" + id + "'", Deck.class);
		return json.getJSONForObject(query.getResultList());
	}

	@Transactional
	public String createCard(String card) {
		Deck toCreate = this.json.getObjectForJSON(card, Deck.class);
		this.em.persist(toCreate);
		return SUCCESS + "Adding the following informatiion: " + toCreate.getName();
	}

	@Transactional(value = TxType.REQUIRED)
	public String deleteCard(int cardNumber) {
		Deck cardTemp = em.find(Deck.class, cardNumber);
		em.remove(cardTemp);
		return "Removed Card: " + cardTemp.getName();
	}

	@Transactional(value = TxType.REQUIRED)
	public String updateCard(int cardNumber, String card) {
		Deck current = this.em.find(Deck.class, cardNumber);
		Deck toChange = this.json.getObjectForJSON(card, Deck.class);
		current.setName(toChange.getName());
		current.setUrl(toChange.getUrl());
		current.setAccount_ID(toChange.getAccount_ID());
		this.em.persist(toChange);
		return SUCCESS;
	}

	public String getCard(Integer id) {
		// TODO Auto-generated method stub
		return "Need To Implement";
	}

}
