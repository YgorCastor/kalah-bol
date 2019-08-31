/*
 *   "THE ULTIMATE BEER-WARE LICENSE" (Revision 6739):
 *   <ygorcastor@gmail.com> wrote this file.  As long as you retain this notice you
 *   can do whatever you want with this stuff. I'm not liable for anything you do
 *   with this code, and if you want to pay me a beer in the unlikely event
 *   of us meeting, be my guest.
 */

package kalah.bol.domain.board;

import io.micronaut.core.annotation.Introspected;
import kalah.bol.domain.board.exceptions.NotEnoughPiecesInPitException;
import kalah.bol.domain.player.Player;

import java.util.Objects;

/**
 * Represents a pit of the Kalah Board
 */
@Introspected
public class Pit {
    private static final Integer DEFAULT_NUM_PIECES = 4;

    private final Player player;
    private Integer numberOfPieces;
    private final Boolean store;

    /**
     * @param player         The owner of this Pit
     * @param numberOfPieces Number of pieces in this pit
     * @param store          true if it's a store pit, false if otherwise
     */
    public Pit(Player player, Integer numberOfPieces, Boolean store) {
        this.player = player;
        this.numberOfPieces = numberOfPieces;
        this.store = store;
    }

    /**
     * Constructor to instantiate a default Pit
     *
     * @param player The owner of this Pit
     */
    public Pit(Player player) {
        this.player = player;
        this.store = false;
        this.numberOfPieces = DEFAULT_NUM_PIECES;
    }

    /**
     * Add a number of pieces to a pit
     *
     * @param quantity Quantity of pieces to add to the pit
     * @return Amount of Pieces at the pit after the insertion
     */
    public Pit addPieces(int quantity) {
        numberOfPieces += quantity;
        return this;
    }

    /**
     * Adds a single piece to the pit
     *
     * @return Amount of Pieces at the pit after the insertion
     */
    public Pit addPiece() {
        return addPieces(1);
    }

    /**
     * Remove a number of pieces from the pit
     *
     * @param quantity Quantity of pieces to remove
     * @return Amount of Pieces at the pit after the removal
     * @throws NotEnoughPiecesInPitException If the amount of pieces to remove exceeds the amout of pieces in the pit
     */
    public Integer removePieces(int quantity) {
        if (quantity > numberOfPieces) {
            throw new NotEnoughPiecesInPitException(quantity, numberOfPieces);
        }
        numberOfPieces -= quantity;
        return getPieces();
    }

    /**
     * Take all the pieces from the pit
     *
     * @return The amount of pieces taken from the pit
     */
    public Integer takeAllPieces() {
        int pieces = numberOfPieces;
        numberOfPieces = 0;
        return pieces;
    }

    /**
     * @return The number of pieces in the pit
     */
    public Integer getPieces() {
        return numberOfPieces;
    }

    /**
     * @return The Owner of this Pit
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return true if the pit is a store, false if otherwise
     */
    public boolean isStore() {
        return store;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pit pit = (Pit) o;
        return Objects.equals(player, pit.player) &&
                Objects.equals(numberOfPieces, pit.numberOfPieces) &&
                Objects.equals(store, pit.store);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, numberOfPieces, store);
    }

    @Override
    public String toString() {
        return "Pit{" +
                "player=" + player +
                ", numberOfPieces=" + numberOfPieces +
                ", store=" + store +
                '}';
    }
}
