package kr.jinsu.scheduler.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import kr.jinsu.scheduler.schedulers.SchedulerDemo;


@Controller
public class SchedulerController {
    @GetMapping("/")
    public void sampleTest() {

        SchedulerDemo.sample1();
    }
}

