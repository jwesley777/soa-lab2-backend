package com.example.booking_new.dto.dtoList;

import com.example.booking_new.dto.TicketTypeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class TicketTypeDTOList {
    @XmlElementWrapper(name = "ticketTypes")
    @XmlElement(name = "ticketType")
    private List<TicketTypeDTO> ticketTypeList;
}