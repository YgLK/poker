                                POKER GAME: Five-card draw
    How to run Poker Game
1. Build the project with Maven.
2. Find target directory in the poker-server and poker-client module.
3. Run the Server from your terminal after moving to the target directory in the poker-server module by entering this command:
    java -jar .\poker-server-1.0-jar-with-dependencies.jar
4. Run the Client from another terminal after moving to the target directory in the poker-client module by entering this command:
    java -jar .\poker-client-1.0-jar-with-dependencies.jar
5. You should run multiple Clients each from another terminal. When all players have set nicknames you can start the game (don't type 'get cards' command before all players has joined).
Type 'queue' to check when is your turn.


           COMMUNICATION - CLIENT-SERVER
Server-client communication is implemented with the use of Java IO.
At the beginning the server waits, listening to the socket for a client
to make a connection request. When the server code encounters the accept method,
it blocks until a client makes a connection request to it. If everything goes well, the server accepts the connection.
Client can communicate with the server by typing commands used for the Poker gameplay (list below).
Server recognizes typed in commands and process it, result is sent back to the Client.


Five-card draw (Poker) - INSTRUCTIONS

GAMEPLAN:
    1. Every player pays Ante and take cards from the table. [Start]
    2. First round of betting. [Bet 1]
    3. Players exchange cards. [Exchange]
    4. Second round of betting. [Bet 2]
    5. Determine a winner.  [Finish]

USEFUL COMMANDS:
    'get cards' - take card from the table [Phase 1]
    'exchange cards _ _ _' - exchange cards from your hand (cards are numbered 0 to 4) e.g. 'exchange cards 1 3' [Phase 3]
    'stay' - inform that you don't want to exchange any cards in the third phase [Phase 3]
    'queue' - show players queue
    'balance' - show balance
    'bet _value_' - bid the amount of _value_ [Phase 2/4]
    'bid status' - info about betting phase [Phase 2/4]
    'winner' - show winner [Phase 5]
    'won cards' - show winner's cards [Phase 5]
    'phase' - get current phase of the game
    'restart' - vote for new game [Phase 5]
                Good luck!
