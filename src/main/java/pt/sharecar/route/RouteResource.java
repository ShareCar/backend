package pt.sharecar.route;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.postgis.jdbc.PGgeometry;
import net.postgis.jdbc.geometry.LinearRing;
import net.postgis.jdbc.geometry.Point;

import java.net.URI;
import java.util.List;

@Path("/routes")

public class RouteResource {

    @Inject
    RouteRepository repository;

    private static PGgeometry createPGGeometry(double[][] coordinates) {
        Point[] points = new Point[coordinates.length];
        for (int i = 0; i < coordinates.length; i++) {
            if (coordinates[i].length != 2) {
                throw new IllegalArgumentException("Invalid coordinate format: " + coordinates[i]);
            }
            points[i] = new Point(coordinates[i][0], coordinates[i][1]);
        }
        LinearRing outerRing = new LinearRing(points);
        return new PGgeometry(outerRing);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Route> list() {
        return repository.listAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(RouteDTO routeDTO) {
        Route route = new Route(routeDTO.description, createPGGeometry(routeDTO.coordinates));
        repository.persist(route);
        return Response.created(URI.create("/routes/" + route.getId())).build();
    }
}