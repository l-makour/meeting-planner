package com.canalplus.meetingplanner.controllers;

import com.canalplus.meetingplanner.dto.ReservationDTO;
import com.canalplus.meetingplanner.dto.MeetingDto;
import com.canalplus.meetingplanner.exceptions.ReservationException;
import com.canalplus.meetingplanner.services.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @PostMapping("reserver")
    public ResponseEntity<ReservationDTO> reserver(@RequestBody MeetingDto reunionDto) {
        try {
            ReservationDTO reservationDTO = reservationService.process(reunionDto);
            reservationDTO.setStatusReservation("OK");
            return ResponseEntity.ok(reservationDTO);
        } catch (ReservationException e) {
            return ResponseEntity.ok(ReservationDTO.builder().statusReservation("KO").errorMessage(e.getMessage()).build());
        }
    }
}
