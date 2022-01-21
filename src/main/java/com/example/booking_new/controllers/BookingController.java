package com.example.booking_new.controllers;

import com.example.booking_new.dto.*;
import com.example.booking_new.dto.dtoList.ticketDTOList;
import com.example.booking_new.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping(value = "/booking", produces = MediaType.APPLICATION_XML_VALUE)
public class BookingController {
    private final TicketService ticketService;

    @Autowired
    public BookingController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @CrossOrigin
    @GetMapping("/sell/vip/{ticket-id}/{person-id}")
    public BookingResponseDTO doubleVipTicket(
            @PathVariable(name="ticket-id") int ticketId,
            @PathVariable(name="person-id") int personId
    ) {
        return ticketService.createMoreVipTicket(ticketId, personId);
//        return new BookingResponseDTO(true, "ok");
    }

    @CrossOrigin
    @GetMapping("/{ticket-id}")
    public ticketDTOList test(
            @PathVariable(name="ticket-id") int ticketId
    ) {
        LocationDTO loc = new LocationDTO(
                "0","0","0","0"
        );
        PersonDTO person = new PersonDTO(
                "0","0","0",loc
        );
        CoordinatesDTO coord = new CoordinatesDTO(
                "0","0","0"
        );
        TicketDTO ticketDTO = new TicketDTO(
                "0","0","0",coord,
                "0","0","0",person
        );
        ticketDTOList ticketDTOList = new ticketDTOList(
                new ArrayList<TicketDTO>(),
                1
        );
        ticketDTOList.getTicketList().add(ticketDTO);
        return ticketDTOList;
    }

    @CrossOrigin
    @GetMapping("/person/{person-id}/cancel")
    public BookingResponseDTO cancelTicketsOfPerson(
            @PathVariable(name="person-id") int personId
    ) {
        return ticketService.deleteTicketsOfPerson(personId);
        //return new BookingResponseDTO(true, "ok");
    }
}
