package pl.ratemyrestaurant.service;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;


public interface StartupJob {
    void onApplicationEvent(ContextRefreshedEvent event);
}
