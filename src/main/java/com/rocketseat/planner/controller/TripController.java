package com.rocketseat.planner.controller;

import com.rocketseat.planner.entity.Trip;
import com.rocketseat.planner.records.*;
import com.rocketseat.planner.service.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController {

    private TripService tripService;

    public TripController(TripService tripService){
        this.tripService = tripService;
    }

    @PostMapping
    public ResponseEntity<Trip> createTrip(@RequestBody TripPostRequestPayload tripPostRequestPayload){
        return ResponseEntity.ok(tripService.save(tripPostRequestPayload));
    }

    @PostMapping("/{id}/invite")
    public ResponseEntity<ParticipantCreateResponse> inviteTrip(@PathVariable UUID id, @RequestBody ParticipantPostRequestPayload  participantPostRequestPayload){
        return tripService.inviteTrip(id, participantPostRequestPayload);
    }
    @PostMapping("/{id}/activities")
    public ResponseEntity<UUIDResponse> registerActivity(@PathVariable UUID id, @RequestBody ActivityPostCreatResponse postRequestPayload){
        return tripService.registerActivity(id, postRequestPayload);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripDetails(@PathVariable UUID id){
        Optional<Trip> searchTrip = this.tripService.findById(id);
        return searchTrip.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable UUID id){
        return this.tripService.confirmTrip(id);
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantData>> getParticipants(@PathVariable UUID id){
        return this.tripService.getParticipants(id);
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityData>> getActivity(@PathVariable UUID id){
        return this.tripService.getActivity(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable UUID id, @RequestBody TripPostRequestPayload payload){
        return this.tripService.updateTrip(id, payload);
    }


    //links
    @PostMapping("/{id}/links")
    public ResponseEntity<UUIDResponse> registerLinks(@PathVariable UUID id, @RequestBody LinkPostCreatResponse postRequestPayload){
        return tripService.registerLinks(id, postRequestPayload);
    }

    @GetMapping("/{id}/links")
    public ResponseEntity<List<LinkData>> getLinks(@PathVariable UUID id){
        return this.tripService.getLink(id);
    }
}
