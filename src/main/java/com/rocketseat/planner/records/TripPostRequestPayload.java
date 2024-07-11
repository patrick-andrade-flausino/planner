package com.rocketseat.planner.records;

import java.time.LocalDateTime;
import java.util.List;

public record TripPostRequestPayload (String destination, LocalDateTime startsAt, LocalDateTime endsAt, List<String>emails_to_invite, Boolean isConfirmed, String ownerName, String ownerEmail){
}
