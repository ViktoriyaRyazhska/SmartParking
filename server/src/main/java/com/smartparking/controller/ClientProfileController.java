package com.smartparking.controller;

import com.smartparking.entity.Client;
import com.smartparking.model.request.ClientRequest;
import com.smartparking.model.request.PasswordRequest;
import com.smartparking.model.response.ClientDetailResponse;
import com.smartparking.model.response.InfoResponse;
import com.smartparking.model.response.ParkingDetailResponse;
import com.smartparking.security.exception.AuthorizationEx;
import com.smartparking.service.ClientService;
import com.smartparking.service.FavoriteService;
import com.smartparking.service.ParkingService;
import com.smartparking.service.impl.SpringSecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    ParkingService parkingService;

    @Autowired
    SpringSecurityUserService springSecurityUserService;

    @GetMapping(value = "")
    public ClientDetailResponse getClientDetails() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientService.findOne(SecurityContextHolder.getContext().getAuthentication().getName());
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

    @PostMapping(value = "/update/password")
    public ResponseEntity saveUser(@RequestBody PasswordRequest passwordRequest) {
        try {
            springSecurityUserService.updateClientPassword(passwordRequest);
        } catch (AuthorizationEx e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new InfoResponse(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new InfoResponse("You are successfully updated password"));
    }

    @GetMapping(value = "/favorites")
    public ResponseEntity<?> getFavoritesParkings() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<ParkingDetailResponse> parkings = favoriteService.findFavoritesDetailByClientId(email);
        for (ParkingDetailResponse parking : parkings) {
            parking.setFavoriteName(parkingService.findFavoriteNameByEmailAndParkingId(email, parking.getId()));
        }
        return new ResponseEntity<>(parkings, HttpStatus.OK);
    }
}
