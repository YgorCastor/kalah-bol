/*
 *   "THE ULTIMATE BEER-WARE LICENSE" (Revision 6739):
 *   <ygorcastor@gmail.com> wrote this file.  As long as you retain this notice you
 *   can do whatever you want with this stuff. I'm not liable for anything you do
 *   with this code, and if you want to pay me a beer in the unlikely event
 *   of us meeting, be my guest.
 */

package kalah.bol.domain.board.exceptions;

import kalah.bol.domain.player.Player;

public class PitStoreNotFoundException extends RuntimeException {

    public PitStoreNotFoundException(Player player) {
        super("Store pit not found for the Player " + player.toString());
    }
}
