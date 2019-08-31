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

import java.util.function.Predicate;

/**
 * This is an utilitary rule to be easer to identify
 * the entry-point of the ending pit rules, there are
 * several rules for where the last piece is put.
 */
public class PitEndingRule implements PitRule {

    @Override
    public GameState execute(GameState gameState) {
        return nextRule().execute(gameState);
    }

    @Override
    public Predicate<GameState> condition() {
        return ALWAYS;
    }

    @Override
    public Rule nextRule() {
        return new OwnStoreEndingPitRule();
    }

}
