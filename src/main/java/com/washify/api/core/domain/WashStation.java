package com.washify.api.core.domain;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
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
    private Point location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "washStation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceEntity> services = new ArrayList<>();

    @OneToMany(mappedBy = "washStation", cascade = CascadeType.ALL)
    private List<TimeSlot> slots;
}