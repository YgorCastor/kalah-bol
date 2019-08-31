/*
 *   "THE ULTIMATE BEER-WARE LICENSE" (Revision 6739):
 *   <ygorcastor@gmail.com> wrote this file.  As long as you retain this notice you
 *   can do whatever you want with this stuff. I'm not liable for anything you do
 *   with this code, and if you want to pay me a beer in the unlikely event
 *   of us meeting, be my guest.
 */

package kalah.bol.controller.game;

import io.micronaut.core.annotation.Introspected;
import kalah.bol.domain.player.Player;

import javax.validation.constraints.NotNull;

/**
 * The type New game command.
 */
@Introspected
public class NewGameCommand {

    @NotNull
    private final Player firstPlayer;
    @NotNull
    private final Player secondPlayer;

    /**
     * Instantiates a new New game command.
     *
     * @param firstPlayer  the first player
     * @param secondPlayer the second player
     */
    public NewGameCommand(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    /**
     * Gets first player.
     *
     * @return the first player
     */
    public Player getFirstPlayer() {
        return firstPlayer;
    }

    /**
     * Gets second player.
     *
     * @return the second player
     */
    public Player getSecondPlayer() {
        return secondPlayer;
    }
}
