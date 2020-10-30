# User Stories - Updated for Sprint 2 #

---
ChangeLog (put any changes you have made):
1. 2020-10-07 - Changed order of stories.
2. 2020-10-11 - Updated Acceptance Criteria for Plunder Chess. Updated the Prompt and added user stories. Updated epics to show what we are working on.
3. 2020-10-20 - Updated Acceptance Criteria for "I need to register to the system or login"
4. 2020-10-28 - Updated "As a user, I can invite others to a match" and "As user I would like to start a game" sections
5. 2020-10-30 - Revised for P2
___

Prompt

---
>“What I need is something like a platform that allows users to play the xGame online. Anyone could register to this platform, for example by using an email, which would be unique for that user. To register, the person should provide a nickname (also unique, maybe public???) and a password. 
><br />
><br />
>What can a user do in the platform? Mmmm. She could create a new match (so she can play it). Since she can't play by herself, she should be able to invite another user to join the match. Perhaps she could send more than one invitation, and then it would be something like "first come, first served", so the first user accepting the invitation will be the one joining the match??? Is that possible?? I guess a user also needs to be able to reject an invitation, so it would be nice if the user who sent it receives a notification. 
><br />
><br />
>It would be cool if a user could be part of multiple games at the same time, though maybe she would want to quit from any game at any time? I think a user would also want to be able to unregister.
><br />
><br />
>The platform also needs to record the history of matches played by a user. Info like players, start and end dates and times, and end results would be useful, you know, to know who won or lost or if there was a tie. I guess info about abandoned games should be also recorded. All this info would be part of the user profile, which can only be viewed by registered users.
><br />
><br />
>The gameplay, well—the xGame has some rules that need to be followed during a match. Besides that, of course a game can't start until enough players have joined, and I'm guessing that after a match starts no other player should be able to join. Who starts the match? If I'm not wrong, that should be specified in the rules of the game. Otherwise, the user who created the match would be the one making the first move. Mmmm, the system should be able to determine whose turn is it... according to the rules, right? Meaning, a player can only make moves when it’s her turn... allowed moves, that is... the rules.
><br />
><br />
>What else? Oh right. The state of the matches should be saved in some way, so the user can play whenever she wants. My guess is that users won't be playing the whole time, so for example, a user would make a move whenever is her turn and log out, and after a while she would come back and check if the other player made a move and it’s her turn again. Asynchronous matches, I think that describes it. The system needs to know when a game is over and should let know the players who won or lost. All according to the rules.”
><br/>
><br/>
>“It would be super cool if a user could play against a bot player, you know, something like an AI agent that could decide what’s the best move to make??
><br/>
><br/>
>Another nice thing would be the organization of tournaments. So, a user could start a tournament that other users could join. Then the tournament would start with something like eight matches between unique players. The hierarchy of the matches could be randomly determined by the system before the first round of games starts. Badges!! The winner of the tournament would receive a gold badge and the second place would receive a silver badge. Those badges need to become part of the user profile, a public part. The system can use this info in a player ranking table, which should be publicly available.
><br/>
><br/>
>What about a chat or some messaging feature? that would be so cool, too. Is that possible???”

---

## **Epics**
These are the User Stories we are tackling for this sprint.

1. Client UI
    - In this epic we plan to create a GUI to allow the user to interface with the xGame registration system.
    - The registration ui will include a window with a login section and register section
    - The register section will allow users to create an xGame account using their email, nickname, and a password
    - The login section will allow existing users to return to xGame to play
2. ChessBoard UI
    - This epic includes the GUI for the chess board itself
    - ChessBoard UI will allow players to click on pieces and move them

## **Must Have User Stories**
### **1. As a user, I can play plunder chess**

