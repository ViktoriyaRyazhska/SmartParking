package com.smartparking.element;

import com.smartparking.controller.MainController;
import com.smartparking.entity.Event;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

public class RequestItemReader implements ItemReader<Event> {

    @Autowired
    MainController mainController;


    @Override
    public Event read() {
        System.out.println("**********reader***********");
        if (!mainController.getEvents().isEmpty()) {
            return mainController.getEvents().remove(0);
        } else {
            return null;
        }
    }
}
