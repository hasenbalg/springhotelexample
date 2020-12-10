package org.hasenbalg.landon.web.application;

import org.hasenbalg.landon.business.domain.RoomReservation;
import org.hasenbalg.landon.business.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "reservations")
public class ReservationController {


    @Autowired
    private ReservationService reservationService;

    @RequestMapping(method = RequestMethod.GET)
    public String getReservations(@RequestParam(value="date", required = false) String dateString, Model model){



        List<RoomReservation> roomReservations = this.reservationService.getRoomReservationsForDate(dateString);
        model.addAttribute("roomReservations", roomReservations);
        return "reservations";
    }
}
