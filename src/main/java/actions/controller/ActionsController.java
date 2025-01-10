package actions.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import actions.entity.Student;
import actions.repository.StudentRepository;

@RestController
@RequestMapping("/api/students")
public class ActionsController {

    @Autowired
    private StudentRepository studentRepository;

    // R: Get all students
    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    @GetMapping(value = "/actions")
    public String actions() {
        return "version: 0.0.1";
    }

    // R: Get student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // C: Create a new student
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    // U: Update a student by ID
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            student.setName(studentDetails.getName());
            return ResponseEntity.ok(studentRepository.save(student));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // D: Delete a student by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
