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

@Singleton
public class GameService {

    private final SessionRepository sessionRepository;

    @Inject
    public GameService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session newGame(NewGameCommand command) {
        Game newGame = new Game(command.getFirstPlayer(), command.getSecondPlayer());
        return sessionRepository.createSession(newGame);
    }

    public Session findGame(String sessionId) {
        return sessionRepository.withId(sessionId);
    }

    public GameState movement(String sessionId, Action action) {
        Session session = sessionRepository.withId(sessionId);
        Game game = session.getGame();
        return game.play(action);
    }

}
