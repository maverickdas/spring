package sp.practice.helloworld.student;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        // System.out.println(student);
        Optional<Student> res =
             studentRepository.findStudentByEmail(student.getEmail());
        if (res.isPresent()) {
            throw new IllegalStateException("email taken!");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        } else {
            throw new IllegalStateException("student with id=" + id + " not found!");
        }
    }

    @Transactional
    public void updateStudent(Long id, String name, String email) {
        Student student = studentRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException(
                        "student with id=" + id + " does not exist!"
                    ));
        if (name != null
                && name.length() > 0
                && !name.equals(student.getName()))
            student.setName(name);

        if (email != null
                && email.length() > 0
                && !email.equals(student.getEmail())) {
            Optional<Student> opt = studentRepository.findStudentByEmail(email);
            if (opt.isPresent())
                throw new IllegalStateException("email taken!");
            student.setEmail(email);
        }

        // v Not required since Transactional
        // studentRepository.save(student);
    }
}
