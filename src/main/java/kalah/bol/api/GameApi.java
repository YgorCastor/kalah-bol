/*
 *   "THE ULTIMATE BEER-WARE LICENSE" (Revision 6739):
 *   <ygorcastor@gmail.com> wrote this file.  As long as you retain this notice you
 *   can do whatever you want with this stuff. I'm not liable for anything you do
 *   with this code, and if you want to pay me a beer in the unlikely event
 *   of us meeting, be my guest.
 */

package kalah.bol.api;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import io.reactivex.Single;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kalah.bol.controller.game.GameService;
import kalah.bol.controller.game.NewGameCommand;
import kalah.bol.domain.game.Action;
import kalah.bol.domain.game.GameState;
import kalah.bol.domain.session.Session;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Controller("/api/game")
@Validated
@Tag(name = "GameApi")
public class GameApi {

    private final GameService gameService;

    @Inject
    public GameApi(GameService gameService) {
        this.gameService = gameService;
    }

    @Operation(summary = "Creates a new Kalah Game",
            description = "A new Kalah Game with the informed players is created"
    )
    @ApiResponse(responseCode = "201", content = @Content(mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = Session.class)),
            description = "Returns the new game"
    )
    @ApiResponse(responseCode = "400", description = "Invalid command supplied")
    @ApiResponse(responseCode = "500", description = "There was an failure at the creation of the game")
    @Post(produces = MediaType.APPLICATION_JSON)
    public Single<HttpResponse<Session>> newGame(@Valid @Body NewGameCommand command) {
        return Single.just(HttpResponse.created(gameService.newGame(command)));
    }

    @Operation(summary = "Find a Kalah game by the session",
            description = "Find an ongoin kalah game"
    )
    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = Session.class)),
            description = "Returns the found session")
    @ApiResponse(responseCode = "400", description = "Invalid session supplied")
    @ApiResponse(responseCode = "404", description = "No game with this session was found")
    @ApiResponse(responseCode = "500", description = "There was an failure while searching the game")
    @Get(uri = "/{sessionId}", produces = MediaType.APPLICATION_JSON)
    public Single<Session> findGameBySession(@NotBlank String sessionId) {
        return Single.just(gameService.findGame(sessionId));
    }

    @Operation(summary = "Make an game movement",
            description = "Makes an game movement and returns the new game state"
    )
    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = GameState.class)),
            description = "Returns the new GameState")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "404", description = "No game with this session was found")
    @ApiResponse(responseCode = "500", description = "There was an failure while executing the movement")
    @Post(uri = "/{sessionId}/play")
    public Single<GameState> makePlay(@NotBlank String sessionId, @Valid @Body Action action) {
        return Single.just(gameService.movement(sessionId, action));
    }

}
