package id.my.hendisantika.loaddata.service;

import id.my.hendisantika.loaddata.entity.Student;
import id.my.hendisantika.loaddata.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-load-kafka
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/11/24
 * Time: 06:20
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class LoadDataIntoDB {

    private final StudentRepository studentRepository;

    private final int batch = 1000;
    private final int start = 0;

    public void saveData() {
        while(start < 100000) {
            List<Student> student = getNextStudentBatch(start);
            start+=batch;
            studentRepository.saveAll(student);
        }
    }

    public List<Student> fetchAll() {
        return dbRepo.findAll();

    }
}
