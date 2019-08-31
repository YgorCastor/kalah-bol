/*
 *   "THE ULTIMATE BEER-WARE LICENSE" (Revision 6739):
 *   <ygorcastor@gmail.com> wrote this file.  As long as you retain this notice you
 *   can do whatever you want with this stuff. I'm not liable for anything you do
 *   with this code, and if you want to pay me a beer in the unlikely event
 *   of us meeting, be my guest.
 */

package kalah.bol.domain.board;

import kalah.bol.domain.player.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Board creation Spec")
class BoardTest {

    @Test
    @DisplayName("Should create a valid kalah default board")
    void isShouldCreateACorrectBoard() {
        //given: You Create an board with two players
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Board board = new Board(player1, player2);

        //then: The board should have 14 pits and each player should have 6 Pits
        assertThat(board.getPits()).hasSize(14);
        assertThat(board.firstPlayerPits()).hasSize(6);
        assertThat(board.secondPlayerPits()).hasSize(6);

        //then: The 7th pit should be the Store of Player 1
        Pit seventhPit = board.getPits().get(6);
        assertThat(seventhPit.isStore()).isTrue();
        assertThat(seventhPit.getPlayer()).isEqualTo(player1);

        //then: The 14th pit should be the store of Player 2
        Pit fourteenthPit = board.getPits().get(13);
        assertThat(fourteenthPit.isStore()).isTrue();
        assertThat(fourteenthPit.getPlayer()).isEqualTo(player2);
    }

}