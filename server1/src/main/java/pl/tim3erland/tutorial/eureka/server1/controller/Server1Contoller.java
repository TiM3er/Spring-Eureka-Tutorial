package pl.tim3erland.tutorial.eureka.server1.controller;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Applications;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Slf4j
@RestController
public class Server1Contoller {
    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

    @Value("${spring.application.name}")
    private String appName;


    @GetMapping("/address/server1")
    public Applications getServer1Address() {
        return eurekaClient.getApplications(appName);
    }

    @GetMapping("/address/server2")
    public Applications getServer2Address() {
        return eurekaClient.getApplications("server2");
    }

    @GetMapping("/address/DiscoveryServer")
    public Applications getDiscoveryServerAddress() {
        return eurekaClient.getApplications("DiscoveryServer");

    }

    @PostMapping("/test")
    private String test() {
        return new RestTemplate().getForObject(eurekaClient.getApplications("server2")
                .getRegisteredApplications().get(0).getInstances().get(0)
                .getHomePageUrl() + "/test", String.class, Collections.emptyMap());
    }
}
