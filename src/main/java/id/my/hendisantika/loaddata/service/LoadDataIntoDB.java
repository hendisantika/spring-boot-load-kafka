package id.my.hendisantika.loaddata.service;

import id.my.hendisantika.loaddata.entity.Student;
import id.my.hendisantika.loaddata.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private int start = 0;

    public void saveData() {
        while(start < 100000) {
            List<Student> student = getNextStudentBatch(start);
            start += batch;
            studentRepository.saveAll(student);
        }
    }

    public List<Student> fetchAll() {
        return studentRepository.findAll();
    }

    private List<Student> getNextStudentBatch(int start) {
        List<Student> student = new ArrayList<Student>();
        for (int i = start; i < start + batch; i++) {
            Student st = Student.builder().name("NAME_" + i).rollNo(i + 1).standard((i + 1) % 10).build();
            student.add(st);
        }
        return student;
    }
}
