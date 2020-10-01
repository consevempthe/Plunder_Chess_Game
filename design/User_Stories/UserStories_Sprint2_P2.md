# User Stories - Updated for Sprint 2 #

---
ChangeLog (put any changes you have made):
1. DateOfChange - What you changed
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

---

## **Epics**
A list of the epics we have created so far
1. Setup Database
    - Create the data structure to store users and game states for any game that is currently going on.
2. Setup Server
    - Create server side implementation so client can retrieve information about users and games
3. Login screen
    - Allow new users to register for an account
4. Create a match
    - Create the client and allow users to play games
5. Play a match
    - Users can play plunder chess, logic for the game is written
6. Settings and game status
    - Create settings, player history, and game statuses

## **Must Have User Stories**
### **1. As user I would like to start a game**

| | |
| :--- | :--- |
| Notes | - Once game is started, all logic should be done on the Client side until saving/updating game status. |
| Acceptance Criteria | - Starting a game opens the game client and the game begin
| Tasks | [33.][33] Add button to dashboard to start game <br /> [34.][34] Create Game table in database  <br /> [35.][35] Add backend logic to create a new game <br /> [36.][36] Add basic window for a game once its been created

### **2. As a user, I need to register to the system or login**

| | |
| :--- | :--- |
| Notes |  - User can register with a unique username and password <br /> - Username should allow special characters
| Acceptance Criteria | - Test with a unique username <br /> - Test with a duplicate username 
| Tasks | [21.][21] Create a server side database for users <br /> [20.][20] Create user table <br /> [14.][14] Implement register button <br /> [12.][12] Add input boxes to register page <br /> [10.][10] Handle message for add user on server <br /> [11.][11] Handle messages for login on server <br /> [9.][9] Create login message <br /> [2.][2] Create register page <br /> [3.][3] Implement submission of user/password <br />[4.][4] Implement username and password input boxes

### **3. As a user, I can invite others to a match**

| | |
| :--- | :--- 
|Notes | - A user should be able to invite another user <br /> - The invited player should receive a message to accept the invite <br /> - The original user should receive a message if it was declined <br /> - Whoever created the game starts the match (white player)
| Acceptance Criteria | - Invite dialog opens for invited player <br /> -  If declined the user should receive a message <br /> - Game should start when the user accepts
| Tasks | [37.][37] Add message for invites <br /> [38.][38] Add button to game UI to open the invite <br /> [39.][39] server can send and receive messages <br /> [40.][40] UI for invite notification <br /> [41.][41] invite message is sent to server

### **4. As a user, I can leave the game and resume when I want**

| | |
| :--- | :---
| Notes | - Game state is kept server side so either player can do a move and quit for the other player to come back to. <br /> - Players receive a turn notification for when its their turn
| Acceptance Criteria | - Game state is kept on server after exiting <br /> - Resumed games should start from the last game state. <br /> - Users should be able to play asynchronously
| Tasks | [42.][42] Exit button UI <br /> [43.][43] Implement exit button <br /> [44.][44] Server saves the game state <br /> [45.][45] Resume game UI <br /> [46.][46] Implement Resume game <br /> [73.][73] Create UI for turn notification <br /> [74.][74] Server sends the notification when a player has finished there turn

### **5. As a user, I can play plunder chess**

| | |
| :--- | :---
| Notes | - A user should receive dialog when they plunder a piece, and should receive dialog to use that plundered move <br /> - The game should tell you what legal moves you can make
| Acceptance Criteria | - Pieces only move according to the rules for that piece <br /> - Illegal move for a piece shouldn't change the state of the game <br /> - Pieces can only be moved on the users turn
| Tasks | [48.][48] UI for game board <br /> [49.][49] UI for pieces <br /> [50.][50] Implement game states - like turn order, pieces on board, time elapsed <br /> [51.][51] Server implementation of saving game state <br /> [52.][52] Game logic for pieces <br /> [53.][53] Game logic for valid movement <br /> [68.][68] Implement Plundering <br /> [76.][76] UI dialog for stealing a piece and using a stolen move

### **6. As a user, I can delete my account**

