# CRC Cards

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
| - Knows which color it is <br> - Has pieces and captured pieces <br> - Captures a piece <br> - Wins games | - Chessboard <br> - Game <br> - Pieces

|**Game History** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Has results and statistics for every game played for a user profile | - Profile <br> - Game

|**ChessBoard** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Moves pieces <br> - Knows when a player is in check/checkmate <br> - Has a turn order | - Game <br> - Player <br> - ChessPiece

|**ChessPiece** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Knows its position <br> - Has a color <br> - Plunders another piece's moves <br> - Belongs to a Chessboard <br> - Is either a rook, bishop, knight, pawn, queen, king <br> - Knows its legal moves depending on what piece it is | - Player <br> - Chessboard

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
| - Let's a user know about games/results/invitations | - Client

