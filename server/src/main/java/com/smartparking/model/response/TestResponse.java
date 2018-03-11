package com.smartparking.model.response;

public class TestResponse {

    private int number;
    private String string;

    public TestResponse() {
    }

    public TestResponse(int number, String string) {
        this.number = number;
        this.string = string;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
