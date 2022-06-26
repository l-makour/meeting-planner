package com.canalplus.meetingplanner.repositories;

import com.canalplus.meetingplanner.entities.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer> {


    Meeting findByType(String type);
}
