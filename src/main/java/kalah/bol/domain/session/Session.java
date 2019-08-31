/*
 *   "THE ULTIMATE BEER-WARE LICENSE" (Revision 6739):
 *   <ygorcastor@gmail.com> wrote this file.  As long as you retain this notice you
 *   can do whatever you want with this stuff. I'm not liable for anything you do
 *   with this code, and if you want to pay me a beer in the unlikely event
 *   of us meeting, be my guest.
 */

package kalah.bol.domain.session;

import io.micronaut.core.annotation.Introspected;
import kalah.bol.domain.game.Game;

/**
 * The type Session.
 */
@Introspected
public class Session {

    private final String sessionId;
    private final Game game;

    /**
     * Instantiates a new Session.
     *
     * @param sessionId the session id
     * @param game      the game
     */
    public Session(String sessionId, Game game) {
        this.sessionId = sessionId;
        this.game = game;
    }

    /**
     * Gets session id.
     *
     * @return the session id
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Gets game.
     *
     * @return the game
     */
    public Game getGame() {
        return game;
    }
}
