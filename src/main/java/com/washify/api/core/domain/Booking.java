package com.washify.api.core.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
public class Booking extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User customer;

    @OneToOne
    @JoinColumn(name = "time_slot_id", unique = true)
    private TimeSlot timeSlot;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private Double totalPrice;
}

enum BookingStatus {
    PENDING, CONFIRMED, CANCELLED, COMPLETED
}