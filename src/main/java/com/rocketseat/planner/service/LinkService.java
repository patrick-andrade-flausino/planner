package com.rocketseat.planner.service;

import com.rocketseat.planner.entity.Activity;
import com.rocketseat.planner.entity.Link;
import com.rocketseat.planner.entity.Trip;
import com.rocketseat.planner.records.*;
import com.rocketseat.planner.repository.ActivitiesRepository;
import com.rocketseat.planner.repository.LinksRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService {

    private LinksRepository linkRepository;

    public LinkService(LinksRepository linkRepository){
        this.linkRepository = linkRepository;
    }

    public UUIDResponse registerLink(LinkPostCreatResponse activitiesPostCreatResponse, Trip trip){
        Link newActivity = new Link(activitiesPostCreatResponse.title(), activitiesPostCreatResponse.url(), trip);

        this.linkRepository.save(newActivity);

        return new UUIDResponse(newActivity.getId());
    }

    public List<LinkData> getLink(UUID id){
        return this.linkRepository.findByTripId(id).stream().map(activity -> new LinkData(activity.getId(), activity.getTitle(),
                activity.getUrl())).toList();
    }
}
