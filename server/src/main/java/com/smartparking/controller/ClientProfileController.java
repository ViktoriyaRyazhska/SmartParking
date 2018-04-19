package com.smartparking.controller;

import com.smartparking.entity.Client;
import com.smartparking.model.request.ClientRequest;
import com.smartparking.model.response.ClientDetailResponse;
import com.smartparking.model.response.ParkingDetailResponse;
import com.smartparking.service.ClientService;
import com.smartparking.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ClientProfileController {

    @Autowired
    ClientService clientService;

    @Autowired
    FavoriteService favoriteService;

    @Value("${cross_origin.client}")
    String hostUrl;

    @GetMapping(value = "")
    public ClientDetailResponse getClientDetails() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientService.findOne(email);
        ClientDetailResponse clientDetailResponse = ClientDetailResponse.of(client);
        return clientDetailResponse;
    }

    @PostMapping(value = "/update/{id}")
    public ResponseEntity updateClientProfile(@PathVariable Long id, @RequestBody ClientRequest clientRequest) {
        if (!clientRequest.getFirstName().equals("") && !clientRequest.getLastName().equals("") &&
                !clientRequest.getEmail().equals("")) {
            clientService.updateFromRequest(id, clientRequest);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Empty data input.", HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/favorites")
    public ResponseEntity<?> getFavoritesParkings() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<ParkingDetailResponse> parkings = favoriteService.findFavoritesDetailByClientId(email);
        return new ResponseEntity<>(parkings, HttpStatus.OK);
    }
}