| | |
|:---|:---
| Notes | - Client should confirm that a user is about to delete account and what that means
| Acceptance Criteria | - Deleting an account deletes user information from server <br /> - Username and password for a deleted account are invalid for logging in
| Tasks | [63.][63] Delete account UI in user setting <br /> [64.][64] Implement delete account <br /> [65.][65] Send user back to game client register screen

---

## **Should Have User Stories**

### **1. I want access to game information and settings**

| | |
| :--- | :---
| Notes | - This should include statistics of games played, how long they took, w/l ratio and log of who played
| Acceptance Criteria | - After a game has been played it shows up in the history with all of the information
| Tasks | [55.][55] Add settings cog and dialog <br /> [56.][56] Implement settings toggle and text boxes for dialog <br /> [57.][57] Server side setting changes <br /> [58.][58] Implement game status dialog <br /> 

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
| Tasks | [66.][66] Implement UI for victory dialog box <br /> [67.][67] Implement game logic to determine winner creating dialog box and ending the game

---

## **Could Have User Stories**

### **1. I want to be able to customize my game settings**

| | |
| :--- | :---
| Notes | - This could be settings like - choosing who starts the game, turn timers and game timers, allowing players to plunder multiple times etc.
| Acceptance Criteria | - Rules changes affect both players <br /> - Players should be notified of the rules for the invitation
| Tasks | [72.][72] Allow player to choose who starts the game

---

## **Design related Tasks**
These are tasks that were created in conjunction with P1 objectives - creating user stories and CRC cards etc.
- [31.][31] Link wiki P1 page with artifacts, CRC cards, user stories/tasks document, Kanban board, etc. 

- [30.][30] Create and upload document of user stories and tasks 

- [29.][29] Create and upload CRC cards 

- [28.][28] Finish Creating Tasks from user stories 

- [27.][27] Add Class Diagram to GitHub 

- [26.][26] Pick UI framework 

- [25.][25] Pick a continuous code quality tool 

- [24.][24] Pick continuous integration tool 

- [23.][23] Pick unit testing tool 

- [19.][19] Update Tasks and Stories 


[1]: /../../issues/1
[2]: /../../issues/2
[3]: /../../issues/3
[4]: /../../issues/4
[5]: /../../issues/5
[6]: /../../issues/6
[7]: /../../issues/7
[8]: /../../issues/8
[9]: /../../issues/9
[10]: /../../issues/10
[11]: /../../issues/11
[12]: /../../issues/12
[13]: /../../issues/13
[14]: /../../issues/14
[15]: /../../issues/15
[16]: /../../issues/16
[17]: /../../issues/17
[18]: /../../issues/18
[19]: /../../issues/19
[20]: /../../issues/20
[21]: /../../issues/21
[22]: /../../issues/22
[23]: /../../issues/23
[24]: /../../issues/24
[25]: /../../issues/25
[26]: /../../issues/26
[27]: /../../issues/27
[28]: /../../issues/28
[29]: /../../issues/29
[30]: /../../issues/30
[31]: /../../issues/31
[32]: /../../issues/32
[33]: /../../issues/33
[34]: /../../issues/34
[35]: /../../issues/35
[36]: /../../issues/36
[37]: /../../issues/37
[38]: /../../issues/38
[39]: /../../issues/39
[40]: /../../issues/40
[41]: /../../issues/41
[42]: /../../issues/42
[43]: /../../issues/43
[44]: /../../issues/44
[45]: /../../issues/45
[46]: /../../issues/46
[47]: /../../issues/47
[48]: /../../issues/48
[49]: /../../issues/49
[50]: /../../issues/50
[51]: /../../issues/51
[52]: /../../issues/52
[53]: /../../issues/53
[54]: /../../issues/54
[55]: /../../issues/55
[56]: /../../issues/56
[57]: /../../issues/57
[58]: /../../issues/58
[59]: /../../issues/59
[60]: /../../issues/60
[61]: /../../issues/61
[62]: /../../issues/62
[63]: /../../issues/63
[64]: /../../issues/64
[65]: /../../issues/65
[66]: /../../issues/66
[67]: /../../issues/67
[68]: /../../issues/68
[69]: /../../issues/69
[70]: /../../issues/70
[71]: /../../issues/71
[72]: /../../issues/72
[73]: /../../issues/73
[74]: /../../issues/74
[75]: /../../issues/75
[76]: /../../issues/76
[77]: /../../issues/77
[78]: /../../issues/78