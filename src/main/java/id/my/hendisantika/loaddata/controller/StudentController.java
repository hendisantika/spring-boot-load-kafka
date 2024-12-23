package id.my.hendisantika.loaddata.controller;

import id.my.hendisantika.loaddata.entity.Student;
import id.my.hendisantika.loaddata.service.LoadDataIntoDB;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Student", description = "Endpoint for managing Student")
public class StudentController {
    private final LoadDataIntoDB ldb;
    private final KafkaTemplate<String, Student> template;
    private final NewTopic topic;

    @GetMapping("/save")
    @Operation(
            summary = "Init Student Data",
            description = "Init Student Data.",
            tags = {"Student"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Student.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Not found", responseCode = "404",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Internal error", responseCode = "500"
                    , content = @Content)
    }
    )
    public void save() {
        log.info("---> Starting synchronous data load at {}", Instant.now());
        ldb.saveData();
        log.info("---> Synchronous data load completed at {}", Instant.now());
    }

    @GetMapping("/send-to-kafka-broker")
    @Operation(
            summary = "Send Student Data to KAFKA",
            description = "Send Student Data to KAFKA.",
            tags = {"Student"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Student.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Not found", responseCode = "404",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Internal error", responseCode = "500"
                    , content = @Content)
    }
    )
    public void sendToKafka() {
        List<Student> list = ldb.fetchAll();
        log.info("---> Fetched {} students", list.size());
        //list.forEach(System.out::println);
        list.stream().forEach(x -> template.send(topic.name(),x));
    }
}
