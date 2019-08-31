/*
 *   "THE ULTIMATE BEER-WARE LICENSE" (Revision 6739):
 *   <ygorcastor@gmail.com> wrote this file.  As long as you retain this notice you
 *   can do whatever you want with this stuff. I'm not liable for anything you do
 *   with this code, and if you want to pay me a beer in the unlikely event
 *   of us meeting, be my guest.
 */

package kalah.bol.domain.game.rules;

import kalah.bol.domain.game.GameState;

import java.util.function.Predicate;

/**
 * The Rule interface provides an contract that can be implemented
 * to add a new rule to the game.
 */
public interface Rule {

    /**
     * Rules that always will be executed.
     */
    Predicate<GameState> ALWAYS = state -> true;

    /**
     * This method must receive a game state which will
     * be mutated after the execution of the rule.
     *
     * @param gameState The current game state
     * @return The game state after the execution of the rule
     */
    GameState execute(GameState gameState);

    /**
     * This method represents the next rule to be executed in the chain,
     * by default it will return the IdentityRule.
     * @return The next rule in the chain.
     */
    default Rule nextRule() {
        return new IdentityRule();
    }

    /**
     * This predicate consists of the pre-conditions that
     * the GameState must have for this rule to be executed.
     *
     * By default, the rule will always be executed.
     *
     * @return Pre-Condition of the rule
     */
    default Predicate<GameState> condition() {
        return ALWAYS;
    }

}
