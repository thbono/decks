package com.lmi.decks.controller;

import com.lmi.decks.DecksApplication;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
    public void create() throws Exception {
        createAndExpectId(1);
        createAndExpectId(2);
        createAndExpectId(3);
    }

    @Test
    public void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/games/3"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        mvc.perform(MockMvcRequestBuilders.delete("/games/3"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void findAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/games").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    public void pinDeck() throws Exception {
        DeckControllerTestIT.createAndExpectId(mvc, 4);
        DeckControllerTestIT.createAndExpectId(mvc, 57);

        mvc.perform(MockMvcRequestBuilders.put("/games/3/decks/4"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        mvc.perform(MockMvcRequestBuilders.put("/games/1/decks/58"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        pindDeckAndExpectSize(4, 1);
        pindDeckAndExpectSize(57, 2);
    }

    private void createAndExpectId(final Integer id) throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/games").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(id)));
    }

    private void pindDeckAndExpectSize(final Integer deckId, final Integer size) throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/games/1/decks/" + deckId).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.decks", Matchers.hasSize(size)));
    }

}