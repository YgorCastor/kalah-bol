/*
 *   "THE ULTIMATE BEER-WARE LICENSE" (Revision 6739):
 *   <ygorcastor@gmail.com> wrote this file.  As long as you retain this notice you
 *   can do whatever you want with this stuff. I'm not liable for anything you do
 *   with this code, and if you want to pay me a beer in the unlikely event
 *   of us meeting, be my guest.
 */

package kalah.bol.domain.board.exceptions;

public class NotEnoughPiecesInPitException extends RuntimeException {

    public NotEnoughPiecesInPitException(int numOfPieces, int maximumNumOfPieces) {
        super("Not enough pieces in the pit: Tried to remove '" + numOfPieces + "', but there are '" + maximumNumOfPieces + "' on it.");
    }

}
