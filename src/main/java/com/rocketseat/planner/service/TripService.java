package com.rocketseat.planner.service;

import com.rocketseat.planner.entity.Trip;
import com.rocketseat.planner.records.*;
import com.rocketseat.planner.repository.TripRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TripService {
    private ParticipantService participantService;
    private ActivityService activitiesService;
    private TripRepository tripRepository;
    private LinkService linkService;

    TripService(TripRepository tripRepository, ParticipantService participantService, ActivityService activitiesService, LinkService linkService){
        this.tripRepository = tripRepository;
        this.participantService = participantService;
        this.activitiesService = activitiesService;
        this.linkService = linkService;
    }


    public Trip save(TripPostRequestPayload tripPostRequestPayload){
        Trip newTrip = new Trip();
        BeanUtils.copyProperties(tripPostRequestPayload, newTrip);
        newTrip.setIsConfirmed(false);
        this.tripRepository.save(newTrip);
        this.participantService.registerParticipantsToEvent(tripPostRequestPayload.emails_to_invite(), newTrip);
        return newTrip;
    }
    public Optional<Trip> findById(UUID id){
        Optional<Trip> searchTrip = tripRepository.findById(id);
        return searchTrip;
    }

    public ResponseEntity<Trip> updateTrip(UUID id, TripPostRequestPayload tripPostRequestPayload){
        Optional<Trip> searchTrip = tripRepository.findById(id);
        if(searchTrip.isPresent()){
            Trip rawTrip = searchTrip.get();
            boolean isConfirmed = rawTrip.getIsConfirmed();
            BeanUtils.copyProperties(tripPostRequestPayload, rawTrip);
            rawTrip.setIsConfirmed(isConfirmed);
            tripRepository.save(rawTrip);
        }

        return searchTrip.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    public ResponseEntity<Trip> confirmTrip(UUID id){
        Optional<Trip> searchTrip = tripRepository.findById(id);
        if(searchTrip.isPresent()){
            Trip rawTrip = searchTrip.get();
            rawTrip.setIsConfirmed(true);
            tripRepository.save(rawTrip);
        }

        return searchTrip.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    public ResponseEntity<ParticipantCreateResponse> inviteTrip(UUID id, ParticipantPostRequestPayload participantPostRequestPayload) {
        Optional<Trip> searchTrip = tripRepository.findById(id);
        if(searchTrip.isPresent()){
            Trip rawTrip = searchTrip.get();

            ParticipantCreateResponse participantCreateResponse =  this.participantService.registerParticipantToEvent(participantPostRequestPayload.email(), rawTrip);

            if (rawTrip.getIsConfirmed()){
                this.participantService.triggerConfirmationEmailToParticipants(participantPostRequestPayload.email());
            }
            return ResponseEntity.ok(participantCreateResponse);
        }

        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<ParticipantData>> getParticipants(UUID id) {
        return ResponseEntity.ok(this.participantService.getParticipants(id));
    }

    public ResponseEntity<UUIDResponse> registerActivity(UUID id, ActivityPostCreatResponse postRequestPayload) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        if (trip.isPresent()){
            return ResponseEntity.ok(this.activitiesService.registerActivity(postRequestPayload, trip.get())) ;
        }

        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<ActivityData>> getActivity(UUID id) {
        return ResponseEntity.ok(this.activitiesService.getActivity(id));
    }

    public ResponseEntity<UUIDResponse> registerLinks(UUID id, LinkPostCreatResponse postRequestPayload) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        if (trip.isPresent()){
            return ResponseEntity.ok(this.linkService.registerLink(postRequestPayload, trip.get())) ;
        }

        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<LinkData>> getLink(UUID id) {
        return ResponseEntity.ok(this.linkService.getLink(id));
    }
}
