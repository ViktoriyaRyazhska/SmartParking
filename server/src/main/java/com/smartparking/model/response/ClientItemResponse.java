package com.smartparking.model.response;

import com.smartparking.entity.Client;
import lombok.Data;

import java.util.Base64;

@Data
public class ClientItemResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String image;

    public static ClientItemResponse of(Client client) {
        ClientItemResponse clientItemResponse = new ClientItemResponse();
        clientItemResponse.setId(client.getId());
        clientItemResponse.setFirstName(client.getFirstName());
        clientItemResponse.setLastName(client.getLastName());
        clientItemResponse.setEmail(client.getEmail());
        clientItemResponse.setRole(client.getRole().toString());
        if (client.getImage() != null) {
            clientItemResponse.setImage(Base64.getEncoder().encodeToString(client.getImage()));
        }
        return clientItemResponse;
    }
}
