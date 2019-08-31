/*
 *   "THE ULTIMATE BEER-WARE LICENSE" (Revision 6739):
 *   <ygorcastor@gmail.com> wrote this file.  As long as you retain this notice you
 *   can do whatever you want with this stuff. I'm not liable for anything you do
 *   with this code, and if you want to pay me a beer in the unlikely event
 *   of us meeting, be my guest.
 */

package kalah.bol.domain.session;

import kalah.bol.domain.game.Game;

/**
 * The interface Session repository.
 */
public interface SessionRepository {

    /**
     * With id session.
     *
     * @param id the id
     * @return the session
     */
    Session withId(String id);

    /**
     * Create session session.
     *
     * @param game the game
     * @return the session
     */
    Session createSession(Game game);

}
