package com.example.booking_new.dto.dtoList;

import com.example.booking_new.dto.PersonDTO;
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
public class personDTOList {
    @XmlElementWrapper(name = "persons")
    @XmlElement(name = "person")
    private List<PersonDTO> personList;
}
