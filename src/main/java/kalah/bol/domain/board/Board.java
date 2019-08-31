/*
 *   "THE ULTIMATE BEER-WARE LICENSE" (Revision 6739):
 *   <ygorcastor@gmail.com> wrote this file.  As long as you retain this notice you
 *   can do whatever you want with this stuff. I'm not liable for anything you do
 *   with this code, and if you want to pay me a beer in the unlikely event
 *   of us meeting, be my guest.
 */

package kalah.bol.domain.board;

import io.micronaut.core.annotation.Introspected;
import kalah.bol.domain.board.exceptions.PitStoreNotFoundException;
import kalah.bol.domain.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The board class represents the Kalah board, it contains
 * the pits and information for each player.
 */
@Introspected
public class Board {

    private static final Integer NUM_OF_PITS = 7 * 2;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private final List<Pit> pits;

    /**
     * Creates a default kalah board
     *
     * @param firstPlayer The first player of the game
     * @param secondPlayer The second player of the game
     */
    public Board(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        pits = new ArrayList<>(NUM_OF_PITS);
        initBoard();
    }

    /**
     * Initializes the board for both players
     */
    public void initBoard() {
        initPitsFor(this.firstPlayer, false);
        initPitsFor(this.secondPlayer, false);
    }

    /**
     * Clears the Board
     */
    public void clearBoard() {
        getPits().clear();
        initPitsFor(this.firstPlayer, true);
        initPitsFor(this.secondPlayer, true);
    }

    /**
     * Initializes the pits for a player, with 6 normal
     * pits and 1 store pit.
     *
     * @param player The player to initialize the pits for
     */
    private void initPitsFor(Player player, boolean empty) {
        for(int i = 0; i <= 6; i++) {
            if(i != 0 && i % 6 == 0) { // Is the store pit
                pits.add(new Pit(player, 0, true));
                continue;
            }
            if(empty) {
                pits.add(new Pit(player, 0, false));
            } else {
                pits.add(new Pit(player));
            }
        }
    }

    /**
     * Lists all the pits from the board
     * @return Pits
     */
    public List<Pit> getPits() {
        return pits;
    }

    /**
     * Returns the pit pointed at the cursor index, the cursor
     * is calculated by the module of the Number Of Pits, in that
     * way we can move through the List in a circular way.
     *
     * @param index Current Cursor index
     * @return The pit in the board at the informed index
     */
    public Pit pit(int index) {
        index = index % NUM_OF_PITS;
        return getPits().get(index);
    }

    /**
     * List the pits of the first player
     *
     * @return Pits of the first player
     */
    public List<Pit> firstPlayerPits() {
        return pitsOfPlayer(this.firstPlayer);
    }

    /**
     * This method returns the amout of pieces currently at the game,
     * for the first player, that is, all the pieces outsite the store.
     *
     * @return The number of pieces at the first player board
     */
    public Integer firstPlayerNumPiecesInGame() {
        return numPiecesInGameForPlayer(this.firstPlayer);
    }

    /**
     * This method clears the board of the first player, putting
     * all the pieces at the store.
     *
     * @return The Store Pit of the first player with all the pieces
     */
    public Pit finishFirstPlayerBoard() {
        return putAllInStore(this.firstPlayer);
    }

    /**
     * List the pits of the second player
     *
     * @return Pits of the second player
     */
    public List<Pit> secondPlayerPits() {
        return pitsOfPlayer(this.secondPlayer);
    }

    /**
     * This method returns the amout of pieces currently at the game,
     * for the second player, that is, all the pieces outsite the store.
     *
     * @return The number of pieces at the second player board
     */
    public Integer secondPlayerNumPiecesInGame() {
        return numPiecesInGameForPlayer(this.secondPlayer);
    }

    /**
     * This method clears the board of the second player, putting
     * all the pieces at the store.
     *
     * @return The Store Pit of the second player with all the pieces
     */
    public Pit finishSecondPlayerBoard() {
        return putAllInStore(this.secondPlayer);
    }

    private Pit putAllInStore(Player player) {
        Pit store = storeOfPlayer(player);
        int allPieces = this.numPiecesInGameForPlayer(player);
        return store.addPieces(allPieces);
    }

    private Pit storeOfPlayer(Player player) {
        return getPits()
                .stream()
                .filter(pit -> pit.getPlayer().equals(player))
                .filter(Pit::isStore)
                .findFirst()
                .orElseThrow(() -> new PitStoreNotFoundException(player));
    }

    private List<Pit> pitsOfPlayer(Player player) {
        return pits.stream()
                .filter(pit -> pit.getPlayer().equals(player))
                .filter(pit -> !pit.isStore())
                .collect(Collectors.toList());
    }

    private Integer numPiecesInGameForPlayer(Player player) {
        return pitsOfPlayer(player)
                .stream()
                .mapToInt(Pit::getPieces)
                .sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return firstPlayer.equals(board.firstPlayer) &&
                secondPlayer.equals(board.secondPlayer) &&
                pits.equals(board.pits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstPlayer, secondPlayer, pits);
    }

    @Override
    public String toString() {
        return "Board{" +
                "firstPlayer=" + firstPlayer +
                ", secondPlayer=" + secondPlayer +
                ", pits=" + pits +
                '}';
    }
}
