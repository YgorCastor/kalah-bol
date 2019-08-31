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
import kalah.bol.domain.game.rules.victory.VictoryRule;
import kalah.bol.domain.player.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DisplayName("Common pit ending Spec")
class CommonPitEndingRuleTest {

    @Test
    @DisplayName("It should change the turns")
    void itShouldChangeTheTurn() {
        //given: The final piece is in a common pit.
        Player firstPlayer = new Player("1");
        Player secondPlayer = new Player("2");
        Board board = new Board(firstPlayer, secondPlayer);
        GameState state = new GameState(board, secondPlayer, 2, 0,
                Turn.PLAYER1,
                VictoryState.ONGOING);
        Rule rule = new CommonPitEndingRule(new IdentityRule());

        //when: The Rule executes
        GameState newState = rule.execute(state);

        //then: The turn changes
        assertThat(newState.getCurrentTurn()).isEqualTo(Turn.PLAYER2);
    }

    @Test
    @DisplayName("It should skip the rule if it not applies")
    void itShouldSkipTheRuleIfNotApplies() {
        //given: The final piece is in a common pit.
        Player firstPlayer = new Player("1");
        Player secondPlayer = new Player("2");
        Board board = new Board(firstPlayer, secondPlayer);
        GameState state = new GameState(board, secondPlayer, 6, 0,
                Turn.PLAYER1,
                VictoryState.ONGOING);
        Rule rule = new CommonPitEndingRule(new IdentityRule());

        //when: The Rule executes
        GameState newState = rule.execute(state);

        //then: The turn stays the same
        assertThat(newState.getCurrentTurn()).isEqualTo(Turn.PLAYER1);
    }

    @Test
    @DisplayName("The next rule should be the Victory rule")
    void validateNextRule() {
        //given:
        Rule rule = new CommonPitEndingRule();

        //then:
        assertThat(rule.nextRule()).isInstanceOf(VictoryRule.class);
    }

}