package com.example.booking_new.service;

import com.example.booking_new.dto.BookingResponseDTO;
import com.example.booking_new.dto.PersonDTO;
import com.example.booking_new.dto.TicketDTO;
import com.example.booking_new.dto.dtoList.personDTOList;
import com.example.booking_new.dto.dtoList.ticketDTOList;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.net.ssl.SSLContext;
import java.util.ArrayList;
import java.util.Date;

@Service
public class TicketService {


    @Value("${trust.store}")
    private Resource trustStore;

    @Value("${trust.store.password}")
    private String trustStorePassword;

    RestTemplate restTemplate() throws Exception {
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray())
                .build();
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .build();
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);
    }
    //private final RestTemplate restTemplate = new RestTemplate();

    private String BASE_URL = "https://localhost:9669";

    public BookingResponseDTO createMoreVipTicket(int ticketId, int personId) {
//        UriComponentsBuilder builder =
//                UriComponentsBuilder.fromHttpUrl(BASE_URL + "/sell/vip/" + ticketId + "/" + personId);
        boolean gotTicket = false;
        boolean gotPerson = false;

        try {

            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(BASE_URL + "/tickets/" + ticketId);
//            ResponseEntity<TicketDTO> responseEntity = null;
//            responseEntity = restTemplate.exchange(builder.toUriString(),
//                    HttpMethod.GET, null,
//                    new ParameterizedTypeReference<TicketDTO>(){});
            ticketDTOList ticketDtoList = restTemplate().getForObject(builder.toUriString(), ticketDTOList.class);
            TicketDTO ticketDto = ticketDtoList.getTicketList().get(0);
            gotTicket = true;
            TicketDTO newTicketDto = new TicketDTO();
            // assign person with personId
            builder = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/persons/" + personId);
            personDTOList personDTOList = restTemplate().getForObject(builder.toUriString(), com.example.booking_new.dto.dtoList.personDTOList.class);
            PersonDTO personDto = personDTOList.getPersonList().get(0);
            gotPerson = true;
            //newTicketDto.setPerson(ticketDto.getPerson());
            newTicketDto.setPerson(personDto);
            // copy ticket
            newTicketDto.setName(ticketDto.getName());
            newTicketDto.setCoordinates(ticketDto.getCoordinates());
            newTicketDto.setDiscount(ticketDto.getDiscount());

            newTicketDto.setCreationDate((new Date()).toString());
            newTicketDto.setType("VIP");
            try {
                double newPrice = Double.parseDouble(ticketDto.getPrice());
                newTicketDto.setPrice(Double.toString(newPrice * 2.0));
            } catch (Exception e) {
                newTicketDto.setPrice(null);
            }
            ticketDTOList newTicketDtoList = new ticketDTOList(
                    new ArrayList<TicketDTO>(),
                    1
            );
            newTicketDtoList.getTicketList().add(newTicketDto);
            // send request to create new ticket
            builder = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/tickets");
//            responseEntity = restTemplate.exchange(builder.toUriString(),
//                    HttpMethod.POST,
//                    newTicketDto,
//                    )
            HttpEntity<ticketDTOList> newTicketEntity = new HttpEntity<>(newTicketDtoList);
            restTemplate().postForObject(builder.toUriString(),
                    newTicketEntity,
                    ticketDTOList.class
            );
            return new BookingResponseDTO(true, "ok");


        } catch (ResourceAccessException e) {
            e.printStackTrace();
            System.out.println("server is not available");
            return new BookingResponseDTO(false, "server is not available");
        } catch (HttpClientErrorException e) {
            if (e.getRawStatusCode() == 404) {
                if (!gotTicket) {
                    System.out.println("ticket with this id does not exist");
                    return new BookingResponseDTO(false, "ticket with this id does not exist");
                } else if (!gotPerson) {
                    System.out.println("person with this id does not exist");
                    return new BookingResponseDTO(false, "person with this id does not exist");
                } else {
                    System.out.println("404 not found");
                    return new BookingResponseDTO(false, "not found");
                }
            } else {
                System.out.println(e.getStatusText());
                System.out.println(e.getRawStatusCode());
                e.printStackTrace();
                return new BookingResponseDTO(false, e.getStatusText());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("something goes wrong in create double vip ticket");
            return new BookingResponseDTO(false, "something goes wrong in create double vip ticket");
        }

    }

    public BookingResponseDTO deleteTicketsOfPerson(int personId) {
        try {
            UriComponentsBuilder builder =
                    UriComponentsBuilder.fromHttpUrl(BASE_URL + String.format(
                            "/tickets?filterBy=person,%d,%d;", personId, personId
                            ));
            ticketDTOList ticketDtoList = restTemplate().getForObject(builder.toUriString(), ticketDTOList.class);
            for (TicketDTO ticketDTO: ticketDtoList.getTicketList()) {
                builder = UriComponentsBuilder.fromHttpUrl(
                        BASE_URL + "/tickets/" + ticketDTO.getId()
                );
                restTemplate().delete(builder.toUriString());
            }
            return new BookingResponseDTO(true, "ok");

        } catch (ResourceAccessException e) {
            System.out.println("server is not available");
            return new BookingResponseDTO(false, "server is not available");
        } catch (HttpClientErrorException e) {
            if (e.getRawStatusCode() == 404) {
                System.out.println("Not found ticket with this id");
                return new BookingResponseDTO(false, "Not found ticket with this id");
            } else {
                System.out.println(e.getStatusText());
                return new BookingResponseDTO(false, e.getStatusText());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("something goes wrong in delete tickets of person");
            return new BookingResponseDTO(false, "something goes wrong in delete tickets of person");
        }
    }
}
