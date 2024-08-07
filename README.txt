2048
----
For my project, I just recreated 2048. Just like the original game, you use the arrow keys to move the tiles around
the board, and when two tiles with the same number collide, they are combined. If you get a 2048 tile, you win, but
if you can no longer make any moves, you lose. The game displays your current score, and also allows you to reset the
board, undo any of your moves, or save your game to resume the next time you run the program.

Class Overview
--------------
Game: Main class that runs the game. It creates a GameManager object, creates the start frame and main game frame with
the GameManager object in it, and creates the reset, undo, and save buttons and adds event listeners to them.

GameManager: Manages the game state. It creates a GameBoard object, keeps track of all the moves made, and calls
the correct methods on the GameBoard when the arrow keys are pressed. It also has the methods to save and load the
progress, reset the board, and draw the board on the screen.

GameBoard: Contains the score and a 2D array of integers that represents the board. It has methods to move the tiles
in all 4 directions and update the score. It also can check if the game is won or lost.

Design Concepts
---------------
2D Arrays: The board is represented as a 2D array of integers. Each integer represents the value of the tile at that
position. A 2D array makes the most sense to use because the board is always a 4x4 grid.

Collections: The list of moves are stored in a linked list. Each item in the list is a GameBoard object, so it stores
both the score and the 2D array of the board. A linked list is used because it is easy to add items to the end and
remove them from the end when the undo function is used.

File I/O: The game can be saved by writing all the contents of the linked list of moves to a text file using a
BufferedWriter. When the game is started, if the text file already exists, it reads the file using a BufferedReader
and starts the game from the last saved state.

JUnit Testable Component: The GameTest class contains at least 5 JUnit tests that test the functionality of the
GameBoard class.