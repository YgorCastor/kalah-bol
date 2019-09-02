/*
 *   "THE ULTIMATE BEER-WARE LICENSE" (Revision 6739):
 *   <ygorcastor@gmail.com> wrote this file.  As long as you retain this notice you
 *   can do whatever you want with this stuff. I'm not liable for anything you do
 *   with this code, and if you want to pay me a beer in the unlikely event
 *   of us meeting, be my guest.
 */

package kalah.bol.domain.session.internal;

import kalah.bol.domain.game.Game;
import kalah.bol.domain.session.Session;
import kalah.bol.domain.session.SessionRepository;
import kalah.bol.domain.session.exceptions.GameSessionNotFoundException;

import javax.inject.Singleton;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
class SessionHashmapRepository implements SessionRepository {

    private final Map<String, Game> sessions;

    public SessionHashmapRepository() {
        this.sessions = new ConcurrentHashMap<>();
    }

    @Override
    public Session withId(String id) {
        if (!sessions.containsKey(id)) {
            throw new GameSessionNotFoundException(id);
        }
        return new Session(id, sessions.get(id));
    }

    @Override
    public Session createSession(Game game) {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, game);
        return new Session(sessionId, game);
    }

    @Override
    public Session updateGame(String id, Game game) {
        sessions.replace(id, game);
        return new Session(id, game);
    }
}
