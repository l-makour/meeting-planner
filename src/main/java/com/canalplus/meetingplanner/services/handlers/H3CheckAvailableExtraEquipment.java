package com.canalplus.meetingplanner.services.handlers;

import com.canalplus.meetingplanner.entities.Equipement;
import com.canalplus.meetingplanner.entities.Reservation;
import com.canalplus.meetingplanner.repositories.EquipementRepository;
import com.canalplus.meetingplanner.services.MeetingRequest;
import com.canalplus.meetingplanner.util.Handler;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(30)
public class H3CheckAvailableExtraEquipment implements Handler<MeetingRequest> {
    private final EquipementRepository equipementRepository;

    public H3CheckAvailableExtraEquipment(EquipementRepository equipementRepository) {
        this.equipementRepository = equipementRepository;
    }

    @Override
    public boolean handle(MeetingRequest request) {

        int nbAvailableWebCam = equipementRepository.findById(1).get().getNbWebcam() - request.getReservationList().stream()
                .map(Reservation::getNbExternalWebcam)
                .reduce(0, Integer::sum);

        int nbAvailableTableaux = equipementRepository.findById(1).get().getNbTableau() - request.getReservationList().stream()
                .map(Reservation::getNbExternalTableau)
                .reduce(0, Integer::sum);

        int nbAvailablePieuvres = equipementRepository.findById(1).get().getNbPieuvre() - request.getReservationList().stream()
                .map(Reservation::getNbExternalPieuvre)
                .reduce(0, Integer::sum);

        int nbAvailableEcrans = equipementRepository.findById(1).get().getNbEcran() - request.getReservationList().stream()
                .map(Reservation::getNbExternalEcran)
                .reduce(0, Integer::sum);

        Equipement equipement = Equipement.builder()
                .nbEcran(nbAvailableEcrans)
                .nbPieuvre(nbAvailablePieuvres)
                .nbTableau(nbAvailableTableaux)
                .nbWebcam(nbAvailableWebCam)
                .build();

        request.setAvailableEquipement(equipement);

        return false;
    }
}
