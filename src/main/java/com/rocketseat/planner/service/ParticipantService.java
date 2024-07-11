package com.rocketseat.planner.service;

import com.rocketseat.planner.entity.Participant;
import com.rocketseat.planner.entity.Trip;
import com.rocketseat.planner.records.ParticipantCreateResponse;
import com.rocketseat.planner.records.ParticipantData;
import com.rocketseat.planner.records.ParticipantPostRequestPayload;
import com.rocketseat.planner.records.TripPostRequestPayload;
import com.rocketseat.planner.repository.ParticipantRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ParticipantService {

    private ParticipantRepository participantRepository;

    public ParticipantService(ParticipantRepository participantRepository){
        this.participantRepository = participantRepository;
    }

    public Optional<Participant> findById(UUID id){
        Optional<Participant> searchParticipant = participantRepository.findById(id);
        return searchParticipant;
    }

    public ResponseEntity<Participant> save(ParticipantPostRequestPayload participantPostRequestPayload, UUID id){
        Optional<Participant> participanSearched = findById(id);
        if(participanSearched.isPresent()){
            Participant newParticipant = participanSearched.get();
            newParticipant.setName(participantPostRequestPayload.name());
            newParticipant.setIsConfirmed(true);
            this.participantRepository.save(newParticipant);
        }

        return participanSearched.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());

    }

    public void registerParticipantsToEvent(List<String> participantsToInvite, Trip trip){
        List<Participant> participantStream = participantsToInvite.stream().map(email -> new Participant(email, trip)).toList();
        this.participantRepository.saveAll(participantStream);

        System.out.println(participantStream.get(0).getId());
    }

    public ParticipantCreateResponse registerParticipantToEvent(String email, Trip trip){
        Participant newParticipant =  new Participant(email, trip);
        this.participantRepository.save(newParticipant);

        return new ParticipantCreateResponse(newParticipant.getId());

    }

    public List<ParticipantData> getParticipants(UUID id){
        return this.participantRepository.findByTripId(id).stream().map(participant -> new ParticipantData(participant.getId(), participant.getName(), participant.getEmail(), participant.getIsConfirmed())).toList();
    }

    public void triggerConfirmationEmailToParticipants (UUID tripId){

    }

    public void triggerConfirmationEmailToParticipants(String email) {
    }
}
