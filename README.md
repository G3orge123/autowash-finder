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

## 🗄️ Database Entity-Relationship (ER) Diagram

```mermaid
erDiagram
    USERS ||--o{ WASH_STATIONS : "owns (1:N)"
    USERS ||--o{ REVIEWS : "writes (1:N)"
    WASH_STATIONS ||--|| STATION_DETAILS : "has (1:1)"
    WASH_STATIONS ||--o{ REVIEWS : "receives (1:N)"
    WASH_STATIONS ||--o{ STATION_SERVICES : "offers (M:N junction)"
    SERVICES ||--o{ STATION_SERVICES : "included in (M:N junction)"

    USERS {
        int id PK
        string email UK
        string password_hash
        string role "driver, wash_admin"
        datetime created_at
    }

    WASH_STATIONS {
        int id PK
        int owner_id FK
        string name
        float latitude
        float longitude
        string status "active, inactive"
    }

    STATION_DETAILS {
        int station_id PK, FK
        boolean has_card_payment
        boolean is_nonstop
        string contact_phone
    }

    REVIEWS {
        int id PK
        int station_id FK
        int user_id FK
        int rating "1 to 5"
        text comment
        datetime created_at
    }

    SERVICES {
        int id PK
        string name "e.g., Active Foam, Hot Wax, Vacuum"
        string description
    }

    STATION_SERVICES {
        int station_id PK, FK
        int service_id PK, FK
        decimal price "price per minute/token"
    }
