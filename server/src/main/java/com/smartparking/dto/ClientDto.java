package com.smartparking.dto;

import java.util.List;

public class ClientDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<FavoriteDto> favoriteDto;
    private ProviderDto providerDto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<FavoriteDto> getFavoriteDto() {
        return favoriteDto;
    }

    public void setFavoriteDto(List<FavoriteDto> favoriteDto) {
        this.favoriteDto = favoriteDto;
    }

    public ProviderDto getProviderDto() {
        return providerDto;
    }

    public void setProviderDto(ProviderDto providerDto) {
        this.providerDto = providerDto;
    }
}
