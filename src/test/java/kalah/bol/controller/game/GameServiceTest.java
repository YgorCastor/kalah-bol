package kalah.bol.controller.game;

import io.micronaut.test.annotation.MicronautTest;
import kalah.bol.domain.game.Action;
import kalah.bol.domain.game.GameState;
import kalah.bol.domain.player.Player;
import kalah.bol.domain.session.Session;
import kalah.bol.domain.session.exceptions.GameSessionNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@MicronautTest
@DisplayName("The game service tests")
class GameServiceTest {

    @Inject
    private GameService gameService;

    @Test
    @DisplayName("It should create a new game")
    void itShouldCreateAnNewGame() {
        //given
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        NewGameCommand command = new NewGameCommand(player1, player2);

        //when
        Session session = gameService.newGame(command);

        //then
        assertThat(session.getSessionId()).isNotBlank();
        assertThat(session.getGame()).isNotNull();
    }

    @Test
    @DisplayName("It should find an existing")
    void itShouldCreateFindAnExistingGame() {
        //given
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        NewGameCommand command = new NewGameCommand(player1, player2);

        //when
        Session session = gameService.newGame(command);
        Session foundSession = gameService.findGame(session.getSessionId());

        //then
        assertThat(foundSession.getSessionId()).isNotBlank();
        assertThat(foundSession.getGame()).isNotNull();
    }

    @Test
    @DisplayName("It should move an existing game")
    void itShouldMoveAnExistingGame() {
        //given
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Action action = new Action(player1, 1);
        NewGameCommand command = new NewGameCommand(player1, player2);

        //when
        Session session = gameService.newGame(command);
        GameState state = gameService.movement(session.getSessionId(), action);

        //then
        assertThat(state).isNotNull();
    }

    @Test
    @DisplayName("It should raise an exception if there`s no session")
    void itShouldRaiseExceptionIfNoSession() {
        assertThatThrownBy(() -> gameService.findGame("1"))
                .isInstanceOf(GameSessionNotFoundException.class);
    }
}