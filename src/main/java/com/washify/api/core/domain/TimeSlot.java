package com.washify.api.core.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "time_slots")
@Getter
@Setter
@NoArgsConstructor
public class TimeSlot extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wash_station_id")
    private WashStation washStation;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isAvailable = true;

    @OneToOne(mappedBy = "timeSlot")
    private Booking booking;
}