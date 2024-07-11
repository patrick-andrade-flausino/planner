package com.rocketseat.planner.repository;

import com.rocketseat.planner.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ActivitiesRepository extends JpaRepository<Activity, UUID> {
    List<Activity> findByTripId(UUID trip_id);
}
