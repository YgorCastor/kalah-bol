/*
 *   "THE ULTIMATE BEER-WARE LICENSE" (Revision 6739):
 *   <ygorcastor@gmail.com> wrote this file.  As long as you retain this notice you
 *   can do whatever you want with this stuff. I'm not liable for anything you do
 *   with this code, and if you want to pay me a beer in the unlikely event
 *   of us meeting, be my guest.
 */

package kalah.bol.domain.game.rules.distribution.ending;

import kalah.bol.domain.game.GameState;
import kalah.bol.domain.game.rules.PitRule;
import kalah.bol.domain.game.rules.Rule;
import kalah.bol.domain.player.Player;

import java.util.function.Predicate;

/**
 * This rule is executed when the last piece is at the
 * own store pit, in that case, the player wins an new turn.
 */
class OwnStoreEndingPitRule implements PitRule {

    private final Rule nextRule;

    OwnStoreEndingPitRule() {
        this.nextRule = new OwnEmptyPitEndingRule();
    }

    OwnStoreEndingPitRule(Rule nextRule) {
        this.nextRule = nextRule;
    }

    @Override
    public GameState execute(GameState gameState) {
        if(condition().test(gameState)) {
            gameState = new GameState(
                    gameState.getBoard(),
                    gameState.getCurrentPlayer(),
                    gameState.getCurrentIndex(),
                    gameState.getRemainingPieces(),
                    gameState.getCurrentTurn(),
                    gameState.getCurrentVictory());
        }
        return nextRule().execute(gameState);
    }

    @Override
    public Predicate<GameState> condition() {
        return state -> {
            Player currentPlayer = state.getCurrentPlayer();
            Player pitOwner = state.getCurrentPit().getPlayer();
            return state.getRemainingPieces() == 0 && pitOwner.equals(currentPlayer) && state.getCurrentPit().isStore();
        };
    }

    @Override
    public Rule nextRule() {
        return nextRule;
    }
}
