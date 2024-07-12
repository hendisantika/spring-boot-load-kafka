package id.my.hendisantika.loaddata.config;

import id.my.hendisantika.loaddata.entity.Student;
import id.my.hendisantika.loaddata.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.Optional;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-load-kafka
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/07/24
 * Time: 18.44
 * To change this template use File | Settings | File Templates.
 */
@Testcontainers
@SpringBootTest
@TestPropertySource(
        properties = {
                "spring.kafka.consumer.auto-offset-reset=earliest"
        }
)
class UserKafkaTestcontainersTest {

    @Container
    static final KafkaContainer kafka = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:7.6.1")
    );
    @Autowired
    private KafkaTemplate<String, Student> kafkaTemplate;

//    @Autowired
//    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private StudentRepository studentRepository;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @BeforeEach
    void setUp() {
        Student student = new Student(1L, "John Wick", 1, 1);
        studentRepository.save(student);
    }

    @Test
    void shouldHandleStudent() {
        Student student = new Student(1L, "John Wick", 1, 1);
        kafkaTemplate.send("student_topic", student);

        await()
                .pollInterval(Duration.ofSeconds(3))
                .atMost(10, SECONDS)
                .untilAsserted(() -> {
                    Optional<Student> optionalStudent = studentRepository.findById(1L);
                    assertThat(optionalStudent).isPresent();
                    assertThat(optionalStudent.get().getName()).isEqualTo("John Wick");
                    assertThat(optionalStudent.get().getRollNo()).isEqualTo(1L);
                    assertThat(optionalStudent.get().getStandard()).isEqualTo(1L);
                });
    }
}
