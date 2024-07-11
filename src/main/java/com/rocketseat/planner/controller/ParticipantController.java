package com.rocketseat.planner.controller;

import com.rocketseat.planner.entity.Participant;
import com.rocketseat.planner.records.ParticipantPostRequestPayload;
import com.rocketseat.planner.service.ParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    private ParticipantService participantService;

    public ParticipantController(ParticipantService participantService){
        this.participantService = participantService;
    }

    @PostMapping("/{id}/confirm")
    public ResponseEntity<Participant> confirmParticipant(@PathVariable UUID id, @RequestBody ParticipantPostRequestPayload participantPostRequestPayload){
        return participantService.save(participantPostRequestPayload, id);
    }

}
