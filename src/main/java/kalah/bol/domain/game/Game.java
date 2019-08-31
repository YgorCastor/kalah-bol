/*
 *   "THE ULTIMATE BEER-WARE LICENSE" (Revision 6739):
 *   <ygorcastor@gmail.com> wrote this file.  As long as you retain this notice you
 *   can do whatever you want with this stuff. I'm not liable for anything you do
 *   with this code, and if you want to pay me a beer in the unlikely event
 *   of us meeting, be my guest.
 */

package kalah.bol.domain.game;

import io.micronaut.core.annotation.Introspected;
import kalah.bol.domain.board.Board;
import kalah.bol.domain.game.exception.NotPlayerTurnException;
import kalah.bol.domain.game.exception.TurnExecutionException;
import kalah.bol.domain.game.rules.Rule;
import kalah.bol.domain.game.rules.distribution.SelectStartingPitRule;
import kalah.bol.domain.player.Player;

import java.util.concurrent.Semaphore;

/**
 * The Game Object represents an actual Kalah game,
 * it's responsible to receive an ActionCommand and
 * executes the rules of the game.
 */
@Introspected
public class Game {

    private GameState state;
    private Rule go;
    private final Semaphore inTurn;

    /**
     * Instantiates a new Game.
     *
     * @param firstPlayer  the first player
     * @param secondPlayer the second player
     */
    public Game(Player firstPlayer, Player secondPlayer) {
        Board board = new Board(firstPlayer, secondPlayer);
        this.state = new GameState(board, firstPlayer, -1, 0, Turn.PLAYER1, VictoryState.ONGOING);
        this.go = new SelectStartingPitRule();
        this.inTurn = new Semaphore(1);
    }

    /**
     * Receives an Action object and executes the player play.
     *
     * @param action The player play
     * @return The new state of the game
     * @throws NotPlayerTurnException If it's not the players turn
     * @throws TurnExecutionException If there's already a turn in execution
     */
    public GameState play(Action action) {
        try {
            inTurn.acquire();
            if (!action.getPlayer().equals(state.getCurrentPlayer())) {
                throw new NotPlayerTurnException(action.getPlayer());
            }
            return go.execute(state.newMovement(action.getPitIndex()));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new TurnExecutionException();
        } finally {
            inTurn.release();
        }
    }

    public GameState getState() {
        return state;
    }
}
