# AutoWash-Finder - Car Wash Locator Platform

## Project Description
AutoWash-Finder is an interactive web application (MVP) that centralizes information about car washes in Romania, with a strong focus on the self-service segment. The platform allows users to quickly find the nearest car wash based on specific filters, while offering owners an admin panel to manage their online presence and attract more customers.

## Core Features (MVP)

### 1. User Module (Driver)
* View car washes on an interactive map.
* Filter results by: Wash Type (Self-Service/Manual), Card Payment availability, 24/7 Schedule.
* View location details: address, price per token, available amenities.

### 2. Car Wash Admin Module (Owner)
* Secure authentication.
* Dashboard for editing location details (schedule, prices, amenities).
* Activate "Premium" status to highlight the location pin on the map.

### 3. SuperAdmin Module
* Approve/delete new car washes from the database.
* User role management.

## Database Entity-Relationship (ER) Diagram

```mermaid
erDiagram
    User ||--|| UserProfile : "has (1:1)"
    User ||--o{ CarWash : "owns (1:N)"
    User ||--o{ Booking : "makes (1:N)"
    User ||--o{ Review : "writes (1:N)"
    CarWash ||--o{ ServicePrice : "offers (1:N)"
    WashService ||--o{ ServicePrice : "priced as (1:N)"
    ServicePrice ||--o{ Booking : "is booked (1:N)"
    CarWash ||--o{ Review : "receives (1:N)"
    CarWash ||--o{ CarWash_Tag : "tagged with (M:N)"
    Tag ||--o{ CarWash_Tag : "belongs to (M:N)"

    User {
        BigInt id PK
        Varchar email UK
        Varchar password
        Enum role "'CUSTOMER', 'OWNER', 'ADMIN'"
    }

    UserProfile {
        BigInt id PK
        BigInt user_id FK "UK"
        Varchar phone_number
        Varchar car_plate
        Varchar preferred_payment_method
    }

    CarWash {
        BigInt id PK
        BigInt owner_id FK
        Varchar name
        Varchar address
        Double latitude
        Double longitude
        Enum status "'OPEN', 'CLOSED'"
    }

    WashService {
        BigInt id PK
        Varchar name
        Text description
    }

    ServicePrice {
        BigInt id PK
        BigInt car_wash_id FK
        BigInt service_id FK
        Decimal price
        Int duration_minutes
    }

    Booking {
        BigInt id PK
        BigInt user_id FK
        BigInt service_price_id FK
        Timestamp appointment_time
        Enum status "'PENDING', 'CONFIRMED', 'COMPLETED', 'CANCELLED'"
    }

    Review {
        BigInt id PK
        BigInt user_id FK
        BigInt car_wash_id FK
        Int rating "1-5"
        Text comment
        Timestamp created_at
    }

    Tag {
        BigInt id PK
        Varchar name UK
    }

    CarWash_Tag {
        BigInt car_wash_id PK, FK
        BigInt tag_id PK, FK
    }
