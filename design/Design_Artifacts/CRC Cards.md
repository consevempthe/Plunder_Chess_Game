# CRC Cards

| **User** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| <br> - Create a game <br> - Can invite another player <br> - Leave a game <br> - Has a profile <br> - Can accept/reject a game invite <br> - Can pause and resume a game | - Game <br> - Profile <br> - Client

|**Profile/Account** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Contains the match history <br> - Has username <br> - Holds the email/password (privately) <br> - Can delete the account | - User

|**Game** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Has two players <br> - Keeps track of the number of turns <br> - Has a timer <br> - Has a chessboard <br> - Determines who is the winner | - User(s) <br> - Chessboard <br> - Game History <br> - Profile/Account

|**Player** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Knows which color it is <br> - Has pieces and captured pieces <br> - Can capture a piece | - Chessboard <br> - Game <br> - Pieces

|**Game History** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Has results and statistics for every game played for a user profile | - Profile <br> - Game

|**ChessBoard** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Moves pieces <br> - Knows when a player is in check/checkmate <br> - Knows which player's turn it is | - Game <br> - Player <br> - ChessPiece

|**ChessPiece** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Knows its position <br> - Has a color <br> - Can plunder another piece <br> - Belongs to a Chessboard <br> - Is either a rook, bishop, knight, pawn, queen, king <br> - Knows its legal moves depending on what piece it is | - Player <br> - Chessboard

|**Client** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Let's a user create a profile <br> - Sets up a game <br> - Has profiles <br> - Sends and receives Requests/Responses | - Server

|**Server** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Can access the database <br> - Connects clients together via notifications | - Client <br> - Database

|**Database** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Stores user information and game history | - Server

|**Request/Response** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Let's a user know about games/results/invitations | - Client

