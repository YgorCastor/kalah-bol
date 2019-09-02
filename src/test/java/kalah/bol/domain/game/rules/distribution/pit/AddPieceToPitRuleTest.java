/*
 *   "THE ULTIMATE BEER-WARE LICENSE" (Revision 6739):
 *   <ygorcastor@gmail.com> wrote this file.  As long as you retain this notice you
 *   can do whatever you want with this stuff. I'm not liable for anything you do
 *   with this code, and if you want to pay me a beer in the unlikely event
 *   of us meeting, be my guest.
 */

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
import kalah.bol.domain.player.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("The Add Piece Rule Spec")
class AddPieceToPitRuleTest {

    @Test
    @DisplayName("It should execute the rule correctly")
    void shouldExecuteTheRuleCorrectly() {
        //given
        Player firstPlayer = new Player("1");
        Player secondPlayer = new Player("2");
        Board board = new Board(firstPlayer, secondPlayer);
        board.pit(0).takeAllPieces();
        GameState state = new GameState(board, firstPlayer, secondPlayer, 1, 2,
                Turn.PLAYER1,
                VictoryState.ONGOING);
        Rule rule = new AddPieceToPitRule();

        // when
        GameState newState = rule.execute(state);

        // then
        assertThat(newState.getRemainingPieces()).isEqualTo(1);
        assertThat(newState.getCurrentIndex()).isEqualTo(2);
        assertThat(newState.getBoard().pit(1).getPieces()).isEqualTo(5);
    }

    @Test
    @DisplayName("The next Default Rule in the chain is IdentityRule")
    void verifyDefaultRules() {
        // given
        Rule rule = new AddPieceToPitRule();

        // then
        assertThat(rule.nextRule()).isInstanceOf(IdentityRule.class);
    }

}