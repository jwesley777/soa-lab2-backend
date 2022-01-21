package com.example.booking_new.dto.dtoList;

import com.example.booking_new.dto.TicketDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class ticketDTOList {
    @XmlElementWrapper(name = "tickets")
    @XmlElement(name = "ticket")
    private List<TicketDTO> ticketList;
    private long count;
}
