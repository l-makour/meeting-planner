package com.canalplus.meetingplanner.services.handlers;

import com.canalplus.meetingplanner.entities.Equipement;
import com.canalplus.meetingplanner.entities.Meeting;
import com.canalplus.meetingplanner.entities.Room;
import com.canalplus.meetingplanner.repositories.MeetingRepository;
import com.canalplus.meetingplanner.services.MeetingRequest;
import com.canalplus.meetingplanner.util.Handler;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@Order(40)
public class H4CheckEquipment implements Handler<MeetingRequest> {


    private final MeetingRepository reunionRepository;

    public H4CheckEquipment(MeetingRepository reunionRepository) {
        this.reunionRepository = reunionRepository;
    }

    @Override
    public boolean handle(MeetingRequest request) {

        List<Room> roomsWithEquipment = checkRoomEquipment(request.getAvailableRooms(), request.getMeetingDto().getType());
        if (roomsWithEquipment.size() > 0) {
            request.setAvailableRooms(roomsWithEquipment);
            return false;
        } else {
            roomsWithEquipment = checkRoomWithExtraEquipment(request.getAvailableRooms(), request.getMeetingDto().getType(), request.getAvailableEquipement());
            request.setAvailableRooms(roomsWithEquipment);
            request.setWithExtraEquipment(true);
        }
        return false;
    }


    public List<Room> checkRoomEquipment(List<Room> availableRooms, String reunionType) {

        List<Room> roomsWithEquipment = new ArrayList<>();

        for (Room room : availableRooms) {
            Meeting meeting = reunionRepository.findByType(reunionType);
            if ((meeting.getNbEcran() <= room.getNbEcran()) &&
                    (meeting.getNbPieuvre() <= room.getNbPieuvre()) &&
                    (meeting.getNbTableau() <= room.getNbTableau()) &&
                    (meeting.getNbWebcam() <= room.getNbWebcam()))
                roomsWithEquipment.add(room);
        }
        return roomsWithEquipment;
    }


    public List<Room> checkRoomWithExtraEquipment(List<Room> availableRooms, String reunionType, Equipement equipement) {

        List<Room> roomsWithEquipment = new ArrayList<>();

        for (Room room : availableRooms) {
            Meeting meeting = reunionRepository.findByType(reunionType);
            if ((meeting.getNbEcran() <= room.getNbEcran() + equipement.getNbEcran()) &&
                    (meeting.getNbPieuvre() <= room.getNbPieuvre() + equipement.getNbPieuvre()) &&
                    (meeting.getNbTableau() <= room.getNbTableau() + equipement.getNbTableau()) &&
                    (meeting.getNbWebcam() <= room.getNbWebcam() + equipement.getNbWebcam()))
                roomsWithEquipment.add(room);
        }
        return roomsWithEquipment;
    }
}
