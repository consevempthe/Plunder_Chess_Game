# User Stories - For P3 #

---
ChangeLog (put any changes you have made):
- 11/17/2020 : Updated Acceptance Criteria for must have user stories.
- 12/04/2020 : Finishing touches on user stories, added more acceptance criteria, added tasks.
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
We are finishing all of our "Must Have User Stories" during the P3 Sprints. These Include:
- As a user I can play plunder chess
- As a user, I need to register/login to the system
- As a user, I can invite others to a match
- As a user, I can leave the game and resume when I want
- As a user, I can delete my account

We are finishing all of the following "Should have user stories"
- I want access to game information and settings
- I can see other user's game history
- I want the game to tell me that I won

## **Must Have User Stories**
### **1. As a user, I can play plunder chess**

| | |
| :--- | :---
| Notes | - A user should receive dialog when they plunder a piece, and should receive dialog to use that plundered move <br /> - The game should tell you what legal moves a ChessPiece can make <br/> - The board should look the same for both players (i.e. the player should always be the on the bottom of the game screen.
| Acceptance Criteria | - The game and its pieces follows the rules of Chess and Plunder chess. <br/> -  Players are not allowed to move pieces illegally and illegal moves don't change the state of the game. <br /> - A player should only be able to move a piece during their turn. <br /> - A player should be notified when they can plunder a move, and if they want to use that plundered move. <br /> - A game should end with a winner and a loser, or be a draw. <br/> - The game should notify users when a player is in check. <br/> - A game should also provide users with a GUI to play Plunder Chess <br/> - The GUI should include methods to get input from the user to control the game logic <br> - The player should only receive a notification for checkmate and nothing else when it happens <br> - A player should be able to plunder when using a plundered move to plunder a piece. <br> - The plundering player should only receive game prompts. <br> - Both players should receive prompts about checkmate, draw and whether they won or lost. <br> - Only the player who is put in check should receive a prompt to move. <br> - A player should be notified when a piece has limited movement because moving that piece would cause check. <br> - The king is highlighted when the opposing player is put into check and un-highlighted after they escape check.

| | | |
| :--- | :--- | :---
| Tasks | 95. Create a ChessPiece class <br/> 96.  Knight Class and LegalMoves() <br/> 97.  Bishop Class and LegalMoves() <br/> 98.  Pawn Class and LegalMoves() <br/> 99. Rook Class and LegalMoves() <br/> 100. King Class and LegalMoves() <br/> 102. Queen Class and LegalMoves() <br/> 103. Pawn - En Passant <br/> 105. Create ChessBoard Class <br/> 121. Create Player Class <br/> 150. Create Movement Class <br> 285. Separate client package into gamelogic and client <br> 288. Law of demeter - board/move history <br> 291. reimplement addMoveToMoveHistory method <br> 296. UI notifies player when they can't move a piece due to check <br> 289. Remove turn order from chessboard <br> 294. Cleanup class names <br> 297. reimplement simulate move method <br> 298. PlunderUI only one player is notified <br> 304. reimplement ChessBoard move Method <br> 327. Retool chessboard tests <br> 292. Game event handling removed from ChessBoard| 77. Implement ChessPiece Plundering <br/> 104. King - Castling <br/> 107. Implement Games Class <br/> 108. Implement Checking <br/> 109. Implement Checkmate <br/> 124. Pawn - Upgrade <br/> 123. Implement Draw <br/> 132. Implement Vest Class <br/> 232. Castling - UI <br/> 231. EnPassant - UI <br/> 228. Add UI capturing and plunder dialogs <br/> 224. Connect Game with Player Class <br/> 217. Server on CS computer <br/> 208. Pawn Upgrade <br/> 207. Plunder - replace vest <br/> 206 Win lose draw - and checkmate <br/> 205. Check UI <br/> 204. Plunder - using movement <br/> 203. Plunder - vest and obtaining <br> 202. ChessPiece - highlight possible moves <br/> 201. ChessPiece - movement <br/> 200. ChessBoard UI <br/> 146. Capture piece for En Passant Move <br/> 129. Move (request) <br/> 104. Implement Castling for the King <br/> 76. Add to UI the option to steal captured piece’s moves (add a vest) <br/> 75. Add support for multiple games to the UI <br/> 48. Add basic UI for ChessBoard <br/> 49. Add basic UI for ChessPieces 

### **2. As a user, I need to register to the system or login**

| | |
| :--- | :--- |
| Notes |  - User can register with a unique username and password <br /> - Username should allow certain special characters
| Acceptance Criteria | - A username should be longer than 3 alphanumeric characters and contain no spaces <br /> - Users must have unique user names <br/> - Users must have a valid email address <br/> - A user password must be at least 7 alphanumeric characters <br/> - A user must submit an email, a password, and a username when creating an account. <br/> - When a user is logging in they must use the password they input for account creation. <br> - Registration for the game should be obvious for a new user. <br> - A user can only login to their account on one client.
| Tasks | 21. Create a server side database for users <br /> 20. Create user table <br /> 14. Implement register button <br /> 12 Add input boxes to register page <br /> 10. Handle message for add user on server <br /> 11. Handle messages for login on server <br /> 9. Create login message <br /> 2. Create register page <br /> 3. Implement submission of user/password <br /> 4. Implement username and password input boxes <br/> 214. SSH directly to Database <br/> 199. Register - Password Validation <br/> 198. Register - Username validation <br/> 197. Register - Email validation <br/> 

### **3. As a user, I can invite others to a match**

| | |
| :--- | :--- 
|Notes | - A user should be able to invite another user <br /> - The invited player should receive a message to accept the invite <br /> - Whoever starts the game starts the match (white player)
| Acceptance Criteria | - Invited player is notified <br /> -  Invites are sent to server and saved to a database <br /> - A game can only be started when the invited player accepts <br> - Players can send an invite to a player that is offline. <br> - A player that is offline can login and receive new invites. <br> - The player should receive a notification if the invited player declines. <br> - A player should have an area on the StartUI that shows them notifications for invites.
| Tasks | 312. INVITE: Accept invitations/receive notifications <br> 316. INVITE: Invites can be sent to offline players. <br> 317. Invitation UI <br> 311. INVITE: start button only works when the other player accepts <br> 

### **4. As user I would like to start a game**

| | |
| :--- | :--- |
| Notes | - Once game is started, all logic should be done on the Client side until saving/updating game established (asynchronous). |
| Acceptance Criteria | - Starting a game opens the game (chessboard) and the game begins <br> - A user can only start a game when the other player has accepted.
| Tasks | 209. Client - start a game <br/> 184. Game request <br/> 185. Games request

### **5. As a user, I can leave the game and resume when I want**

| | |
| :--- | :---
| Notes | - Game state is kept server side so either player can do a move and quit for the other player to come back to. <br /> - Players receive a turn notification for when its their turn
| Acceptance Criteria | - Game state is kept on server after exiting <br /> - Resumed games should start from the last game state. <br /> - Users should be able to play asynchronously <br> - A user should know when it is there turn in a game from the Start UI <br> - A user can continue their game of chess from the startUI <br> - A user should know if they have been put in check from the startUI <br> - A user should be prompted if they quit out of the game to say that the state of the game is being saved. <br> - A user should be prompted that they haven't made a move if they are trying to quit the game. <br> - A user knows all of the games that they are currently playing
| Tasks | 314. GamesUI - showing current games being played <br> 313. Asynchronous GamePlay <br> 315. GamesUI - should show if its the user's turn for the game or if they are in check
### **6. As a user, I can delete my account**

| | |
|:---|:---
| Notes | - Client should confirm that a user is about to delete account and what that means
| Acceptance Criteria | - Deleting an account deletes user information from server <br /> - Username and password for a deleted account are invalid for logging in <br> - Any games that player is in will be deleted and the other player will receive a notification about the game ending. <br> - A user can re-register using the email and account name after an account has been deleted.
| Tasks | 318. DELETE: allow the user to delete their account

---

## **Should Have User Stories**

### **1. I want access to game information and settings**

| | |
| :--- | :---
| Notes | - This should include statistics of games played, how long they took, w/l ratio and log of who played
| Acceptance Criteria | - After a game has been played it shows up in the history with all of the information  <br> - Shows the username and email for that player <br> - Show the Player's Match History
| Tasks | - 320. Profile UI

### **2. I can see other users game history**

| | |
| :--- | :---
| Notes | - Users should just be public until we have a friends list (i.e. a user can see all other users game history)
| Acceptance Criteria | - A player can search up any history using a nickname
| Tasks | - 320. Profile UI

### **3. I want the game to tell me that I won**

| | |
| :--- | :---
| Notes | - Should say which user wins and tell the elapsed time or total turns...or both
| Acceptance Criteria | - Forfeiting a game brings up the "win/lose game" dialog <br /> - A game shouldn't continue after checkmate <br> - The game should both players which player has one using that player's nickname <br> - The win should appear on their profile after the match
| Tasks | - 109. Checkmate logic <br> 206. Draw/Checkmate UI

---

## **Could Have User Stories**

### **1. I want to be able to customize my game settings**

| | |
| :--- | :---
| Notes | - This could be settings like - choosing who starts the game, turn timers and game timers, allowing players to plunder multiple times etc.
| Acceptance Criteria | - Rules changes affect both players <br /> - Players should be notified of the rules for the invitation
| Tasks | - TBD

### **2. I want to be able to play games of plunder against a bot**

| | |
| :--- | :---
| Notes | - Players should be able to choose a difficulty for the AI |
| Acceptance Criteria | - A player should be able to defeat an easy AI, but struggle on higher difficulties |
| Tasks | - TBD|

### **3. I want to create tournaments to play with other players**

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
