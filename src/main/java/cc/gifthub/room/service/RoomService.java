package cc.gifthub.room.service;

import cc.gifthub.room.dto.RoomJoinDto;

public interface RoomService {
    void createRoom(Long currentUserId);


    void enterRoom(Long currentUserId, RoomJoinDto roomJoinDto);

    void exitRoom(Long currentUserId, Long room_id);

}
