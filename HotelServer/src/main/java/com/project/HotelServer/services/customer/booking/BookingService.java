package com.project.HotelServer.services.customer.booking;

import com.project.HotelServer.dto.ReservationDto;
import com.project.HotelServer.dto.ReservationResponseDto;

public interface BookingService {

    boolean postReservation(ReservationDto reservationDto);

    ReservationResponseDto getAllReservationsByUserId(Long userId, int pageNumber);
}
