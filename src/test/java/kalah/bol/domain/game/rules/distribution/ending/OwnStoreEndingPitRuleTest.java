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

package kalah.bol.domain.game.rules.distribution.ending;

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

@DisplayName("The rule when the final piece is at the own store pit")
class OwnStoreEndingPitRuleTest {

    @Test
    @DisplayName("It should keep the bonus turn")
    void shouldAddBonusTurn() {
        //given
        Player firstPlayer = new Player("1");
        Player secondPlayer = new Player("2");
        Board board = new Board(firstPlayer, secondPlayer);
        board.pit(0).takeAllPieces();
        GameState state = new GameState(board , firstPlayer, 6, 0,
                Turn.PLAYER1,
                VictoryState.ONGOING);
        Rule rule = new OwnStoreEndingPitRule(new IdentityRule());

        // when
        GameState newState = rule.execute(state);

        // then
        assertThat(newState.getRemainingPieces()).isEqualTo(0);
        assertThat(newState.getCurrentTurn()).isEqualTo(Turn.PLAYER1);
    }

    @Test
    @DisplayName("The next rule by default should be the OwnEmptyPitEndingRule")
    void validateNextRule() {
        // given
        Rule rule = new OwnStoreEndingPitRule();

        //then
        assertThat(rule.nextRule()).isInstanceOf(OwnEmptyPitEndingRule.class);
    }

}