package org.hasenbalg.landon.web.service;

import org.hasenbalg.landon.business.domain.RoomReservation;
import org.hasenbalg.landon.business.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ReservationServiceController {


    @Autowired
    private ReservationService reservationService;

    @RequestMapping(method = RequestMethod.GET, value = "/reservations/{date}")
    public List<RoomReservation> getAllReservationsForDate(@PathVariable(value="date", required = false) String dateString){
        return this.reservationService.getRoomReservationsForDate(dateString);
    }
}
