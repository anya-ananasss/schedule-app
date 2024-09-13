package anya.ooptasks.scheduleapp.schedule.controller;


import anya.ooptasks.scheduleapp.schedule.model.Schedule;
import anya.ooptasks.scheduleapp.schedule.service.ScheduleService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;


    @GetMapping("/schedule")
    public String initSchedulePage(Model model) {
        List<Schedule> allScheduleDays = scheduleService.findAllDays();
        List<DayOfWeek> presentWeekDays = scheduleService.findAllDistinctDaysOfWeek();
        List<DayOfWeek> allDays = Arrays.stream(DayOfWeek.values()).toList();
        List<LocalTime> startTimes = scheduleService.findAllDistinctStartTimes();
        List<LocalTime> endTimes = scheduleService.findAllDistinctEndTimes();
        String username = "default default";
        String[][] contents = new String[startTimes.size()][presentWeekDays.size()];

        for (int day = 0; day < presentWeekDays.size(); day++) {
            for (int time = 0; time < startTimes.size(); time++) {
                contents[time][day] = allScheduleDays.get(0).getContent();
                allScheduleDays.remove(0);
            }
        }
        scheduleService.findAllDays();

        System.out.println(allScheduleDays.size());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            org.springframework.security.core.userdetails.User us = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            username = us.getUsername();

        }



        model.addAttribute("defaultContent", contents);
        model.addAttribute("defaultDays", presentWeekDays);
        model.addAttribute("allDays", allDays);
        model.addAttribute("defaultSingleDays", allScheduleDays);
        model.addAttribute("defaultStartTimes", startTimes);
        model.addAttribute("defaultEndTimes", endTimes);
        model.addAttribute("username", username);

        System.out.println("Я ТУТ БЫЛ!!");
        return "schedule";
    }

    @ResponseBody
    @GetMapping("/get_db_content")
    public List<Schedule.JointId> findAllIds() {
        System.out.println("и пиво пил");
        return scheduleService.findAllIds();
    }

    @ResponseBody
    @PostMapping("/schedule")
    public void examineNewTimeValues(@RequestBody Schedule schedule) {
        LocalTime startTime = schedule.getId().getStartTime();
        LocalTime endTime = schedule.getId().getEndTime();
        System.out.println("по усам стекло");
        scheduleService.examineNewTimeline(startTime, endTime);
    }

    @ResponseBody
    @Transactional
    @PutMapping("/schedule")
    public void saveChanges(@RequestBody Schedule schedule) {
        System.out.println("а в рот не попало");
        String s = schedule.getContent();
        scheduleService.saveChanges(schedule);
    }

    @ResponseBody
    @Transactional
    @DeleteMapping("/schedule")
    public void deleteElement(@RequestBody Schedule element) {
        System.out.println("ббуууба");
        scheduleService.deleteAllById(element.getId());

    }
}


//TODO: в индексе забабахать возможность откатиться к прошлой версии - все содержимое таблицы чистится, таблица в базе данных тоже, и через fetch и в цикле все заполняется значениями из origTableArr


