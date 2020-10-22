# CRC Cards - Sprint 2

| **User** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| <br> - Creates a game <br> - Invites another player <br> - Leaves a game <br> - Has a profile <br> - Accepts and rejects a game invite <br> - Pauses and resumes a game | - Game <br> - Profile <br> - Client

|**Profile/Account** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Contains a public match history <br> - Has a username <br> - Holds the email/password (privately) <br> -  Has the option to be deleted | - User <br> - Match History

|**Game** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Has two players <br> - Has a game status <br> - Has a chessboard <br> - Determines who is the winner | - User(s) <br> - Game Status <br>  - Chessboard <br> - Game History <br> - Profile/Account

|**Game Status** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Has a game timer <br> - Keeps track of the number of turns <br> - Contains Start/end dates for the game <br> - Has the winner | - Game <br> - Game History

|**Player** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Knows which color it is <br> - Knows what ChessPieces it has and  what ChessPiece's its captured <br> - Can be put into Check/Checkmate | - Chessboard <br> - Game <br> - Pieces

|**Game History** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Has results and statistics for every game played for a user profile | - Profile <br> - Game

|**ChessBoard** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Moves pieces and records the movement <br> - Allows for the use of vests/plundering <br> - Initializes the starting positions for the game <br> - Can get and set ChessPiece position <br> - Keeps track of the board state (draw, check, checkmate) <br> - Handles Pawn promoting | - Game <br> - Player <br> - ChessPiece <br> - Move History - Vest <br>

|**ChessBoardUI** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - A visual represenation of the chessboard <br> - Renders the board with pieces in the correct locations  <br> - Calls funcations to play plunder chess through the Game class| - Game <br> - Player <br> - ChessPiece <br> - ChessBoard

|**Move** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Knows the ChessPiece and the move that it made <b> - Know the type of piece that was captured, if any | - ChessPiece <br> - ChessBoard <br> - Move History

|**Move History** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Is a collection of Move objects for a ChessPiece object <br> - Keeps track of type of moves for draw checking | - ChessPiece <br> - ChessBoard <br> - Move

|**ChessPiece** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Knows its position <br> - Has a color <br> - Plunders another ChessPiece's moves <br> - Has a Chessboard <br> - Is either a rook, bishop, knight, pawn, queen, king <br> - Can gain a Vest <br> - Knows what Vest it can have <br> - Knows the Vest it has on <br> - Knows what legal moves it can make, in and out of Check <br>  - Has a history of the moves it made | - Player <br> - Chessboard <br> - Vest <br> - Movement <br> - Move History

|**Vest** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Has a ChessPiece <br> - Has a color <br> - Determines when a piece has a plundered move it can use | - ChessPiece

|**Game Client** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Allows a user to create a profile <br> - Sets up a game <br> - Has profiles <br> - Sends and receives Requests/Responses | - Server <br> - Request/Response Notifications

|**Server Socket** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Accesses the database thru a connection <br> - Connects user clients together via notifications | - Client <br> - Database

|**Database Connection** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Stores user information and game history | - Server

|**Request/Response Notifications** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Informs a user about active games, game results, and invitations | - Client

