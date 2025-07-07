package com.example.untitled25.controller;

import com.example.untitled25.model.Teacher;
import com.example.untitled25.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teachers") // 이하경로의 상위경로
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherRepository teacherRepository;

    @GetMapping // 선생 리스트 페이지
    public String list(Model model) {
        model.addAttribute("teachers", teacherRepository.findAll());

        return "teacher-list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("teacher", new Teacher());

        return "teacher-form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Teacher teacher) {
        teacherRepository.save(teacher); // 리퀘스트파람 은 필드 하나의 값을 전달 받는것 이라면,
                                        // 모델어트리뷰트 는 객체 에 맞는 값을 전부 전달 받는다.
        return "redirect:/teachers";
    }


    @GetMapping("/edit/{id}") // {id} 값에 접근하려면 패쓰베리어블 을 써야한다 (리퀘스트파람이 아님)
    public String editForm(@PathVariable int id, Model model) {
        model.addAttribute("teacher", teacherRepository.findById(id));

        return "teacher-form";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Teacher teacher) {
        teacherRepository.update(teacher);

        return "redirect:/teachers";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        try {
            int affected = teacherRepository.deleteById(id);

            if (affected == 0) {
                System.out.println("해당 교사를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
//            model.addAttribute("error", "너 에러 발생:" + e.getMessage()); //모델은 여기서 발생한것을 사용자에게 알려주는 녀석
            System.out.println(e.getMessage());
        }

        return "redirect:/teachers";
    }
    }
}
