# CRC Cards - P2

## Client Package

| **User** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| <br> - Creates a game <br> - Invites another player <br> - Leaves a game <br> - Has a Match History <br> - Holds the email/username/and password <br> - Pauses and resumes a game <br> - Can be deleted | - Game <br> - Profile <br> - Client <br> - MatcHistory

|**Game Client** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Handles responses from UI and calls the server to get certain information | - Response Notifications <br> - User <br> LoginUI

|**Response Notifications** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Handles responses from the server and calls the UI to get change information | - Game Client <br> - Server

## GameLogic Package

|**ChessBoard** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Moves, captures, and plunders ChessPieces on the board <br> - Allows for the use of vests from plundering <br> - Initializes the starting positions of ChessPieces <br> - Keeps track of the board state (stalemate, check, checkmate) <br> - Promotes a pawn when it reaches the end of the board  <br> - Knows which Player can move pieces and which King is in check based on player color. <br> - Contains a history of the moves that have been made. | - Game <br> - Player <br> - ChessPiece <br> - Move History <br> - Vest <br>

|**ChessPiece** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Knows its position on the ChessBoard <br> - Has a color based on the player. <br> - Knows whether it has moved or not. <br> - Belongs to a Chessboard <br> - Is either a rook, bishop, knight, pawn, queen, king <br> - Can gain a Vest of another piece <br> - Knows what ChessPiece's it can plunder <br> - Knows the Vest it has on <br> - Knows what legal moves it can make, in and out of Check | - Player <br> - Chessboard <br> - Vest <br> - Movement

|**Piece Movement** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - A collection of movements that a ChessPiece can make | - ChessPiece

|**Vest** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Denotes which movement a ChessPiece has plundered based on a unique color. <br> - Knows the move a ChessPiece can make based on its vest. | - ChessPiece <br> - ChessBoard

|**Game** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Has two players and knows the current player. <br> - Has a Game Status <br> - Has a chessboard <br> - Notifies players of plundering, checkmate and stalemate. <br> - Increments the turn <br> - Allows the player to move, capture and plunder based on player input | - User(s) <br> - Game Status <br>  - Chessboard <br> - Game History <br> - ChessBoardUI <br> - Player

|**Player** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Knows the User's name <br> - Knows what ChessPieces it can move <br> - Can be put into Check/Checkmate | - Chessboard <br> - Game <br> - ChessPiece

|**Match History** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Has the list of games that a user has played | - User <br> - Game

|**Move** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Knows the ChessPiece and the move that it made <br> - Know the type of piece that was captured <br> - Knows the position of those pieces | - ChessPiece <br> - ChessBoard <br> - Move History

|**Move History** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - A collection of moves made on a chessboard <br> - Helps check if the game is a stalemate| - ChessPiece <br> - ChessBoard <br> - Move

|**Game Status** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Keeps track of the game time <br> - Knows the result of a game <br> - Knows the status of a game (i.e. Win, Draw).| - Game <br> - Game History

## clientUI Package

|**ChessBoardUI** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Visually represents the Chessboard depending on the player's color <br> - Renders the board with ChessPieces in the correct locations  <br> - Allows the user to move ChessPieces based on their input <br> - Updates the Game based on user choices. <br>- Let's a player plunder other ChessPieces <br> - Highlights legal movement for a selected ChessPiece| - Game <br> - Game Client <br> - CheckUI <br> - PlunderUI <br> - PawnPromoteUI

| **CheckUI** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Informs a player when they are put into check and tells them to save their king. | - ChessBoardUI

| **PawnPromoteUI** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Informs a player when they can upgrade their pawn <br> - Let's a player choose which ChessPiece to upgrade into | - ChessBoardUI <br> - (Pawn) ChessPiece

| **LoginUI** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Let's a User login to XGame System | - Game Client <br> - RegisterUI <br> - StartUI

| **RegisterUI** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Let's a User register to the XGame System | - LoginUI <br> - Game Client

|**StartUI**| |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Allows a user to register or login <br> - Allows a user to invite other games <br> - Has users <br> - Allows a user to play a game of Plunder Chess | - Game Client <br> - LoginUI

## Server Package

|**Server Socket** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Accesses the database thru a ssh connection <br> - Connects user clients together via notifications and request/response | - Request/Response Notifications <br> - Game Client <br> - Database Connection

|**Database Connection** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Stores user information and game history | - Server Socket

|**Request Notifications** | |
| :--- | :---
| **Responsibilities** | **Collaborators**
| - Handles the Requests generated by the user through the client <br> - Talks with the database to get the necessary information | - Database Connection <br> - Game Client

