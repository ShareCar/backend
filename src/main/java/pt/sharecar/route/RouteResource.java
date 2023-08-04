package pt.sharecar.route;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.net.URI;
import java.util.List;

@Path("/routes")
public class RouteResource {

    @Inject
    RouteRepository repository;

    private static Geometry createPGGeometry(double[][] coordinates) {
        GeometryFactory factory = new GeometryFactory();
        Point[] points = new Point[coordinates.length];
        for (int i = 0; i < coordinates.length; i++) {
            if (coordinates[i].length != 2) {
                throw new IllegalArgumentException("Invalid coordinate format: " + coordinates[i]);
            }
            points[i] = factory.createPoint(new Coordinate(coordinates[i][1], coordinates[i][0]));
        }
        return factory.createMultiPoint(points);
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