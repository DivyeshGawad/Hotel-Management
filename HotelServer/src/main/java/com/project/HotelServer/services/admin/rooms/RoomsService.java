package com.project.HotelServer.services.admin.rooms;

import com.project.HotelServer.dto.RoomDto;
import com.project.HotelServer.dto.RoomResponseDto;

public interface RoomsService {

    boolean postRoom(RoomDto roomDto);

    RoomResponseDto getAllRooms(int pageNumber);

    RoomDto getRoomById(Long id);

    boolean updateRoom(Long id, RoomDto roomDto);

    void deleteRoom(Long id);
}
