package com.project.HotelServer.services.customer.booking;

import com.project.HotelServer.dto.ReservationDto;
import com.project.HotelServer.dto.ReservationResponseDto;
import com.project.HotelServer.entity.Reservation;
import com.project.HotelServer.entity.Room;
import com.project.HotelServer.entity.User;
import com.project.HotelServer.enums.ReservationStatus;
import com.project.HotelServer.repository.ReservationRepository;
import com.project.HotelServer.repository.RoomRepository;
import com.project.HotelServer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{

    private final RoomRepository roomRepository;

    private final UserRepository userRepository;

    private final ReservationRepository reservationRepository;

    public static final int SEARCH_RESULT_PER_PAGE = 4;

    public boolean postReservation(ReservationDto reservationDto) {
        Optional<User> optionalUser = userRepository.findById(reservationDto.getUserId());
        Optional<Room> optionalRoom = roomRepository.findById(reservationDto.getRoomId());

        if (optionalRoom.isPresent() && optionalUser.isPresent()) {
            Room room = optionalRoom.get();
            User user = optionalUser.get();

            Reservation reservation = new Reservation();
            reservation.setRoom(room);
            reservation.setUser(user);
            reservation.setCheckInDate(reservationDto.getCheckInDate());
            reservation.setCheckOutDate(reservationDto.getCheckOutDate());
            reservation.setReservationStatus(ReservationStatus.PENDING);

            // Calculate total price based on the number of days
            long days = ChronoUnit.DAYS.between(reservationDto.getCheckInDate(), reservationDto.getCheckOutDate());
            reservation.setPrice(room.getPrice() * days);

            // Save the reservation
            reservationRepository.save(reservation);

            return true;
        }

        // Optionally log that the reservation could not be completed due to missing user or room
        return false;
    }

    public ReservationResponseDto getAllReservationsByUserId(Long userId,int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);

        Page<Reservation> reservationPage = reservationRepository.findAllByUserId(pageable, userId);

        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();

        reservationResponseDto.setReservationDtoList(reservationPage.stream().map(Reservation::getReservationDto).collect(Collectors.toList()));

        reservationResponseDto.setPageNumber(reservationPage.getPageable().getPageNumber());
        reservationResponseDto.setTotalPages(reservationPage.getTotalPages());

        return reservationResponseDto;
    }
}
