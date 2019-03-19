package com.lmi.decks.domain.repository;

import com.lmi.decks.domain.model.Deck;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeckRepository extends CrudRepository<Deck, Long> {
}
