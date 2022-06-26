package com.canalplus.meetingplanner.entities;

import com.canalplus.meetingplanner.enums.MeetingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime effectiveEndDate;
    @Enumerated(EnumType.STRING)
    private MeetingType meetingType;
    private Integer nbExternalWebcam;
    private Integer nbExternalTableau;
    private Integer nbExternalPieuvre;
    private Integer nbExternalEcran;

    @ManyToOne
    @JoinColumn(name = "id_room")
    private Room room;

}
