package com.washify.api.core.service;

import com.washify.api.core.domain.User;
import com.washify.api.core.domain.WashStation;
import com.washify.api.core.dto.WashStationRequest;
import com.washify.api.infrastructure.repository.UserRepository;
import com.washify.api.infrastructure.repository.WashStationRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WashStationService {

    private final WashStationRepository washStationRepository;
    private final UserRepository userRepository;


    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);


    @Transactional
    public WashStation createStation(WashStationRequest request) {
        User owner = userRepository.findById(request.ownerId())
                .orElseThrow(() -> new RuntimeException("Eroare: Owner-ul cu ID " + request.ownerId() + " nu a fost găsit!"));


        WashStation station = new WashStation();
        station.setName(request.name());
        station.setAddress(request.address());
        station.setOwner(owner);


        Point location = geometryFactory.createPoint(new Coordinate(request.longitude(), request.latitude()));
        station.setLocation(location);

        return washStationRepository.save(station);
    }

    @Transactional(readOnly = true)
    public List<WashStation> getStationsForUser(User user) {

        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getName().name().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return washStationRepository.findAll();
        }


        return washStationRepository.findByOwnerId(user.getId());
    }

    @Transactional
    public void deleteStation(Long id) {
        if (!washStationRepository.existsById(id)) {
            throw new RuntimeException("Spălătoria nu există!");
        }
        washStationRepository.deleteById(id);
    }
}