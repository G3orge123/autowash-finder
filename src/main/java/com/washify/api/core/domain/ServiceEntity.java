package com.washify.api.core.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "services")
@Getter
@Setter
@NoArgsConstructor
public class ServiceEntity extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String name;
    private String description;
    private Double price;
    private Integer durationMinutes;
}