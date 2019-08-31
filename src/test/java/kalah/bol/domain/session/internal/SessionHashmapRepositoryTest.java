/*
 *   "THE ULTIMATE BEER-WARE LICENSE" (Revision 6739):
 *   <ygorcastor@gmail.com> wrote this file.  As long as you retain this notice you
 *   can do whatever you want with this stuff. I'm not liable for anything you do
 *   with this code, and if you want to pay me a beer in the unlikely event
 *   of us meeting, be my guest.
 */

package kalah.bol.domain.session.internal;

import io.micronaut.test.annotation.MicronautTest;
import kalah.bol.domain.game.Game;
import kalah.bol.domain.player.Player;
import kalah.bol.domain.session.Session;
import kalah.bol.domain.session.SessionRepository;
import kalah.bol.domain.session.exceptions.GameSessionNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@MicronautTest
@DisplayName("The Session Repository HashMap implementation")
class SessionHashmapRepositoryTest {

    @Inject
    SessionRepository sessionRepository;

    @Test
    @DisplayName("It should create an session correctly")
    void shouldCreateAnSession() {
        // should
        Player player1 = new Player("1");
        Player player2 = new Player("2");
        Game game = new Game(player1, player2);

        //when
        Session session = sessionRepository.createSession(game);

        //then
        assertThat(session).isNotNull();
        assertThat(session.getGame()).isEqualTo(game);
    }

    @Test
    @DisplayName("It should raise an exception if there's no session")
    void shouldRaiseExceptionIfNoSession() {
        assertThatThrownBy(() -> sessionRepository.withId("aaaa"))
                .isInstanceOf(GameSessionNotFoundException.class);
    }
}