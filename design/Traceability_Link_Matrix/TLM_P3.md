# Traceability Link Matrix

### User Story: I can play plunder chess - A list of tasks and classes involved in this user story.

| **Row: Tasks, Column: Classes** | ChessBoard | ChessPiece | Knight | Bishop | Rook | Queen | King | Pawn | Game | Player | Vest | Move | Piece Movement | Move History | GameUI |
| :------------------------------ | ---------: | ---------: | -----: | -----: | ---: | ----: | ---: | ---: | ---: | -----: | ---: | ---: | -------------: | -----------: | -----: |
| 95. Create ChessPiece Class     |            |          X |        |        |      |       |      |      |      |        |      |      |                |              |        |
| 105. Create ChessBoard Class    |         X  |            |        |        |      |       |      |      |      |        |      |      |                |              |        |   
| 118. ChessBoard toString()      |         X  |            |        |        |      |       |      |      |      |        |      |      |                |              |        |
| 96. Knight Class                |            |            |     X  |        |      |       |      |      |      |        |      |      |              X |              |        |
| 97. Bishop Class                |            |            |        |      X |      |       |      |      |      |        |      |      |              X |              |        |
| 99. Rook Class                  |            |            |        |        |    X |       |      |      |      |        |      |      |              X |              |        |
| 102. Queen Class                |            |            |        |        |      |     X |      |      |      |        |      |      |              X |              |        | 
| 100. King Class                 |            |            |        |        |      |       |    X |      |      |        |      |      |              X |              |        |
| 104. Implement Castling         |          X |            |        |        |      |       |    X |      |      |        |      |      |              X |            X |        |
| 98. Pawn Class                  |            |            |        |        |      |       |      |    X |      |        |      |      |              X |              |        |
| 103. Pawn En Passant            |            |            |        |        |      |       |      |    X |      |        |      |      |              X |            X |        |
| 108. Game Check Logic           |          X |            |        |        |      |       |    X |      |      |      X |      |      |                |            X |        |
| 121. Implement Player Class     |            |            |        |        |      |       |      |      |      |      X |      |      |                |              |        |
| 107. Implement Plundering       |          X |          X |      X |      X |    X |     X |    X |    X |      |        |    X |    X |                |              |        |
| 109. Game Checkmate Logic       |          X |            |        |        |      |       |      |      |    X |      X |      |      |                |            X |        |
| 132. Implement Vest Class       |            |          X |        |        |      |       |      |      |      |        |    X |      |                |              |        |
| 150. Implement Movement Class   |            |            |      X |      X |    X |     X |    X |    X |      |        |      |    X |              X |              |        |
| 123. Game Draw Logic            |          X |            |        |        |      |       |      |      |      |        |      |      |                |            X |        |
| 123. Pawn Upgrade               |          X |            |        |        |      |       |      |    X |      |        |      |      |                |              |        |
| 107. Implement Game Class       |          X |            |        |        |      |       |      |      |    X |        |      |      |                |              |        |
| 121. Implement Player Class     |            |            |        |        |      |       |      |      |    X |      X |      |      |                |              |        |
| 208. Pawn Promotion User Input  |          X |            |        |        |      |       |      |      |      |        |      |      |                |              |        |
| 224. Player With Class          |          X |          X |      X |      X |    X |     X |    X |    X |    X |      X |      |      |              X |            X |      X |
| 231. EnPassant UI               |          X |            |        |        |      |       |      |    X |    X |        |      |      |              X |              |      X |
| 232. Castling UI                |          X |            |        |        |      |       |    X |      |    X |        |      |      |                |              |      X |
| 204. Plunder UI - Movement      |          X |            |        |        |      |       |      |      |      |        |      |      |                |              |      X |
| 206. Draw/Checkmate UI          |            |            |        |        |      |       |      |      |    X |        |      |      |                |              |      X |
| 205. Check UI                   |            |            |        |        |      |       |      |      |      |        |      |      |                |              |      X |
| 203. Plunder UI                 |          X |            |        |        |      |       |      |      |    X |        |      |      |                |              |      X |
| 202. Movement Highlighting      |            |            |        |        |      |       |      |      |      |        |      |      |                |              |      X |
| 200. ChessBoard UI              |            |            |        |        |      |       |      |      |      |        |      |      |                |              |      X |
| 265. Check displays properly    |          X |            |        |        |      |       |      |      |    X |        |      |      |                |              |      X |
| 286. Remove toString() method   |          X |          X |      X |      X |    X |     X |    X |    X |      |        |      |      |                |              |        |
| 288. ChessBoard-Law of Demeter  |          X |            |        |        |      |       |      |      |      |        |      |      |              X |            X |        |
| 287. ChessPiece-Law of Demeter  |          X |          X |        |        |      |       |      |      |      |        |    X |      |                |              |        |
| 291. Change Move History        |          X |          X |        |        |      |       |      |      |      |        |      |      |                |            X |        |
| 289. Remove turns - ChessBoard  |          X |            |        |        |      |       |      |      |      |        |      |      |                |              |        |
| 295. UI-IllegalMovesCauseCheck  |            |            |        |        |      |       |      |      |      |        |      |      |                |              |      X |
| 297. SimulateMove Retooled      |          X |          X |        |        |      |       |      |      |      |        |      |      |                |              |        |
| 298. Plunder notifies one player|          X |            |        |        |      |       |      |      |      |        |      |      |                |              |      X |
| 287. Move Method Rework         |          X |            |        |        |      |       |      |      |    X |        |      |      |                |              |      X |

  
 ### User Story: I can register or login into the system; User Story: I can start a game
 
