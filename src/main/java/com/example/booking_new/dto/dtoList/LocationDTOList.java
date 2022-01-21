package com.example.booking_new.dto.dtoList;

import com.example.booking_new.dto.LocationDTO;
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
public class LocationDTOList {
    @XmlElementWrapper(name = "locations")
    @XmlElement(name = "location")
    private List<LocationDTO> locationsList;
}
