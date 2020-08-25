package tourguidepricer.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Provider {
    public final String name;
    public final double price;
    public final UUID tripId;

    @JsonCreator
    public Provider(@JsonProperty("tripId") UUID tripId, @JsonProperty("name")String name, @JsonProperty("price")double price) {
        this.name = name;
        this.tripId = tripId;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public UUID getTripId() {
        return tripId;
    }
}
