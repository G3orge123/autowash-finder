package com.washify.api.core.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "services")
@Getter
@Setter
@NoArgsConstructor
public class ServiceEntity extends BaseEntity {
    @Column(nullable = false)
    private String name;
    private String description;
    private Double price;
    private Integer durationMinutes;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wash_station_id", nullable = false)
    private WashStation washStation;
}