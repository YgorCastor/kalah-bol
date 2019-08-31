/*
 *   "THE ULTIMATE BEER-WARE LICENSE" (Revision 6739):
 *   <ygorcastor@gmail.com> wrote this file.  As long as you retain this notice you
 *   can do whatever you want with this stuff. I'm not liable for anything you do
 *   with this code, and if you want to pay me a beer in the unlikely event
 *   of us meeting, be my guest.
 */

package kalah.bol.domain.game.rules.victory;

import kalah.bol.domain.game.GameState;
import kalah.bol.domain.game.VictoryState;
import kalah.bol.domain.game.rules.Rule;

import java.util.function.Predicate;

/**
 * The Victory Rule validates the winning cases, if
 * the victory is achieved, then the state is changed
 * to the Victory for the winner.
 */
public class VictoryRule implements Rule {

    @Override
    public GameState execute(GameState gameState) {
        if (condition().test(gameState)) {
            Integer firstPlayerScore = gameState.getBoard().finishFirstPlayerBoard().getPieces();
            Integer secondPlayerScore = gameState.getBoard().finishSecondPlayerBoard().getPieces();
            VictoryState victory = firstPlayerScore > secondPlayerScore ? VictoryState.PLAYER1 : VictoryState.PLAYER2;
            return new GameState(
                    gameState.getBoard(),
                    gameState.getCurrentPlayer(),
                    gameState.getCurrentIndex(),
                    gameState.getRemainingPieces(),
                    gameState.getCurrentTurn(),
                    victory
            );
        }
        return nextRule().execute(gameState);
    }

    @Override
    public Predicate<GameState> condition() {
        return gameState -> {
            int numPiecesBoardFirstPlayer = gameState.getBoard().firstPlayerNumPiecesInGame();
            int numPiecesBoardSecondPlayer = gameState.getBoard().firstPlayerNumPiecesInGame();
            return numPiecesBoardFirstPlayer == 0 || numPiecesBoardSecondPlayer == 0;
        };
    }
}
