package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("employers")
public class EmployerController {

    @Autowired
    EmployerRepository employerRepository;

//    pass in employer.id and employer.name
//    @GetMapping
//    public String displayEmployersPage(@RequestParam(required = false) Integer employerId,
//                                       Model model) {
//
//        model.addAttribute("employers", employerRepository.findAll());
////        model.addAttribute("title", "All Employers");
////        if (employerId == null) {
////            model.addAttribute("title", "All Employers");
////            model.addAttribute("employers", employerRepository.findAll());
////        } else {
////            Optional<Employer> result = employerRepository.findById(employerId);
////            if (result.isEmpty()) {
////                model.addAttribute("title", "Invalid Employer ID: " + employerId);
////            } else {
////                Employer employer = result.get();
////                model.addAttribute("title", "Employers: " + employer.getName());
////                model.addAttribute("events", employer.getName());
////            }
////        }
////            return "/employers/index";
////    }
//            return "/employers/index";
//    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("employers", employerRepository.findAll());

        return "employers/index";
    }



    @GetMapping("add")
    public String displayAddEmployerForm(Model model) {
        model.addAttribute(new Employer());
        return "employers/add";
    }

    @PostMapping("add")
    public String processAddEmployerForm(@ModelAttribute @Valid Employer newEmployer,
                                    Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "employers/add";
        }
        employerRepository.save(newEmployer);
        return "redirect:";
    }



    @GetMapping("view/{employerId}")
    public String displayViewEmployer(Model model, @PathVariable int employerId) {

        Optional optEmployer = employerRepository.findById(employerId);
        if (optEmployer.isPresent()) {
            Employer employer = (Employer) optEmployer.get();
            model.addAttribute("employer", employer);
            return "employers/view";
        } else {
            return "redirect:../";
        }

    }
}
