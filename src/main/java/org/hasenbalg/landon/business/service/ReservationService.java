package org.hasenbalg.landon.business.service;

import org.hasenbalg.landon.business.domain.RoomReservation;
import org.hasenbalg.landon.data.entity.Guest;
import org.hasenbalg.landon.data.entity.Reservation;
import org.hasenbalg.landon.data.entity.Room;
import org.hasenbalg.landon.data.repository.GuestRepository;
import org.hasenbalg.landon.data.repository.ReservationRepository;
import org.hasenbalg.landon.data.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReservationService {
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");


    @Autowired
    public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }
public List<RoomReservation> getRoomReservationsForDate(String  dateString){
      Iterable<Room> rooms = this.roomRepository.findAll();
    Map<Long, RoomReservation> roomReservationMap = new HashMap<>();
    rooms.forEach(room->{
        RoomReservation roomReservation = new RoomReservation();
        roomReservation.setRoomId(room.getId());
        roomReservation.setRoomName(room.getName());
        roomReservation.setRoomNumber(room.getNumber());
        roomReservationMap.put(room.getId(), roomReservation);
    });

    Date date = new Date();
    try {
        date = DATE_FORMAT.parse(dateString);
    } catch (ParseException e) {
        e.printStackTrace();
    }

    List<Reservation> reservations = this.reservationRepository.findByDate(new java.sql.Date(date.getTime()));
    if(!reservations.isEmpty()){
        Date finalDate = date;
        reservations.forEach(reservation -> {
            Optional<Guest> guest = this.guestRepository.findById(reservation.getGuestId());
            if(guest.isPresent()){


                Guest g = guest.get();
                RoomReservation rr = roomReservationMap.get(reservation.getId());
                rr.setGuestId(g.getId());
                rr.setDate(finalDate);
                rr.setFirstName(g.getFirstName());
                rr.setLastName(g.getLastName());

            }
        });
    }
    List<RoomReservation> roomReservations = new ArrayList<>();
    for(Long roomId:roomReservationMap.keySet()){
        roomReservations.add(roomReservationMap.get(roomId));
    }
    return roomReservations;

}

}
