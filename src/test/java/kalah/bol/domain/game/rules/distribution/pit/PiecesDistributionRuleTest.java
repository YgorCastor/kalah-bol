/*
 *   "THE ULTIMATE BEER-WARE LICENSE" (Revision 6739):
 *   <ygorcastor@gmail.com> wrote this file.  As long as you retain this notice you
 *   can do whatever you want with this stuff. I'm not liable for anything you do
 *   with this code, and if you want to pay me a beer in the unlikely event
 *   of us meeting, be my guest.
 */

package kalah.bol.domain.game.rules.distribution.pit;

import kalah.bol.domain.board.Board;
import kalah.bol.domain.game.GameState;
import kalah.bol.domain.game.Turn;
import kalah.bol.domain.game.VictoryState;
import kalah.bol.domain.game.rules.IdentityRule;
import kalah.bol.domain.game.rules.Rule;
import kalah.bol.domain.game.rules.distribution.ending.PitEndingRule;
import kalah.bol.domain.player.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Pieces Distribution Spec")
class PiecesDistributionRuleTest {

    @Test
    @DisplayName("The distribution of pieces must be correct")
    void shouldIterateTheNumberOfPieces() {
        //given
        Player firstPlayer = new Player("1");
        Player secondPlayer = new Player("2");
        Board board = new Board(firstPlayer, secondPlayer);
        board.pit(0).takeAllPieces();
        GameState state = new GameState(board, firstPlayer, secondPlayer, 0, 20,
                Turn.PLAYER1,
                VictoryState.ONGOING);
        Rule rule = new PiecesDistributionRule(new SkipPieceToPitRule(), new IdentityRule());

        //when
        GameState newState = rule.execute(state);

        //then
        assertThat(newState).isNotNull();
        assertThat(newState.getBoard().pit(0).getPieces()).isEqualTo(2); // The First Player Pit has 2 Pieces
        assertThat(newState.getBoard().pit(1).getPieces()).isEqualTo(6); // The First Player Second pit has 6 Pieces
        assertThat(newState.getBoard().pit(6).getPieces()).isEqualTo(2); // The First Player Store has 2 Pieces
        assertThat(newState.getBoard().pit(7).getPieces()).isEqualTo(5); // The Second Player First Pit has 5 Pieces
        assertThat(newState.getBoard().pit(13).getPieces()).isEqualTo(0); // The Second Player Store as 0 Pieces
    }

    @Test
    @DisplayName("The next Default Rule in the chain is PitEndingRule")
    void verifyDefaultRules() {
        // given
        Rule rule = new PiecesDistributionRule();

        // then
        assertThat(rule.nextRule()).isInstanceOf(PitEndingRule.class);
    }

}