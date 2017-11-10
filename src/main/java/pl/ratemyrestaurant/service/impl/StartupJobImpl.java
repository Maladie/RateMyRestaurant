package pl.ratemyrestaurant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import pl.ratemyrestaurant.repository.UserTokenRepository;
import pl.ratemyrestaurant.service.StartupJob;

@Service
public class StartupJobImpl implements StartupJob, ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserTokenRepository repository;

    // Removes all token entries from UserToken table invoked after application init
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        repository.deleteAll();
    }
}
