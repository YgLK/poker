<h1 align="center"> POKER GAME: Five-card draw 

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
[![Generic badge](https://img.shields.io/badge/MAVEN-3.8.1-<COLOR>.svg)](https://shields.io/)
[![Generic badge](https://img.shields.io/badge/coverage-65/100-yellow.svg)](https://shields.io/)

## Table of Contents

- [Instructions](#instructions)
- [CLIENT-SERVER communication](#communication_cl_server)
- [Gameplan (all phases with description)](#gameplan)
- [Useful commands](#commands)
- [Javadoc & SonarQube raport](#doc_scube)


## Instructions (how to run the game) <a name="instructions"></a>

<ol>
 <li> Build the project with Maven
</li>
 <li> Find target directory in the poker-server and poker-client module.
</li>
 <li> Run the Server from your terminal after moving to the target directory in the poker-server module by entering this command:

    ```java -jar .\poker-server-1.0-jar-with-dependencies.jar```
</li>
 <li> Run the Client from another terminal after moving to the target directory in the poker-client module by entering this command:

   ``` java -jar .\poker-client-1.0-jar-with-dependencies.jar```
</li>
 <li> You should run multiple Clients each from another terminal. When all players have set nicknames you can start the game (don't type `get cards` command before all players has joined).</li>
</ol>


Type `queue` to check when is your turn.

## CLIENT-SERVER communication <a name="communication_cl_server"></a>

Server-client communication is implemented with the use of Java IO. <br> <br>
At the beginning the server waits, listening to the socket for a client <br>
to make a connection request. When the server code encounters the accept method, <br>
it blocks until a client makes a connection request to it. If everything goes well, the server accepts the connection.

Client can communicate with the server by typing commands used for the Poker gameplay (list below). <br>
Server recognizes typed in commands and process it, result is sent back to the Client. <br>

## GAMEPLAN (all poker-game phases with description) <a name="gameplan"></a>
 Game rules available under this [link](https://pl.wikipedia.org/wiki/Poker_pi%C4%99ciokartowy_dobierany). The game is split into 5 phases.

  
  `1` Every player pays Ante and take cards from the table. *[Start]*

  `2` First round of betting. *[Bet 1]*

  `3` Players exchange cards. *[Exchange]*

  `4` Second round of betting. *[Bet 2]*

  `5` Determine a winner.  *[Finish]*

## USEFUL COMMANDS <a name="commands"></a>

   ```get cards``` - take card from the table *[Phase 1]*

    `exchange cards _ _ _` - exchange cards from your hand (cards are numbered 0 to 4) e.g. 'exchange cards 1 3' *[Phase 3]*

    `stay `- inform that you don't want to exchange any cards in the third phase *[Phase 3]*

    `queue` - show players queue

    `balance` - show balance

    `bet _value_` - bid the amount of _value_ *[Phase 2/4]*

   `bid status` - info about betting phase *[Phase 2/4]*

    `winner` - show winner *[Phase 5]*

   `won cards` - show winner's cards *[Phase 5]*

    `phase`- get current phase of the game

    `restart` - vote for new game *[Phase 5]*

## Javadoc & SonarQube raport <a name="doc_scube"></a>
 In order to explore `Javadoc` or `SonarQube raport` download appropriately `javadoc` or `sonar-cube` folder,<br> open them and for:
 > `Javadoc`: open [allclasses.html](https://github.com/YgLK/poker/blob/dev/javadoc/allclasses.html) file <br> Then the project documentation will appear.
 
 or 
 
 > `SonarQube raport`: <br> 
> open [Poker_SonarQube_Coverage.html](https://github.com/YgLK/poker/blob/dev/sonar-cube/Poker_SonarQube_Coverage.html) <br> Then project test coverage will appear. <br>
> open [Poker_SonarQube_Issues.html](https://github.com/YgLK/poker/blob/dev/sonar-cube/Poker_SonarQube_Issues.html) <br>Then project code smells (issues) raport will appear.
