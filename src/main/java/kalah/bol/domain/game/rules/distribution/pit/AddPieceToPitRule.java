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

/**
 * This is the default rule for a Piece, it adds
 * a single piece to a pit and deduces one piece from
 * the hand of the player.
 */
class AddPieceToPitRule implements PitRule {

    @Override
    public GameState execute(GameState state) {
        if(condition().test(state)) {
            state.getCurrentPit().addPiece();
            int nextIndex = state.getCurrentIndex() + 1;
            int remainingPieces = state.getRemainingPieces() - 1;
            return new GameState(
                    state.getBoard(),
                    state.getCurrentPlayer(),
                    nextIndex,
                    remainingPieces,
                    state.getCurrentTurn(),
                    state.getCurrentVictory()
            );
        }
        return nextRule().execute(state);
    }

}
