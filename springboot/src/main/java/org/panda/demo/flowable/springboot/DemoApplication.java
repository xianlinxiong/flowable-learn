package org.panda.demo.flowable.springboot;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.panda.demo.flowable.springboot.service.MyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    @Bean
    public CommandLineRunner init(final MyService myservice) {

        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                myservice.createDemoUsers();
            }
        };
    }
}