| | |
| :--- | :---
| Notes | - A user should receive dialog when they plunder a piece, and should receive dialog to use that plundered move <br /> - The game should tell you what legal moves a ChessPiece can make <br/> - The board should look the same for both players (i.e. the player should always be the on the bottom of the game screen.
| Acceptance Criteria | - The game and its pieces follows the rules of Chess and Plunder chess. <br/> -  Players are not allowed to move pieces illegally and illegal moves don't change the state of the game. <br /> - A player should only be able to move a piece during their turn. <br /> - A player should be notified when they can plunder a move, and if they want to use that plundered move. <br /> - A game should end with a winner and a loser, or be a draw. <br/> - The game should notify users when a player is in check. <br/> - A game should also provide users with a GUI to play Plunder Chess <br/> - The GUI should include methods to get input from the user to control the game logic

| | | |
| :--- | :--- | :---
| Tasks | 95. Create a ChessPiece class <br/> 96.  Knight Class and LegalMoves() <br/> 97.  Bishop Class and LegalMoves() <br/> 98.  Pawn Class and LegalMoves() <br/> 99. Rook Class and LegalMoves() <br/> 100. King Class and LegalMoves() <br/> 102. Queen Class and LegalMoves() <br/> 103. Pawn - En Passant <br/> 105. Create ChessBoard Class <br/> 121. Create Player Class <br/> 150. Create Movement Class | 77. Implement ChessPiece Plundering <br/> 104. King - Castling <br/> 107. Implement Games Class <br/> 108. Implement Checking <br/> 109. Implement Checkmate <br/> 124. Pawn - Upgrade <br/> 123. Implement Draw <br/> 132. Implement Vest Class <br/> 232. Castling - UI <br/> 231. EnPassant - UI <br/> 228. Add UI capturing and plunder dialogs <br/> 224. Connect Game with Player Class <br/> 217. Server on CS computer <br/> 208. Pawn Upgrade <br/> 207. Plunder - replace vest <br/> 206 Win lose draw - and checkmate <br/> 205. Check UI <br/> 204. Plunder - using movment <br/> 203. Plunder - vest and obtaining <br> 202. ChessPiece - highlight possible moves <br/> 201. ChessPiece - movement <br/> 200. ChessBoard UI <br/> 146. Capture piece for En Passant Move <br/> 129. Move (request) <br/> 104. Implement Castling for the King <br/> 76. Add to UI the option to steal captured piece’s moves (add a vest) <br/> 75. Add support for multiple games to the UI <br/> 48. Add basic UI for ChessBoard <br/> 49. Add basic UI for ChessPieces 

### **2. As a user, I need to register to the system or login**

| | |
| :--- | :--- |
| Notes |  - User can register with a unique username and password <br /> - Username should allow certain special characters
| Acceptance Criteria | - A username should be longer than 3 alphanumeric characters and contain no spaces <br /> - Users must have unique user names <br/> - Users must have a valid email address <br/> - A user password must be at least 7 alphanumeric characters <br/> - A user must submit an email, a password, and a username when creating an account. <br/> - When a user is logging in they must use the password they input for account creation.
| Tasks | 21. Create a server side database for users <br /> 20. Create user table <br /> 14. Implement register button <br /> 12 Add input boxes to register page <br /> 10. Handle message for add user on server <br /> 11. Handle messages for login on server <br /> 9. Create login message <br /> 2. Create register page <br /> 3. Implement submission of user/password <br /> 4. Implement username and password input boxes <br/> 214. SSH directly to Database <br/> 199. Register - Password Validation <br/> 198. Register - Username validation <br/> 197. Register - Email validation <br/> 

### **3. As a user, I can invite others to a match**

| | |
| :--- | :--- 
|Notes | - A user should be able to invite another user <br /> - The invited player should receive a message to accept the invite <br /> - Whoever starts the game starts the match (white player)
| Acceptance Criteria | - Invited player is notified <br /> -  Invites are sent to server <br /> - User can accept by starting the game <br /> - Invites are recorded to a database
| Tasks | - Add message for invites <br /> - Add button to UI to invite <br /> - Server can send and receive messages <br /> - UI for invite notification <br /> - Invite request is sent to server

### **4. As user I would like to start a game**

| | |
| :--- | :--- |
| Notes | - Once game is started, all logic should be done on the Client side until saving/updating game established (asynchronous). |
| Acceptance Criteria | - Starting a game opens the game (chessboard) and the game begins
| Tasks | - Add button to dashboard to start game <br /> - Create Game table in database  <br /> - Add basic window for a game once its been created <br /> -User can start game in UI <br/> 184. Game request <br/> 185. Games request

### **5. As a user, I can leave the game and resume when I want**

| | |
| :--- | :---
| Notes | - Game state is kept server side so either player can do a move and quit for the other player to come back to. <br /> - Players receive a turn notification for when its their turn
| Acceptance Criteria | - Game state is kept on server after exiting <br /> - Resumed games should start from the last game state. <br /> - Users should be able to play asynchronously
| Tasks | 42. Exit button UI <br /> 43. Implement exit button <br /> 44. Server saves the game state <br /> 45. Resume game UI <br /> 46. Implement Resume game <br /> 73. Create UI for turn notification <br /> 74. Server sends the notification when a player has finished there turn <br/> 210. Asychronous <br/> 209. Client - Start a game

### **6. As a user, I can delete my account**

| | |
|:---|:---
| Notes | - Client should confirm that a user is about to delete account and what that means
| Acceptance Criteria | - Deleting an account deletes user information from server <br /> - Username and password for a deleted account are invalid for logging in
| Tasks | 63. Delete account UI in user setting <br /> 64. Implement delete account <br /> 65. Send user back to game client register screen

---

## **Should Have User Stories**

### **1. I want access to game information and settings**

| | |
| :--- | :---
| Notes | - This should include statistics of games played, how long they took, w/l ratio and log of who played
| Acceptance Criteria | - After a game has been played it shows up in the history with all of the information
| Tasks | 55. Add settings cog and dialog <br /> 56. Implement settings toggle and text boxes for dialog <br /> 57. Server side setting changes <br /> 58. Implement game status dialog <br /> 

### **2. I can see other users game history**

| | |
| :--- | :---
| Notes | - Users should just be public until we have a friends list (i.e. a user can see all other users game history)
| Acceptance Criteria | - Clicking a username takes them to the public profile of that user
| Tasks | n/a

### **3. I want the game to tell me that I won**

| | |
| :--- | :---
| Notes | - Should say which user wins and tell the elapsed time or total turns...or both
| Acceptance Criteria | - Forfeiting a game brings up the "win/lose game" dialog <br /> - A game shouldn't continue after checkmate
| Tasks | 66. Implement UI for victory dialog box <br /> 67. Implement game logic to determine winner creating dialog box and ending the game

---

## **Could Have User Stories**

### **1. I want to be able to customize my game settings**

| | |
| :--- | :---
| Notes | - This could be settings like - choosing who starts the game, turn timers and game timers, allowing players to plunder multiple times etc.
| Acceptance Criteria | - Rules changes affect both players <br /> - Players should be notified of the rules for the invitation
| Tasks | 72. Allow player to choose who starts the game

### **2. I want to be able to play games of plunder against a bot**

| | |
| :--- | :---
| Notes | - Players should be able to choose a difficulty for the AI |
| Acceptance Criteria | - A player should be able to defeat an easy AI, but struggle on higher difficulties |
| Tasks | - TBD|

### **3. I want to be create tournaments to play with other players**

| | |
| :--- | :---
| Notes | - This should be another menu where a player can invite groups of people. <br> - The bracket should be generated depending on how many players joined. |
| Acceptance Criteria | - Players can invite any number of people to a tournament |
| Tasks | - TBD |

### **4. I want to earn badges for winning games in a tournament**

| | |
| :--- | :---
| Notes | - Should be some sort of icon that looks really cool and can be displayed in multiples on a player profile. |
| Acceptance Criteria | - If a player gets 1st or 2nd in a tournament they should be notified that they got a badge and should see it on there profile |
| Tasks | - TBD |

### **5. I want to be able to chat with my opponent during games**

| | |
| :--- | :---
| Notes | - Chat should be unobtrusive to playing the game <br/> - Potentially have a filter for language |
| Acceptance Criteria | - A player can receive and send messages during a game <br/> - A player can look at chat history after the game |
| Tasks | - TBD |
---
