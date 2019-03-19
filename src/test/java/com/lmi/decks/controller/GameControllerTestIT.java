package com.lmi.decks.controller;

import com.lmi.decks.DecksApplication;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = DecksApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class GameControllerTestIT {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void test01_create() throws Exception {
        createAndExpectId(1);
        createAndExpectId(2);
        createAndExpectId(3);
    }

    @Test
    public void test02_delete() throws Exception {
        mvc.perform(delete("/games/3"))
                .andExpect(status().isNoContent());
        mvc.perform(delete("/games/3"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test03_findAll() throws Exception {
        mvc.perform(get("/games").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void test04_pinDeck() throws Exception {
        DeckControllerTestIT.createAndExpectId(mvc, 4);
        DeckControllerTestIT.createAndExpectId(mvc, 57);

        mvc.perform(put("/games/3/decks/4"))
                .andExpect(status().isNotFound());
        mvc.perform(put("/games/1/decks/58"))
                .andExpect(status().isNotFound());

        pinDeckAndExpectSize(4, 1);
        pinDeckAndExpectSize(57, 2);
    }

    @Test
    public void test05_addPlayer() throws Exception {
        mvc.perform(post("/games/3/players"))
                .andExpect(status().isNotFound());

        addPlayerAndExpectSize(1);
        addPlayerAndExpectSize(2);
        addPlayerAndExpectSize(3);
    }

    @Test
    public void test06_removePlayer() throws Exception {
        mvc.perform(delete("/games/3/players/112"))
                .andExpect(status().isNotFound());

        mvc.perform(delete("/games/1/players/112").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.players", hasSize(2)));
    }

    @Test
    public void test07_dealCard() throws Exception {
        mvc.perform(post("/games/3/players/110/cards"))
                .andExpect(status().isNotFound());
        mvc.perform(post("/games/1/players/112/cards"))
                .andExpect(status().isNotFound());

        mvc.perform(post("/games/1/players/110/cards").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void test08_getPlayerCards() throws Exception {
        mvc.perform(get("/games/3/players/110/cards"))
                .andExpect(status().isNotFound());
        mvc.perform(get("/games/1/players/112/cards"))
                .andExpect(status().isNotFound());

        mvc.perform(get("/games/1/players/110/cards").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void test09_getPlayers() throws Exception {
        mvc.perform(get("/games/3/players"))
                .andExpect(status().isNotFound());

        mvc.perform(get("/games/1/players").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", startsWith("Player")));
    }

    @Test
    public void test10_getSuits() throws Exception {
        mvc.perform(get("/games/3/suits"))
                .andExpect(status().isNotFound());

        mvc.perform(get("/games/1/suits").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void test11_getCards() throws Exception {
        mvc.perform(get("/games/3/cards"))
                .andExpect(status().isNotFound());

        mvc.perform(get("/games/1/cards").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(51)));
    }

    @Test
    public void test12_shuffle() throws Exception {
        mvc.perform(put("/games/3/shuffle"))
                .andExpect(status().isNotFound());

        mvc.perform(put("/games/1/shuffle").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.decks", hasSize(2)))
                .andExpect(jsonPath("$.players", hasSize(4)));
    }

    private void createAndExpectId(final Integer id) throws Exception {
        mvc.perform(post("/games").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(id)));
    }

    private void pinDeckAndExpectSize(final Integer deckId, final Integer size) throws Exception {
        mvc.perform(put("/games/1/decks/" + deckId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.decks", hasSize(size)));
    }

    private void addPlayerAndExpectSize(final Integer size) throws Exception {
        mvc.perform(post("/games/1/players").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.players", hasSize(size)));
    }

}