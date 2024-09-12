package com.project.HotelServer.services.admin.rooms;

import com.project.HotelServer.dto.RoomDto;
import com.project.HotelServer.dto.RoomResponseDto;
import com.project.HotelServer.entity.Room;
import com.project.HotelServer.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomsServiceImpl implements RoomsService {

    private static final Logger logger = LoggerFactory.getLogger(RoomsServiceImpl.class);

    private final RoomRepository roomRepository;

    @Override
    public boolean postRoom(RoomDto roomDto) {
        try {
            Room room = new Room();

            room.setName(roomDto.getName());
            room.setType(roomDto.getType());
            room.setPrice(roomDto.getPrice());
            room.setAvailable(true);

            roomRepository.save(room);
            return true;
        } catch (Exception e) {
            // Log the error for debugging purposes
            logger.error("Error occurred while saving the room: ", e);
            return false;
        }
    }

    public RoomResponseDto getAllRooms(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber, 3);
        Page<Room> roomPage = roomRepository.findAll(pageable);

        RoomResponseDto roomResponseDto = new RoomResponseDto();
        roomResponseDto.setPageNumber(roomPage.getPageable().getPageNumber());
        roomResponseDto.setTotalPages(roomPage.getTotalPages());
        roomResponseDto.setRoomDtoList(roomPage.stream().map(Room::getRoomDto).collect(Collectors.toList()));

        return roomResponseDto;
    }

    public RoomDto getRoomById(Long id){
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if(optionalRoom.isPresent()){
            return optionalRoom.get().getRoomDto();
        }else {
            throw new EntityNotFoundException("Room not Present");
        }
    }

    public boolean updateRoom(Long id, RoomDto roomDto){
        Optional<Room> optionalRoom = roomRepository.findById(id);

        if(optionalRoom.isPresent()){
            Room existingRoom = optionalRoom.get();

            existingRoom.setName(roomDto.getName());
            existingRoom.setPrice(roomDto.getPrice());
            existingRoom.setType(roomDto.getType());

            roomRepository.save(existingRoom);
            return true;
        }
        return false;
    }

    public void deleteRoom(Long id){
        Optional<Room> optionalRoom = roomRepository.findById(id);

        if(optionalRoom.isPresent()){
            roomRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Room not present");
        }
    }
}
