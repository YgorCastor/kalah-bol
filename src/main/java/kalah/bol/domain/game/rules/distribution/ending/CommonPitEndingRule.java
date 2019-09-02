/*
 *   "THE ULTIMATE BEER-WARE LICENSE" (Revision 6739):
 *   <ygorcastor@gmail.com> wrote this file.  As long as you retain this notice you
 *   can do whatever you want with this stuff. I'm not liable for anything you do
 *   with this code, and if you want to pay me a beer in the unlikely event
 *   of us meeting, be my guest.
 */

package kalah.bol.domain.game.rules.distribution.ending;

import kalah.bol.domain.game.GameState;
import kalah.bol.domain.game.Turn;
import kalah.bol.domain.game.rules.PitRule;
import kalah.bol.domain.game.rules.Rule;
import kalah.bol.domain.game.rules.victory.VictoryRule;

import java.util.function.Predicate;

/**
 * The default ending rule, in this case the turn is switched
 * for the next player.
 */
class CommonPitEndingRule implements PitRule {

    private final Rule nextRule;

    public CommonPitEndingRule() {
        this.nextRule = new VictoryRule();
    }

    public CommonPitEndingRule(Rule nextRule) {
        this.nextRule = nextRule;
    }

    @Override
    public GameState execute(GameState gameState) {
        if(condition().test(gameState)) {
            Turn turn = gameState.getCurrentTurn();
            turn = turn == Turn.PLAYER1 ? Turn.PLAYER2 : Turn.PLAYER1;
            gameState = new GameState(
                    gameState.getBoard(),
                    gameState.getOtherPlayer(),
                    gameState.getCurrentPlayer(),
                    gameState.getCurrentIndex(),
                    gameState.getRemainingPieces(),
                    turn,
                    gameState.getCurrentVictory());
        }
        return nextRule().execute(gameState);
    }

    @Override
    public Predicate<GameState> condition() {
        return state -> state.getRemainingPieces() == 0 && !state.getCurrentPit().isStore();
    }

    @Override
    public Rule nextRule() {
        return nextRule;
    }

}
