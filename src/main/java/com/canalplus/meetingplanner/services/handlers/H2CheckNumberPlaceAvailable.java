package com.canalplus.meetingplanner.services.handlers;

import com.canalplus.meetingplanner.services.MeetingRequest;
import com.canalplus.meetingplanner.util.Handler;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@Order(20)
public class H2CheckNumberPlaceAvailable implements Handler<MeetingRequest> {

    @Override
    public boolean handle(MeetingRequest request) {

        request.setAvailableRooms(request.getAvailableRooms().stream()
                .filter(room -> room.getNbPlace() * 0.7 >= request.getMeetingDto().getNbPeople())
                .collect(Collectors.toList()));

        return false;
    }
}
