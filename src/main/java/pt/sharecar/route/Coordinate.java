package pt.sharecar.route;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public final class Coordinate {

    private final Double latitude;

    private final Double longitude;

    public Coordinate(Double latitude, Double longitude) {
        validateCoordinate(latitude, longitude);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private void validateCoordinate(Double latitude, Double longitude) {
        if (latitude == null || longitude == null) {
            throw new IllegalArgumentException("Latitude and longitude cannot be null.");
        }
        if (latitude < -90.0 || latitude > 90.0) {
            throw new IllegalArgumentException("Latitude should be between -90.0 and 90.0.");
        }
        if (longitude < -180.0 || longitude > 180.0) {
            throw new IllegalArgumentException("Longitude should be between -180.0 and 180.0.");
        }
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

}
