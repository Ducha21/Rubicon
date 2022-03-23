package vn.rts.customs.eclare.configuration.dbrouting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import vn.rts.customs.eclare.service.ThietLapThongSoService;

@Component
public class OnStartupConfig implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    ThietLapThongSoService initDataRouting;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initDataRouting.initDataRouting();
    }
}
