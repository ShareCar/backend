package pt.sharecar.route;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import pt.sharecar.route.Route;

@ApplicationScoped
@Transactional
public class RouteRepository implements PanacheRepository<Route> { }
