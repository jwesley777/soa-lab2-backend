package com.example.booking_new.dto.dtoList;

import com.example.booking_new.dto.CoordinatesDTO;
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
public class CoordinatesDTOList {
    @XmlElementWrapper(name = "coordinates")
    @XmlElement(name = "coordinate")
    private List<CoordinatesDTO> coordinatesList;
}
