/*
 *   "THE ULTIMATE BEER-WARE LICENSE" (Revision 6739):
 *   <ygorcastor@gmail.com> wrote this file.  As long as you retain this notice you
 *   can do whatever you want with this stuff. I'm not liable for anything you do
 *   with this code, and if you want to pay me a beer in the unlikely event
 *   of us meeting, be my guest.
 */

package kalah.bol.domain.game;

import kalah.bol.domain.game.exception.NotPlayerTurnException;
import kalah.bol.domain.player.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("The Game Object Specification")
class GameTest {

    @Test
    @DisplayName("It Should execute and movement correctly")
    void itShouldExecuteAnMovementCorrectly() {
        // should
        Player player1 = new Player("1");
        Player player2 = new Player("2");
        Game game = new Game(player1, player2);

        // when
        Action action = new Action(player1, 3);
        GameState state = game.play(action);

        // then
        assertThat(state).isNotNull();
    }

    @Test
    @DisplayName("It Should raise an exception if wrong player")
    void itShouldRaiseAnExceptionIfWrongPlayer() {
        // should
        Player player1 = new Player("1");
        Player player2 = new Player("2");
        Game game = new Game(player1, player2);

        // when
        Action action = new Action(player2, 3);
        assertThatThrownBy(() -> game.play(action)).isInstanceOf(NotPlayerTurnException.class);
    }
}
