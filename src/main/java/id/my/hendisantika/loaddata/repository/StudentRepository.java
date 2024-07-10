package id.my.hendisantika.loaddata.repository;

import id.my.hendisantika.loaddata.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

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
public interface StudentRepository extends JpaRepository<Student, Long> {
}
