package pl.ratemyrestaurant.service;

import org.springframework.context.event.ContextRefreshedEvent;


public interface StartupJob {
    void onApplicationEvent(ContextRefreshedEvent event);
}
