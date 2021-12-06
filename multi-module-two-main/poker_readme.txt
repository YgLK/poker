    How to run Poker Game
1. Build the project with Maven.
2. Find target directory in the poker-server and poker-client module.
2. Run the Server from your terminal after moving to the target directory in the poker-server module by entering this command:
    java -jar .\poker-server-1.0-jar-with-dependencies.jar
2. Run the Client from another terminal after moving to the target directory in the poker-client module by entering this command:
    java -jar .\poker-client-1.0-jar-with-dependencies.jar
You can run multiple Clients (from multiple terminals).


Five-card draw (Poker) - INSTRUCTIONS

GAMEPLAN:
    1. Every player pays Ante and take cards from the table. [Start]
    2. First round of betting. [Bet 1]
    3. Players exchange cards. [Exchange]
    4. Second round of betting. [Bet 2]
    5. Determine a winner.  [Finish]

USEFUL COMMANDS:
    'get cards' - take card from the table
    'exchange cards _ _ _' - exchange cards from your hand (cards are numbered 0 to 4) e.g. 'exchange cards 1 3'
    'stay' - inform that you don't want exchange any cards in the third phase
    'queue' - show players queue
    'bet _value_' - bid the amount of _value_
    'bid status' - info about betting phase
    'balance' - show balance
                    Good luck!