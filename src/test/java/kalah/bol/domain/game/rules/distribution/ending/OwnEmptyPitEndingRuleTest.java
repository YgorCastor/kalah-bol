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

@DisplayName("The rule when the final piece it's in an empty pit on the player side")
class OwnEmptyPitEndingRuleTest {

    @Test
    @DisplayName("It Should Steal the opposing pit pieces - Player 1")
    void itShouldStealTheOpposingPits_player1Side() {
        //given: The Ending Pit is Empty
        Player firstPlayer = new Player("1");
        Player secondPlayer = new Player("2");
        Board board = new Board(firstPlayer, secondPlayer);
        board.pit(1).takeAllPieces();
        GameState state = new GameState(board, firstPlayer, 1, 0,
                Turn.PLAYER1,
                VictoryState.ONGOING);
        Rule rule = new OwnEmptyPitEndingRule(new IdentityRule());

        //when: The rule execute
        GameState newState = rule.execute(state);

        //then: The current pit has the pieces of the opposing pit
        assertThat(newState.getCurrentPit().getPieces()).isEqualTo(4);
        assertThat(newState.getBoard().pit(12).getPieces()).isEqualTo(0);

        //given: Another Case of ending Pit
        board = new Board(firstPlayer, secondPlayer);
        board.pit(3).takeAllPieces();
        state = new GameState(board, firstPlayer, 3, 0,
                Turn.PLAYER1,
                VictoryState.ONGOING);

        //when: The rule execute
        newState = rule.execute(state);

        //then: The current pit has the pieces of the opposing pit
        assertThat(newState.getCurrentPit().getPieces()).isEqualTo(4);
        assertThat(newState.getBoard().pit(10).getPieces()).isEqualTo(0);
    }

    @Test
    @DisplayName("It Should Steal the opposing pit pieces - Player 2")
    void itShouldStealTheOpposingPits_player2Side() {
        //given
        Player firstPlayer = new Player("1");
        Player secondPlayer = new Player("2");
        Board board = new Board(firstPlayer, secondPlayer);
        board.pit(12).takeAllPieces();
        GameState state = new GameState(board, secondPlayer, 12, 0,
                Turn.PLAYER2,
                VictoryState.ONGOING);
        Rule rule = new OwnEmptyPitEndingRule(new IdentityRule());

        // when
        GameState newState = rule.execute(state);

        // then
        assertThat(newState.getCurrentPit().getPieces()).isEqualTo(4);
        assertThat(newState.getBoard().pit(1).getPieces()).isEqualTo(0);

        // when
        board = new Board(firstPlayer, secondPlayer);
        board.pit(10).takeAllPieces();
        state = new GameState(board, secondPlayer, 10, 0,
                Turn.PLAYER2,
                VictoryState.ONGOING);

        // when
        newState = rule.execute(state);

        // then
        assertThat(newState.getCurrentPit().getPieces()).isEqualTo(4);
        assertThat(newState.getBoard().pit(3).getPieces()).isEqualTo(0);
    }

}