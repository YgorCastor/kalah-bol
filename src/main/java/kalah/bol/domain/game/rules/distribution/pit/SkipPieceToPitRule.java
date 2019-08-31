/*
 *   "THE ULTIMATE BEER-WARE LICENSE" (Revision 6739):
 *   <ygorcastor@gmail.com> wrote this file.  As long as you retain this notice you
 *   can do whatever you want with this stuff. I'm not liable for anything you do
 *   with this code, and if you want to pay me a beer in the unlikely event
 *   of us meeting, be my guest.
 */

package kalah.bol.domain.game.rules.distribution.pit;

import kalah.bol.domain.game.GameState;
import kalah.bol.domain.game.rules.PitRule;
import kalah.bol.domain.game.rules.Rule;
import kalah.bol.domain.player.Player;

import java.util.function.Predicate;

/**
 * The SkipPiece rule happens when the the player reaches the opposite
 * player store, in this case it's not allowed to put a piece there.
 */
class SkipPieceToPitRule implements PitRule {

    private final Rule nextRule;

    public SkipPieceToPitRule() {
        this.nextRule = new AddPieceToPitRule();
    }

    public SkipPieceToPitRule(Rule nextRule) {
        this.nextRule = nextRule;
    }

    @Override
    public GameState execute(GameState state) {
        if(condition().test(state)){
            int nextIndex = state.getCurrentIndex() + 1;
            return new GameState(
                    state.getBoard(),
                    state.getCurrentPlayer(),
                    nextIndex,
                    state.getRemainingPieces(),
                    state.getCurrentTurn(),
                    state.getCurrentVictory()
            );
        }
        return nextRule().execute(state);
    }

    @Override
    public Predicate<GameState> condition() {
        return state -> {
            Player currentPlayer = state.getCurrentPlayer();
            Player pitOfPlayer = state.getCurrentPit().getPlayer();
            return !pitOfPlayer.equals(currentPlayer) && state.getCurrentPit().isStore();
        };
    }

    @Override
    public Rule nextRule() {
        return nextRule;
    }
}
