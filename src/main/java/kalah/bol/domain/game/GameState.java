/*
 *   "THE ULTIMATE BEER-WARE LICENSE" (Revision 6739):
 *   <ygorcastor@gmail.com> wrote this file.  As long as you retain this notice you
 *   can do whatever you want with this stuff. I'm not liable for anything you do
 *   with this code, and if you want to pay me a beer in the unlikely event
 *   of us meeting, be my guest.
 */

package kalah.bol.domain.game;

import com.google.gson.Gson;
import io.micronaut.core.annotation.Introspected;
import kalah.bol.domain.board.Board;
import kalah.bol.domain.board.Pit;
import kalah.bol.domain.player.Player;

import java.util.Objects;

/**
 *  The GameState is an immutable state from a game
 */
@Introspected
public class GameState {

    private final Board board;
    private final Player currentPlayer;
    private final Integer currentIndex;
    private final Integer remainingPieces;
    private final Turn turn;
    private final VictoryState victoryState;

    /**
     * Instantiates a new Game state.
     *
     * @param board           The current board state
     * @param currentPlayer   The current player
     * @param currentIndex    the current index
     * @param remainingPieces the remaining pieces
     * @param turn            the turn
     * @param victoryState    the victory state
     */
    public GameState(
            Board board,
            Player currentPlayer,
            Integer currentIndex,
            Integer remainingPieces,
            Turn turn,
            VictoryState victoryState
    ) {
        this.board = board;
        this.currentPlayer = currentPlayer;
        this.currentIndex = currentIndex;
        this.remainingPieces = remainingPieces;
        this.turn = turn;
        this.victoryState = victoryState;
    }

    private GameState(GameState state, int fromIndex) {
        this.board = state.board;
        this.currentPlayer = state.currentPlayer;
        this.currentIndex = fromIndex;
        this.remainingPieces = state.remainingPieces;
        this.turn = state.turn;
        this.victoryState = state.victoryState;
    }

    /**
     * @return The actual board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @return The player execution the actions
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * @return The current index at the board
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * @return The remaining pieces at the player hand
     */
    public int getRemainingPieces() {
        return remainingPieces;
    }

    /**
     * @return The current Pit
     */
    public Pit getCurrentPit() {
        return board.pit(getCurrentIndex());
    }

    /**
     * @return The Current Turn
     */
    public Turn getCurrentTurn() {
        return turn;
    }

    /**
     * @return The actual Victory State of the Game
     */
    public VictoryState getCurrentVictory() {
        return victoryState;
    }

    /**
     * Creates a new GameState with on with the
     * starting pit index to execute the movements
     *
     * @param fromIndex The Starting Index
     * @return The new GameState
     */
    public GameState newMovement(int fromIndex) {
        return new GameState(clone(this), fromIndex);
    }

    /**
     * Deeply clones the GameState to avoid an
     * inconstant GameState in the case of a Rule
     * failure
     *
     * @param state The Game State
     * @return A deeply cloned GameState
     */
    private static GameState clone(GameState state) {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(state), GameState.class);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameState gameState = (GameState) o;
        return Objects.equals(board, gameState.board) &&
                Objects.equals(currentPlayer, gameState.currentPlayer) &&
                Objects.equals(currentIndex, gameState.currentIndex) &&
                Objects.equals(remainingPieces, gameState.remainingPieces) &&
                turn == gameState.turn &&
                victoryState == gameState.victoryState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, currentPlayer, currentIndex, remainingPieces, turn, victoryState);
    }

    @Override
    public String toString() {
        return "GameState{" +
                "board=" + board +
                ", currentPlayer=" + currentPlayer +
                ", currentIndex=" + currentIndex +
                ", remainingPieces=" + remainingPieces +
                ", turn=" + turn +
                ", victoryState=" + victoryState +
                '}';
    }
}
