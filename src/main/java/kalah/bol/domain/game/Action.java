/*
 *   "THE ULTIMATE BEER-WARE LICENSE" (Revision 6739):
 *   <ygorcastor@gmail.com> wrote this file.  As long as you retain this notice you
 *   can do whatever you want with this stuff. I'm not liable for anything you do
 *   with this code, and if you want to pay me a beer in the unlikely event
 *   of us meeting, be my guest.
 */

package kalah.bol.domain.game;

import io.micronaut.core.annotation.Introspected;
import kalah.bol.domain.player.Player;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * An game Action Movement
 */
@Introspected
public class Action {

    @NotNull
    private final Player player;
    @Min(0)
    private final Integer pitIndex;

    public Action(Player player, Integer pitIndex) {
        this.player = player;
        this.pitIndex = pitIndex;
    }

    public Player getPlayer() {
        return player;
    }

    public Integer getPitIndex() {
        return pitIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        return Objects.equals(player, action.player) &&
                Objects.equals(pitIndex, action.pitIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, pitIndex);
    }

    @Override
    public String toString() {
        return "Action{" +
                "player=" + player +
                ", pitIndex=" + pitIndex +
                '}';
    }
}
