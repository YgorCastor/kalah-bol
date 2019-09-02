/*
 *   "THE ULTIMATE BEER-WARE LICENSE" (Revision 6739):
 *   <ygorcastor@gmail.com> wrote this file.  As long as you retain this notice you
 *   can do whatever you want with this stuff. I'm not liable for anything you do
 *   with this code, and if you want to pay me a beer in the unlikely event
 *   of us meeting, be my guest.
 */

package kalah.bol.domain.game.rules.distribution;

import kalah.bol.domain.board.Board;
import kalah.bol.domain.game.GameState;
import kalah.bol.domain.game.Turn;
import kalah.bol.domain.game.VictoryState;
import kalah.bol.domain.game.rules.IdentityRule;
import kalah.bol.domain.game.rules.Rule;
import kalah.bol.domain.game.rules.distribution.pit.PiecesDistributionRule;
import kalah.bol.domain.game.rules.exception.InvalidInitialPitException;
import kalah.bol.domain.player.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Starting movement Rule Specs")
class SelectStartingPitRuleTest {

    @Test
    @DisplayName("Should select a correct pit and take it's pieces")
    void shouldSelectPitCorrectly() {
        // given
        Player firstPlayer = new Player("1");
        Player secondPlayer = new Player("2");
        Board board = new Board(firstPlayer, secondPlayer);
        GameState state = new GameState(board, firstPlayer, secondPlayer, 1, 0, Turn.PLAYER1, VictoryState.ONGOING);
        Rule rule = new SelectStartingPitRule(new IdentityRule());

        // when
        GameState changedState = rule.execute(state);

        // then
        assertThat(changedState).isNotNull();
        assertThat(changedState.getCurrentIndex()).isEqualTo(2);
        assertThat(changedState.getBoard().pit(1).getPieces()).isEqualTo(0);
    }

    @Test
    @DisplayName("Should thrown an exception if the player chooses it's own store")
    void shouldThrowExceptionIfStorePit() {
        // given
        Player firstPlayer = new Player("1");
        Player secondPlayer = new Player("2");
        Board board = new Board(firstPlayer, secondPlayer);
        GameState state = new GameState(board, firstPlayer, secondPlayer, 6, 0, Turn.PLAYER1, VictoryState.ONGOING);
        Rule rule = new SelectStartingPitRule(new IdentityRule());

        // then
        assertThatThrownBy(() -> rule.execute(state))
                .isInstanceOf(InvalidInitialPitException.class);
    }

    @Test
    @DisplayName("Should thrown an exception if the player chooses another player pit")
    void shouldThrowExceptionIfAnotherPlayer() {
        // given
        Player firstPlayer = new Player("1");
        Player secondPlayer = new Player("2");
        Board board = new Board(firstPlayer, secondPlayer);
        GameState state = new GameState(board, firstPlayer, secondPlayer, 7, 0, Turn.PLAYER1, VictoryState.ONGOING);
        Rule rule = new SelectStartingPitRule(new IdentityRule());

        // then
        assertThatThrownBy(() -> rule.execute(state))
                .isInstanceOf(InvalidInitialPitException.class);
    }

    @Test
    @DisplayName("The next rule in the chain should be PiecesDistributionRule")
    void theDefaultNextRuleShouldBeDistributionRule() {
        // given
        Rule rule = new SelectStartingPitRule();

        // then
        assertThat(rule.nextRule()).isInstanceOf(PiecesDistributionRule.class);
    }


}
