package id.my.hendisantika.loaddata.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.my.hendisantika.loaddata.entity.Student;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-load-kafka
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/11/24
 * Time: 06:23
 * To change this template use File | Settings | File Templates.
 */
public class CustomStudentSerializer implements Serializer<Student> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, Student data) {
        try {
            if (data == null) {
                System.out.println("Null received at serializing");
                return null;
            }
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing MessageDto to byte[]");
        }
    }

    @Override
    public void close() {
    }
}
