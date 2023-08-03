package pt.sharecar.route;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import net.postgis.jdbc.PGgeometry;
import net.postgis.jdbc.geometry.Geometry;
import net.postgis.jdbc.geometry.Polygon;
import org.hibernate.annotations.Type;


import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Route implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String description;

    @JsonIgnore
    @Column(columnDefinition = "Geometry")
    private PGgeometry coordinates;

    public Route() {
    }

    public Route(String description, PGgeometry coordinates) {
        this.description = description;
        this.coordinates = coordinates;
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PGgeometry getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(PGgeometry coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(id, route.id) && Objects.equals(description, route.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
