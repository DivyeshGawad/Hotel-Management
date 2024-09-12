package com.project.HotelServer.services.customer.room;

import com.project.HotelServer.dto.RoomResponseDto;

public interface RoomService {

    RoomResponseDto getAvailableRooms(int pageNumber);
}
