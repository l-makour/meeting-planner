package com.canalplus.meetingplanner.services;


import com.canalplus.meetingplanner.dto.ReservationDTO;
import com.canalplus.meetingplanner.dto.MeetingDto;
import com.canalplus.meetingplanner.entities.Equipement;
import com.canalplus.meetingplanner.entities.Reservation;
import com.canalplus.meetingplanner.entities.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeetingRequest {

    private MeetingDto meetingDto;
    private ReservationDTO reservationDTO;
    private List<Reservation> reservationList;
    private List<Room> availableRooms = new ArrayList<>();
    private Room bestRoom;
    private Equipement availableEquipement;
    private boolean withExtraEquipment = false;
    private Reservation reservation = new Reservation();
    private boolean found;

}
