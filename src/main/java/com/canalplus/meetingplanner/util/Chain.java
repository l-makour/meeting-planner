package com.canalplus.meetingplanner.util;

import com.canalplus.meetingplanner.exceptions.ReservationException;

public interface Chain<T> {

    void handle(T t) throws ReservationException;
}
