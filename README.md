# Decks

LogMeIn assignment

To run:

    mvn spring-boot:run

The rest API will be available at:

    http://localhost:8080

To run unit tests:

    mvn test
    
To run integration tests:

    mvn verify   

## API

Create and delete a game

    POST /games    
    GET /games
    DELETE /games/X

Create a deck

    POST /decks    

Add a deck to a game deck

    PUT /games/X/decks/Y

Add and remove players from a game

    POST /games/X/players
    DELETE /games/X/players/Y

Deal cards to a player in a game from the game deck

    POST /games/X/players/Y/cards

Get the list of cards for a player

    GET /games/X/players/Y/cards

Get the list of players in a game along with the total added value of all the cards each player holds

    GET /games/X/players

Get the count of how many cards per suit are left undealt in the game deck

    GET /games/X/suits

Get the count of each card remaining in the game deck sorted by suit and face value

    GET /games/X/cards

Shuffle the game deck

    PUT /games/X/shuffle