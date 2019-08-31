/*
 *   "THE ULTIMATE BEER-WARE LICENSE" (Revision 6739):
 *   <ygorcastor@gmail.com> wrote this file.  As long as you retain this notice you
 *   can do whatever you want with this stuff. I'm not liable for anything you do
 *   with this code, and if you want to pay me a beer in the unlikely event
 *   of us meeting, be my guest.
 */

package kalah.bol.domain.game.rules.distribution.ending;

import kalah.bol.domain.board.Pit;
import kalah.bol.domain.game.GameState;
import kalah.bol.domain.game.rules.PitRule;
import kalah.bol.domain.game.rules.Rule;
import kalah.bol.domain.player.Player;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This rule is executed when the last piece is in an empty
 * pit on the player side, in that case, all the pieces
 * on the opposing side pit are removed and added to the player
 * pit.
 */
class OwnEmptyPitEndingRule implements PitRule {

    private final Rule nextRule;

    OwnEmptyPitEndingRule() {
        this.nextRule = new CommonPitEndingRule();
    }

    OwnEmptyPitEndingRule(Rule nextRule) {
        this.nextRule = nextRule;
    }

    @Override
    public GameState execute(GameState gameState) {
        if (condition().test(gameState)) {
            Pit currentPit = gameState.getCurrentPit();
            Pit opposingPit = opposingPit(gameState);
            int stealPieces = opposingPit.takeAllPieces();
            currentPit.addPieces(stealPieces);
        }
        return nextRule().execute(gameState);
    }

    private Pit opposingPit(GameState state) {
        int pitIndex = state.getCurrentIndex() % 7;
        Player currentPlayer = state.getCurrentPlayer();
        List<Pit> opposingPits = state.getBoard()
                .getPits()
                .stream()
                .filter(p -> !p.getPlayer().equals(currentPlayer))
                .collect(Collectors.toList());
        Collections.reverse(opposingPits);
        return opposingPits.get(pitIndex);
    }

    @Override
    public Predicate<GameState> condition() {
        return state -> {
            Player currentPlayer = state.getCurrentPlayer();
            Player pitOwner = state.getCurrentPit().getPlayer();
            return pitOwner.equals(currentPlayer) &&
                    state.getCurrentPit().getPieces() == 0 &&
                    !state.getCurrentPit().isStore();
        };
    }


    @Override
    public Rule nextRule() {
        return nextRule;
    }
}
