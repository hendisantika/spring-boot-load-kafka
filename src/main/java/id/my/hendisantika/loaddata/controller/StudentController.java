package id.my.hendisantika.loaddata.controller;

import id.my.hendisantika.loaddata.entity.Student;
import id.my.hendisantika.loaddata.service.LoadDataIntoDB;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-load-kafka
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/11/24
 * Time: 06:25
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class StudentController {
    private final LoadDataIntoDB ldb;
    private final KafkaTemplate<String, Student> template;
    private final NewTopic topic;

    @GetMapping("/save")
    public void save() {
        log.info("---> Starting synchronous data load at {}", Instant.now());
        ldb.saveData();
        log.info("---> Synchronous data load completed at {}", Instant.now());
    }

    @GetMapping("/send-to-kafka-broker")
    public void sendToKafka() {
        List<Student> list = ldb.fetchAll();
        list.stream().forEach(x -> template.send(topic.name(),x));
    }
}
