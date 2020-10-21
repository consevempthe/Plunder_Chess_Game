Updated as of 10-20-20

### Game Logic - tasks and classes

| **Row: Tasks, Column: Classes** | ChessBoard | ChessPiece | Knight | Bishop | Rook | Queen | King | Pawn | Game | Player | Vest | Move | Piece Movement | Move History |
| :------------------------------ | ---------: | ---------: | -----: | -----: | ---: | ----: | ---: | ---: | ---: | -----: | ---: | ---: | -------------: | -----------: |
| 95. Create ChessPiece Class     |            |          X |        |        |      |       |      |      |      |        |      |      |                |              |
| 105. Create ChessBoard Class    |         X  |            |        |        |      |       |      |      |      |        |      |      |                |              |   
| 118. ChessBoard toString()      |         X  |            |        |        |      |       |      |      |      |        |      |      |                |              |
| 96. Knight Class                |            |            |     X  |        |      |       |      |      |      |        |      |      |              X |              |
| 97. Bishop Class                |            |            |        |      X |      |       |      |      |      |        |      |      |              X |              |
| 99. Rook Class                  |            |            |        |        |    X |       |      |      |      |        |      |      |              X |              |   
| 102. Queen Class                |            |            |        |        |      |     X |      |      |      |        |      |      |              X |              |   
| 100. King Class                 |            |            |        |        |      |       |    X |      |      |        |      |      |              X |              |   
| 104. Implement Castling         |          X |            |        |        |      |       |    X |      |      |        |      |      |              X |            X |   
| 98. Pawn Class                  |            |            |        |        |      |       |      |    X |      |        |      |      |              X |              |   
| 103. Pawn En Passant            |            |            |        |        |      |       |      |    X |      |        |      |      |              X |            X |   
| 108. Game Check Logic           |          X |            |        |        |      |       |    X |      |      |      X |      |      |                |            X |   
| 121. Implement Player Class     |            |            |        |        |      |       |      |      |      |      X |      |      |                |              |   
| 107. Implement Plundering       |          X |          X |      X |      X |    X |     X |    X |    X |      |        |    X |    X |                |              |     
| 109. Game Checkmate Logic       |          X |            |        |        |      |       |      |      |    X |      X |      |      |                |            X |   
| 132. Implement Vest Class       |            |          X |        |        |      |       |      |      |      |        |    X |      |                |              |
| 150. Implement Movement Class   |            |            |      X |      X |    X |     X |    X |    X |      |        |      |    X |              X |              |   
| 123. Game Draw Logic            |          X |            |        |        |      |       |      |      |      |        |      |      |                |            X |   
| 123. Pawn Upgrade               |          X |            |        |        |      |       |      |    X |      |        |      |      |                |              |   
| 107. Implement Game Class       |          X |            |        |        |      |       |      |      |    X |        |      |      |                |              |   
|                                 |            |            |        |        |      |       |      |      |      |        |      |      |                |              |   
  
 ### Client/Game UI - tasks and classes
 
| **Row: Tasks, Column: Classes** | GameStatus | Client | InviteResponse | MoveResponse | RegistrationResponse | MatchHistory | Game | Player | User | LoginUI | LoginResponse |
| :------------------------------ | ---------: | -----: | -------------: | -----------: | -------------------: | -----------: | ---: | -----: | ---: | -------:| ------------: |
| 169. **Register Login User**    |            |      X |                |              |                      |              |      |        |    X |         |       X       |
| 190. **Login Screen/Validation**|            |    X   |                |              |                      |              |      |        |      |   X     |       X       |
|                                 |            |        |                |              |                      |              |      |        |      |         |

 ### Server - tasks and classes
 
 | **Row: Tasks, Column: Classes** | DatabaseAccessor | InviteRequest | LoginRequest | MoveRequest | RegistrationRequest | Server | ServerWorker | 
 | :------------------------------ | ---------------: | ------------: | -----------: | ----------: | ------------------: | -----: | -----------: | 
 | 169. **Register Login User**    |                X |               |            X |             |                   X |        |            X |        
 | 128. Create RegistrationRequest |                  |               |              |             |                   X |        |              |                    
 |                                 |                  |               |              |             |                     |        |              |   
