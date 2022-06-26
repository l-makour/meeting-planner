package com.canalplus.meetingplanner.services.handlers;

import com.canalplus.meetingplanner.enums.MeetingType;
import com.canalplus.meetingplanner.exceptions.ReservationException;
import com.canalplus.meetingplanner.services.MeetingRequest;
import com.canalplus.meetingplanner.util.Handler;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
@Order(0)
public class H0CheckReservationHours implements Handler<MeetingRequest> {

    private static final String TIME_NOT_AVAILABLE = "Impossible de réserver dans ces horaires";

    @Override
    public boolean handle(MeetingRequest request) throws ReservationException {

        if (request.getMeetingDto().getStartDate().getHour() > 19 || request.getMeetingDto().getStartDate().getHour() < 8
                || request.getMeetingDto().getStartDate().isBefore(LocalDateTime.now())|| request.getMeetingDto().getEndDate().isBefore(LocalDateTime.now()) )
            throw new ReservationException(TIME_NOT_AVAILABLE);

        if (MeetingType.RS == MeetingType.valueOf(request.getMeetingDto().getType())){
            request.getMeetingDto().setNbPeople(3);
        }
        return false;
    }
}
