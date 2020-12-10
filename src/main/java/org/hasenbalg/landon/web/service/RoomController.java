package org.hasenbalg.landon.web.service;

import org.hasenbalg.landon.data.entity.Room;
import org.hasenbalg.landon.data.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RoomController {
    @Autowired
    private RoomRepository repository;

    @RequestMapping(value = "/rooms", method=RequestMethod.GET)
    List<Room> findAll(@RequestParam(required = false) String roomNumber){
        List<Room> rooms = new ArrayList<>();
        if(roomNumber != null){
            Room room = repository.findByNumber(roomNumber);
            if(room != null){
                rooms.add(room);
            }
        }else{
            repository.findAll().forEach(r->rooms.add(r));
        }
        return rooms;
    }
}
