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
import kalah.bol.domain.game.rules.distribution.ending.PitEndingRule;

/**
 * The pieces distribution rule consists of
 * iterate the amount of Pieces in the hard of the player
 * through the pits in the board.
 *
 * Since there are different rules for some special pits,
 * there's a second class of rules to be applied.
 */
public class PiecesDistributionRule implements Rule {

    private final PitRule pitRule;
    private final Rule nextRule;

    /**
     * Creates a new DistributionRule,
     * the default first rule for the pit is to Skip the pit.
     *
     * The default next rule is the ending pit rule chain.
     */
    public PiecesDistributionRule() {
        this.pitRule = new SkipPieceToPitRule();
        this.nextRule = new PitEndingRule();
    }

    public PiecesDistributionRule(PitRule pitRule, Rule nextRule) {
        this.pitRule = pitRule;
        this.nextRule = nextRule;
    }

    @Override
    public GameState execute(GameState state) {
        while (state.getRemainingPieces() != 0) {
            state = this.pitRule.execute(state);
        }
        return nextRule().execute(state);
    }

    @Override
    public Rule nextRule() {
        return nextRule;
    }

}
