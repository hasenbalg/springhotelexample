package org.hasenbalg.landon.web.application;

import org.hasenbalg.landon.business.domain.RoomReservation;
import org.hasenbalg.landon.business.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {
    @MockBean
    private ReservationService reservationService;
    @Autowired
    private MockMvc mockMvc;
    @Test
    void getReservations() throws Exception{
   Date date = new Date(2020, 12,10);
        List<RoomReservation> mockRoomReservations = new ArrayList<>();
        RoomReservation r = new RoomReservation();
        r.setLastName("huhu");
        r.setFirstName("hihi");
        r.setDate(date);
        r.setGuestId(1);
        r.setRoomNumber("h2");
        r.setRoomName("nibnu");
        r.setRoomId(100);
        mockRoomReservations.add(r);

        given(reservationService.getRoomReservationsForDate("2020-12-10")).willReturn(mockRoomReservations);
this.mockMvc.perform(get("/reservations?date=2020-12-10")).andExpect(status().isOk()).andExpect(content().string(containsString("huhu")));
    }
}