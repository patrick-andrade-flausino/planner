package com.rocketseat.planner.service;

import com.rocketseat.planner.entity.Activity;
import com.rocketseat.planner.entity.Trip;
import com.rocketseat.planner.records.ActivityPostCreatResponse;
import com.rocketseat.planner.records.UUIDResponse;
import com.rocketseat.planner.records.ActivityData;
import com.rocketseat.planner.repository.ActivitiesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    private ActivitiesRepository activitiesRepository;

    public ActivityService(ActivitiesRepository activitiesRepository){
        this.activitiesRepository = activitiesRepository;
    }

    public UUIDResponse registerActivity(ActivityPostCreatResponse activitiesPostCreatResponse, Trip trip){
        Activity newActivity = new Activity(activitiesPostCreatResponse.title(), activitiesPostCreatResponse.occurs_at(), trip);

        this.activitiesRepository.save(newActivity);

        return new UUIDResponse(newActivity.getId());
    }

    public List<ActivityData> getActivity(UUID id){
        return this.activitiesRepository.findByTripId(id).stream().map(activity -> new ActivityData(activity.getId(), activity.getTitle(),
                activity.getOccursAt())).toList();
    }
}
