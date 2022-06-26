package com.canalplus.meetingplanner.util;

import com.canalplus.meetingplanner.exceptions.ReservationException;

public interface Handler<T> {

    boolean handle(T request) throws ReservationException;
}
