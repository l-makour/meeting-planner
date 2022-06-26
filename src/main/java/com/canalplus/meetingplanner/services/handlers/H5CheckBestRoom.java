package com.canalplus.meetingplanner.services.handlers;

import com.canalplus.meetingplanner.entities.Room;
import com.canalplus.meetingplanner.services.MeetingRequest;
import com.canalplus.meetingplanner.util.Handler;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(50)
public class H5CheckBestRoom implements Handler<MeetingRequest> {
    @Override
    public boolean handle(MeetingRequest request) {

        Room bestRoom = null;
        for (Room room : request.getAvailableRooms()) {
            if (bestRoom == null) bestRoom = room;
            if (room.getNbPlace() < bestRoom.getNbPlace()) bestRoom = room;
        }
        request.setBestRoom(bestRoom);
        return false;
    }
}
