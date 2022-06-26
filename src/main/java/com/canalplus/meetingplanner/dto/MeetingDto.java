package com.canalplus.meetingplanner.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MeetingDto {

    LocalDateTime startDate;
    LocalDateTime endDate;
    Integer nbPeople;
    String type;
}


