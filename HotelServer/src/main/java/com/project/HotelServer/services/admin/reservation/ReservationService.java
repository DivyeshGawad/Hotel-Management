package com.project.HotelServer.services.admin.reservation;

import com.project.HotelServer.dto.ReservationResponseDto;

public interface ReservationService {

    ReservationResponseDto getAllReservations(int pageNumber);

    boolean changeReservationStatus(Long id, String status);
}
