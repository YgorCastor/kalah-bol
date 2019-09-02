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

package kalah.bol.controller.game;

import kalah.bol.domain.game.Action;
import kalah.bol.domain.game.Game;
import kalah.bol.domain.game.GameState;
import kalah.bol.domain.session.Session;
import kalah.bol.domain.session.SessionRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The Main game controller, this class is responsible to
 * dispatch the commands of the game.
 */
@Singleton
public class GameService {

    private final SessionRepository sessionRepository;

    @Inject
    public GameService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    /**
     * Starts an new game
     *
     * @param command The New Game command
     * @return A new session.
     */
    public Session newGame(NewGameCommand command) {
        Game newGame = new Game(command.getFirstPlayer(), command.getSecondPlayer());
        return sessionRepository.createSession(newGame);
    }

    /**
     * Finds an ongoing game with the informed Id
     *
     * @param sessionId The session id
     * @return The session if found
     */
    public Session findGame(String sessionId) {
        return sessionRepository.withId(sessionId);
    }

    /**
     * Dispatches an movement to the informed game.
     *
     * @param sessionId The Game session Id
     * @param action The action to be executed
     * @return The new game state
     */
    public GameState movement(String sessionId, Action action) {
        Session session = sessionRepository.withId(sessionId);
        Game game = session.getGame();
        GameState newState = game.play(action);
        sessionRepository.updateGame(sessionId, game);
        return newState;
    }

}
