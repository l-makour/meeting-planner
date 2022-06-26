package com.canalplus.meetingplanner.services.handlers;


import com.canalplus.meetingplanner.entities.Reservation;
import com.canalplus.meetingplanner.entities.Room;
import com.canalplus.meetingplanner.repositories.ReservationRepository;
import com.canalplus.meetingplanner.repositories.RoomRepository;
import com.canalplus.meetingplanner.services.MeetingRequest;
import com.canalplus.meetingplanner.util.Handler;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Order(10)
public class H1CheckAvailableRooms implements Handler<MeetingRequest> {


    private final ReservationRepository reservationRepository;
    private final RoomRepository salleRepository;

    public H1CheckAvailableRooms(ReservationRepository reservationRepository, RoomRepository salleRepository) {
        this.reservationRepository = reservationRepository;
        this.salleRepository = salleRepository;
    }

    @Override
    public boolean handle(MeetingRequest request) {
        List<Reservation> reservationList = reservationRepository.findAllByStartDateAndEffectiveEndDate(request.getMeetingDto().getStartDate(),
                request.getMeetingDto().getEndDate().plusHours(1L));

        List<Room> unavailableRooms = reservationList.stream()
                .map(Reservation::getRoom)
                .collect(Collectors.toList());

        List<Room> availableRooms = salleRepository.findAll().stream()
                .filter(room -> !unavailableRooms.contains(room))
                .collect(Collectors.toList());

        request.setReservationList(reservationList);
        request.setAvailableRooms(availableRooms);
        return false;
    }
}
