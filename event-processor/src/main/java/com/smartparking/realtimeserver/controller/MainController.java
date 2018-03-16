package com.smartparking.realtimeserver.controller;

import com.smartparking.entity.Event;
import com.smartparking.entity.Occasion;
import com.smartparking.entity.Parking;
import com.smartparking.entity.Spot;
import com.smartparking.realtimeserver.element.InRequest;
import com.smartparking.repository.EventRepository;
import com.smartparking.service.ParkingService;
import com.smartparking.service.SpotService;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class MainController {

    private Map<Long, String> tokens;
    private List<InRequest> requests = new ArrayList<>();
    private Map<Long, Spot> spots;

    @Autowired
    private ParkingService parkingService;

    @Autowired
    private SpotService spotService;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Autowired
    private EventRepository eventRepository;


    @PostConstruct
    public void init() {
        tokens = parkingService.findAll().stream()
                .collect(Collectors.toMap(Parking::getId, Parking::getToken));
        spots = spotService.findAll().stream()
                .collect(Collectors.toMap(Spot::getId, s -> s));
    }

    @RequestMapping("parking/update")
    public ResponseEntity parkingUpdated(@RequestParam Long parkingId) {
        String token;
        token = parkingService.findById(parkingId).getToken();
        if (token == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            tokens.put(parkingId, token);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @RequestMapping("spot/add")
    public ResponseEntity spotUpdate(@RequestParam Long spotId) {
        Spot spot = spotService.findById(spotId);
        if (spot == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            spots.put(spotId, spot);
            return new ResponseEntity(HttpStatus.OK);
        }
    }
//
//    @RequestMapping("parking/delete")
//    public ResponseEntity parkingDelete(@RequestParam Long parkingId){
//        tokens.remove(parkingId);
//        spots = spotService.findAll().stream()
//                .collect(Collectors.toMap(Spot::getId, s -> s));
//        return new ResponseEntity(HttpStatus.OK);
//    }
//
//    @RequestMapping("spot/delete")
//    public ResponseEntity spotDelete(@RequestParam Long spotId){
//        spots.remove(spotId);
//        return new ResponseEntity(HttpStatus.OK);
//    }



    @RequestMapping(value = "spot/update", method = RequestMethod.POST)
    public ResponseEntity processingInRequests(@RequestParam String id,
                                               @RequestParam String parkingToken) {

        System.out.println(id);
        System.out.println(parkingToken);
        Long spotId = null;
        if (id != null) {
            try {
                spotId = Long.valueOf(id);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

//        Spot spot = spots.get(spotId);
//        if (spot!=null && tokens.containsValue(parkingToken)){
//            requests.add(new InRequest(spotId, parkingToken,LocalDateTime.now().toInstant(ZoneOffset.UTC)));
//            return new ResponseEntity(HttpStatus.OK);
//        } else {
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        }

        System.out.println(id);
        System.out.println(parkingToken);
        System.out.println(spots.get(spotId).getId());
        Event event = new Event();
        event.setSpot(spots.get(spotId));
        event.setOccasion(Occasion.ARRIVED);
        event.setCurrentEventTime();

        eventRepository.save(event);

        return new ResponseEntity(HttpStatus.OK);

//        if (spot == null) {
//            spot = spotService.findById(spotId);
//            if (spot == null) {
//                return new ResponseEntity(HttpStatus.BAD_REQUEST);
//            } else {
//                spots.put(spotId, spot);
//            }
//        }
//
//        if (spot.getParking().getToken().equals(parkingToken)) {
//            Event event = eventService.findBySpotId(spotId);
//            if (event == null) {
//                event = new Event();
//                event.setSpot(spot);
//                event.setCurrentArrivalTime();
//                eventService.save(event);
//            } else {
//                event.setCurrentDepartureTime();
//            }
//            return new ResponseEntity(HttpStatus.OK);
//        } else {
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        }
    }

    public List<InRequest> getRequests() {
        return requests;
    }

    public Map<Long, Spot> getSpots() {
        return spots;
    }

    @RequestMapping("/test/request")
    public void testRequest() {
        System.out.println("**************************************");
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("http://localhost:8081/spot/update");
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("id", "100"));
        urlParameters.add(new BasicNameValuePair("parkingToken", "qwerty"));
    }

    @RequestMapping("/test/flush")
    public void testFlush() {
        try {
            jobLauncher.run(job, new JobParameters());
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }

}