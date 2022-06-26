package com.canalplus.meetingplanner.services;

import com.canalplus.meetingplanner.dto.ReservationDTO;
import com.canalplus.meetingplanner.dto.MeetingDto;
import com.canalplus.meetingplanner.exceptions.ReservationException;
import com.canalplus.meetingplanner.util.Chain;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {


    private final Chain<MeetingRequest> requestChain;


    public ReservationService(Chain<MeetingRequest> requestChain) {
        this.requestChain = requestChain;
    }


    public ReservationDTO process(MeetingDto meetingDto) throws ReservationException {

        MeetingRequest meetingRequest = new MeetingRequest();
        meetingRequest.setMeetingDto(meetingDto);
        requestChain.handle(meetingRequest);
        return meetingRequest.getReservationDTO();
    }


}