| **Row: Tasks, Column: Classes** | GameStatus | Client | InviteResponse | MoveResponse | RegistrationResponse | MatchHistory | Game | Player | User | LoginUI | LoginResponse | StartUI | GameUI | GameResponse | GamesResponse | RegisterUI |
| :------------------------------ | ---------: | -----: | -------------: | -----------: | -------------------: | -----------: | ---: | -----: | ---: | -------:| ------------: | -------:| -----: | ------------:| ------------: | ---------: |
| 169. Register Login User        |            |      X |                |              |                      |              |      |        |    X |         |             X |         |        |              |               |            |
| 190. Login Screen/Validation    |            |      X |                |              |                      |              |      |        |      |       X |             X |         |        |              |               |            |
| 209. Client - Start a game      |            |      X |              X |              |                      |              |    X |      X |    X |       X |             X |       X |      X |            X |               |            |
| 197. Register - Email validation|            |        |                |              |                    X |              |      |        |      |         |               |         |        |              |               |          X |
| 198. Register - PW validation   |            |        |                |              |                    X |              |      |        |      |         |               |         |        |              |               |          X |
| 199. Register - name validation |            |        |                |              |                    X |              |      |        |      |         |               |         |        |              |               |          X |

##### Server Related Tasks and Classes
 
 | **Row: Tasks, Column: Classes** | DatabaseAccessor | InviteRequest | LoginRequest | MoveRequest | RegistrationRequest | Server | ServerWorker | RemoteSSHConnector | GameRequest | GamesRequest |
 | :------------------------------ | ---------------: | ------------: | -----------: | ----------: | ------------------: | -----: | -----------: | -----------------: | ----------: | -----------: |
 | 169. Register Login User        |                X |               |            X |             |                   X |        |            X |                  X |             |              |
 | 128. Create RegistrationRequest |                  |               |              |             |                   X |        |              |                    |             |              |
 | 217. Server on CS Computer      |                  |               |              |             |                     |        |              |                  X |             |              |
 | 214. SSH directly to database   |                  |               |              |             |                     |        |              |                  X |             |              |
 | 184. Game request               |                  |               |              |             |                     |        |            X |                    |           X |              |
 | 185. Games request              |                X |               |              |             |                     |      X |            X |                  X |             |            X |
