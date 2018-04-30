package com.smartparking.model.response;

import com.smartparking.entity.Client;
import com.smartparking.entity.Favorite;
import lombok.Data;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ClientDetailResponse {

    private Long id;
    private Boolean activated;
    private Long providersId;
    private String firstName;
    private String lastName;
    private String email;
    private String providerName;
    private String role;
    private String image;

    public static ClientDetailResponse of(Client client) {
        ClientDetailResponse response = new ClientDetailResponse();
        response.setId(client.getId());
        response.setActivated(client.getActivated());

        if (client.getProvider() == null) {
            response.setProvidersId(null);
            response.setProviderName(null);
        } else {
            response.setProvidersId(client.getProvider().getId());
            response.setProviderName(client.getProvider().getName());
        }

        response.setFirstName(client.getFirstName());
        response.setLastName(client.getLastName());
        response.setEmail(client.getEmail());
        response.setRole(client.getRole().toString());

        if (client.getImage() != null) {
            response.setImage(Base64.getEncoder().encodeToString(client.getImage()));
        }
        return response;
    }
}
