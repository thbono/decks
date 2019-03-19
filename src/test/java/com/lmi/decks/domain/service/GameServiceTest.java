package com.lmi.decks.domain.service;

import com.lmi.decks.domain.model.Deck;
import com.lmi.decks.domain.model.Game;
import com.lmi.decks.domain.repository.DeckRepository;
import com.lmi.decks.domain.repository.GameRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

public class GameServiceTest {

    @Mock
    private GameRepository repository;

    @Mock
    private DeckRepository deckRepository;

    private GameService service = new GameService();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(service, "repository", repository);
        ReflectionTestUtils.setField(service, "deckRepository", deckRepository);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(new Game()));
        Mockito.when(repository.findById(2L)).thenReturn(Optional.empty());
        Mockito.when(repository.save(Mockito.any(Game.class))).then(AdditionalAnswers.returnsFirstArg());

        Mockito.when(deckRepository.findById(1L)).thenReturn(Optional.of(new Deck()));
        Mockito.when(deckRepository.findById(2L)).thenReturn(Optional.empty());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void shouldNotPinDeckWhereThereIsNoGame() {
        service.pinDeck(2L, 1L);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void shouldNotPinDeckWhereThereIsNoDeck() {
        service.pinDeck(1L, 2L);
    }

    @Test
    public void pinDeck() {
        Assert.assertEquals(1, service.pinDeck(1L, 1L).getDecks().size());
    }

}