# Connect-Four-Game-Board

This is a JavaFX application.
This application creates a Connect Four Board Game.

ConnectFourBoard: The model class that contains the representation of the grid of
discs, along with statistics and the status of the game.
Observer: An interface the view implements so that it may receive updates from the
model.

**Implementation**
The title of your window should be ConnectFourGUI.
❼ Your window should contain:
– An area where the grid of discs are displayed.
✯ Clicking on any button in a column will cause a disc to be dropped into it
(as long as the column is not full and the game is not over).
✯ Images must be used for the placed discs. You are free to change the provided
images but please keep them appropriate.
– Three pieces of information should be displayed and updated automatically as
the game is being played:
✯ The number of moves made in the game, which corresponds to the number
of discs played.
✯ The current player to take the next turn.
✯ The status of the game, which is NOT OVER while the game is being played,
and either P1 WINS, P2 WINS, or TIE when the game has ended.
This information should not be editable by the user.
❼ When the game is over, the GUI should stay up in a disabled state until the user closes
it.
