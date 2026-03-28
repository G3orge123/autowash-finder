package com.washify.api.core.domain;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;
import java.util.Set;
import java.util.List;

@Entity
@Table(name = "wash_stations")
@Getter
@Setter
@NoArgsConstructor
public class WashStation extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String address;

    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point location; // Suport PostGIS pentru Waze/Maps

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "station_services",
            joinColumns = @JoinColumn(name = "station_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<ServiceEntity> services;

    @OneToMany(mappedBy = "washStation", cascade = CascadeType.ALL)
    private List<TimeSlot> slots;
}