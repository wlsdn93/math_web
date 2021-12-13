package com.math.weakness.web;

import com.math.weakness.dto.ProblemRequestDto;
import com.math.weakness.dto.ProblemResponseDto;
import com.math.weakness.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/problems")
public class ProblemController {

    private final ProblemService problemService;

    @Value("${file.dir}")
    private String fileDir;

    @Autowired
    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @GetMapping
    public String problems(Model model) {
        List<ProblemResponseDto> problems = problemService.findAll();
        model.addAttribute("problems", problems);
        return "problems";
    }

    @GetMapping("/add")
    public String addProblem() {
        return "add-form";
    }


    @PostMapping("/add")
    public String items(@RequestParam MultipartFile problemImageFile,
                        @RequestParam MultipartFile solutionImageFile,
                        @RequestParam String title,
                        @RequestParam String answer,
                        @RequestParam String difficulty
                        ) throws IOException {

        String problemOriginalName = problemImageFile.getOriginalFilename();
        String solutionOriginalNane = solutionImageFile.getOriginalFilename();
        String problemFilePath = fileDir + problemOriginalName;
        String solutionFilePath = fileDir + solutionOriginalNane;

        ProblemRequestDto requestDto = ProblemRequestDto.builder()
                                        .title(title)
                                        .difficulty(Integer.parseInt(difficulty))
                                        .answer(answer)
                                        .problemImageName(problemOriginalName)
                                        .solutionImageName(solutionOriginalNane)
                                        .build();

        problemService.addProblem(requestDto);

        problemImageFile.transferTo(new File(problemFilePath));
        solutionImageFile.transferTo(new File(solutionFilePath));

        return "redirect:/problems";
    }
}