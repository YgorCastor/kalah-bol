/*
 *   "THE ULTIMATE BEER-WARE LICENSE" (Revision 6739):
 *   <ygorcastor@gmail.com> wrote this file.  As long as you retain this notice you
 *   can do whatever you want with this stuff. I'm not liable for anything you do
 *   with this code, and if you want to pay me a beer in the unlikely event
 *   of us meeting, be my guest.
 */

package kalah.bol.api.exceptionhandlers;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import kalah.bol.domain.board.exceptions.PitStoreNotFoundException;

import javax.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {PitStoreNotFoundException.class, ExceptionHandler.class})
public class PitStoreNotFoundExceptionHandler implements ExceptionHandler<PitStoreNotFoundException, HttpResponse> {

    @Override
    public HttpResponse handle(HttpRequest request, PitStoreNotFoundException exception) {
        return HttpResponse.notFound(exception);
    }

}
