package com.rocketseat.planner.records;

import java.time.LocalDateTime;

public record ActivityPostCreatResponse(LocalDateTime occurs_at, String title) {
}
