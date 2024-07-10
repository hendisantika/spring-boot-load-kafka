package id.my.hendisantika.loaddata.controller;

import id.my.hendisantika.loaddata.entity.Student;
import id.my.hendisantika.loaddata.service.LoadDataIntoDB;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RestController;

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

@RestController
@RequiredArgsConstructor
public class StudentController {
    private final LoadDataIntoDB ldb;
    private final KafkaTemplate<String, Student> template;
    private final NewTopic topic;
}
