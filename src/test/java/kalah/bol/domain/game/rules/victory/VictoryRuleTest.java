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

package kalah.bol.domain.game.rules.victory;

import kalah.bol.domain.board.Board;
import kalah.bol.domain.game.GameState;
import kalah.bol.domain.game.Turn;
import kalah.bol.domain.game.VictoryState;
import kalah.bol.domain.game.rules.Rule;
import kalah.bol.domain.player.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DisplayName("The Victory rule specification")
class VictoryRuleTest {

    @Test
    @DisplayName("The first player wins the game")
    void shouldWinPlayer1() {
        //given: The first player board has no pieces.
        Player firstPlayer = new Player("1");
        Player secondPlayer = new Player("2");
        Board board = new Board(firstPlayer, secondPlayer);
        board.clearBoard();
        board.pit(6).addPieces(10);
        board.pit(13).addPieces(2);
        board.pit(11).addPieces(2);
        GameState state = new GameState(board, secondPlayer, firstPlayer, 1, 0,
                Turn.PLAYER1,
                VictoryState.ONGOING);
        Rule rule = new VictoryRule();

        //when
        GameState newState = rule.execute(state);

        //then
        assertThat(newState.getCurrentVictory()).isEqualTo(VictoryState.PLAYER1);
    }

    @Test
    @DisplayName("The second player wins the game")
    void shouldWinPlayer2() {
        //given: The first player board has no pieces.
        Player firstPlayer = new Player("1");
        Player secondPlayer = new Player("2");
        Board board = new Board(firstPlayer, secondPlayer);
        board.clearBoard();
        board.pit(6).addPieces(10);
        board.pit(13).addPieces(2);
        board.pit(11).addPieces(13);
        GameState state = new GameState(board, secondPlayer, firstPlayer, 1, 0,
                Turn.PLAYER1,
                VictoryState.ONGOING);
        Rule rule = new VictoryRule();

        //when
        GameState newState = rule.execute(state);

        //then
        assertThat(newState.getCurrentVictory()).isEqualTo(VictoryState.PLAYER2);
    }

}