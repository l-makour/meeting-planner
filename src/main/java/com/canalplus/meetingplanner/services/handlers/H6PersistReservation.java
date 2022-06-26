package com.canalplus.meetingplanner.services.handlers;


import com.canalplus.meetingplanner.dto.ReservationDTO;
import com.canalplus.meetingplanner.entities.Meeting;
import com.canalplus.meetingplanner.entities.Reservation;
import com.canalplus.meetingplanner.enums.MeetingType;
import com.canalplus.meetingplanner.exceptions.ReservationException;
import com.canalplus.meetingplanner.repositories.MeetingRepository;
import com.canalplus.meetingplanner.repositories.ReservationRepository;
import com.canalplus.meetingplanner.services.MeetingRequest;
import com.canalplus.meetingplanner.util.Handler;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(60)
public class H6PersistReservation implements Handler<MeetingRequest> {

    private final MeetingRepository reunionRepository;
    private final ReservationRepository reservationRepository;
    private static final String ROOM_NOT_FOUND = "Aucune salle n'est disponible pour vos critères de recherche";

    public H6PersistReservation(MeetingRepository reunionRepository, ReservationRepository reservationRepository) {
        this.reunionRepository = reunionRepository;
        this.reservationRepository = reservationRepository;
    }


    @Override
    public boolean handle(MeetingRequest request) throws ReservationException {

        if (request.getBestRoom() == null) throw new ReservationException(ROOM_NOT_FOUND);

        Reservation reservation = Reservation.builder()
                .startDate(request.getMeetingDto().getStartDate())
                .endDate(request.getMeetingDto().getEndDate())
                .effectiveEndDate(request.getMeetingDto().getEndDate().plusHours(1L))
                .meetingType(MeetingType.valueOf(request.getMeetingDto().getType()))
                .room(request.getBestRoom())
                .nbExternalEcran(0)
                .nbExternalPieuvre(0)
                .nbExternalTableau(0)
                .nbExternalWebcam(0)
                .build();

        if (request.isWithExtraEquipment()) {
            Meeting reunion = reunionRepository.findByType(request.getMeetingDto().getType());
            reservation.setNbExternalTableau(Math.max(0,reunion.getNbTableau() - request.getBestRoom().getNbTableau()));
            reservation.setNbExternalEcran(Math.max(0,reunion.getNbEcran() - request.getBestRoom().getNbEcran()));
            reservation.setNbExternalWebcam(Math.max(0,reunion.getNbWebcam() - request.getBestRoom().getNbWebcam()));
            reservation.setNbExternalPieuvre(Math.max(0,reunion.getNbPieuvre() - request.getBestRoom().getNbPieuvre()));
        }

        reservationRepository.save(reservation);

        request.setReservationDTO(ReservationDTO.builder()
                .roomName(request.getBestRoom().getName())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .nbPeople(request.getMeetingDto().getNbPeople())
                .build());

        return true;
    }
}
