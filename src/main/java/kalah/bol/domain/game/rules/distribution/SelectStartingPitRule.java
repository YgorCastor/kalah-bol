/*
 *   "THE ULTIMATE BEER-WARE LICENSE" (Revision 6739):
 *   <ygorcastor@gmail.com> wrote this file.  As long as you retain this notice you
 *   can do whatever you want with this stuff. I'm not liable for anything you do
 *   with this code, and if you want to pay me a beer in the unlikely event
 *   of us meeting, be my guest.
 */

package kalah.bol.domain.game.rules.distribution;

import kalah.bol.domain.board.Pit;
import kalah.bol.domain.game.GameState;
import kalah.bol.domain.game.rules.Rule;
import kalah.bol.domain.game.rules.distribution.pit.PiecesDistributionRule;
import kalah.bol.domain.game.rules.exception.InvalidInitialPitException;

/**
 * The first Rule in the Chain, this rule is responsible for
 * check if it's a allowable Pit to start an movement, if so,
 * it will empty the selected pit and generate a new state with
 * pieces to distribute through the Pits in the board.
 */
public class SelectStartingPitRule implements Rule {

    private final Rule nextRule;

    /**
     * Creates the Rule, by default the next rule in the Chain
     * if the PiecesDistributionRule.
     */
    public SelectStartingPitRule() {
        this.nextRule = new PiecesDistributionRule();
    }

    public SelectStartingPitRule(Rule nextRule) {
        this.nextRule = nextRule;
    }

    @Override
    public GameState execute(GameState state) {
        Pit currentPit = state.getCurrentPit();

        if (!currentPit.getPlayer().equals(state.getCurrentPlayer()) || currentPit.isStore()) {
            throw new InvalidInitialPitException(currentPit);
        }

        int numPieces = currentPit.takeAllPieces();
        int nextIndex = state.getCurrentIndex() + 1;
        GameState changedState = new GameState(
                state.getBoard(),
                state.getCurrentPlayer(),
                nextIndex,
                numPieces,
                state.getCurrentTurn(),
                state.getCurrentVictory()
        );
        return nextRule().execute(changedState);
    }

    @Override
    public Rule nextRule() {
        return nextRule;
    }

}
