package com.example.untitled25.controller;

import com.example.untitled25.model.Student;
import com.example.untitled25.model.Teacher;
import com.example.untitled25.repository.StudentRepository;
import com.example.untitled25.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentRepository studentRepository; // Student 가 Teacher 도 받는 이유는
    private final TeacherRepository teacherRepository; // Teacher 의 데이터 (Id) 를 받아와야하기 때문

    @GetMapping
    public String list(Model model) {
        model.addAttribute("students", studentRepository.findAll());

        return "student-list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        List<Teacher> teachers = teacherRepository.findAll(); // 선생님 정보를 가져오는 역할
        // 근데 왜 선생 전체 정보를 가져왔나?? : 여기서 고르라고 보여주기위함
        model.addAttribute("student", new Student());
        model.addAttribute("teachers", teachers);
        // 새로운 teachers 가 아닌 기존 teachers 이어서 new 가 없다.

        return "student-form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Student student) {
        studentRepository.save(student);

        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        Student student = studentRepository.findById(id);
        List<Teacher> teachers = teacherRepository.findAll();
        model.addAttribute("student", student);
        model.addAttribute("teachers", teachers);

        return "student-form";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Student student) {
        studentRepository.update(student);

        return "redirect:/students";
    }
    
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        studentRepository.deleteById(id);

        return "redirect:/students";
    }
}