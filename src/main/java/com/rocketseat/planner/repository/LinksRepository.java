package com.rocketseat.planner.repository;

import com.rocketseat.planner.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LinksRepository extends JpaRepository<Link, UUID> {
    List<Link> findByTripId(UUID trip_id);
}
