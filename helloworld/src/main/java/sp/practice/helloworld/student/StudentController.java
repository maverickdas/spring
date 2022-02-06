package sp.practice.helloworld.student;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {
    Logger logger = LoggerFactory.getLogger(StudentController.class);
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        logger.info("Serving GET request");
        return this.studentService.getStudents();
    }

    @PostMapping
    public void register(@RequestBody Student student) {
        logger.info("Serving POST request");
        studentService.addNewStudent(student);
    }

    @DeleteMapping(path="{studentId}")
    public void delete(@PathVariable("studentId") Long id) {
        logger.info("Serving DELETE request");
        studentService.deleteStudent(id);
    }

    @PutMapping(path="{studentId}")
    public void update(
                    @PathVariable("studentId") Long id,
                    @RequestParam(required = false) String name,
                    @RequestParam(required = false) String email) {
        logger.info("Serving PUT request");
        studentService.updateStudent(id, name, email);
    }
}

