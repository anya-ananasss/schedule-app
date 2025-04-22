package anya.ooptasks.scheduleapp.schedule.controller;


import anya.ooptasks.scheduleapp.schedule.model.Schedule;
import anya.ooptasks.scheduleapp.schedule.service.ScheduleService;
import anya.ooptasks.scheduleapp.user.model.User;
import anya.ooptasks.scheduleapp.user.service.UserService;
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
import java.util.Objects;

@Controller
@AllArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final UserService userService;

    @GetMapping("/schedule")
    public String initSchedulePage(Model model) {
        String username = "root";
        int userId = 0;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            org.springframework.security.core.userdetails.User us = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            username = us.getUsername();
            userId = userService.findIdByUsername(username);
        }
        User user = userService.findUserById(userId);
        List<Schedule> allScheduleDays = scheduleService.findAllDays(user);
        List<DayOfWeek> presentWeekDays = scheduleService.findAllDistinctDaysOfWeek(user);
        List<DayOfWeek> allDays = Arrays.stream(DayOfWeek.values()).toList();
        List<LocalTime> startTimes = scheduleService.findAllDistinctStartTimes(user);
        List<LocalTime> endTimes = scheduleService.findAllDistinctEndTimes(user);
        String[][] contents = new String[startTimes.size()][presentWeekDays.size()];

        for (int day = 0; day < presentWeekDays.size(); day++) {
            for (int time = 0; time < startTimes.size(); time++) {
                contents[time][day] = allScheduleDays.get(0).getContent();
                allScheduleDays.remove(0);
            }
        }
        scheduleService.findAllDays(user);


        model.addAttribute("defaultContent", contents);
        model.addAttribute("defaultDays", presentWeekDays);
        model.addAttribute("allDays", allDays);
        model.addAttribute("defaultSingleDays", allScheduleDays);
        model.addAttribute("defaultStartTimes", startTimes);
        model.addAttribute("defaultEndTimes", endTimes);
        model.addAttribute("username", username);
        model.addAttribute("userId", userId);

        return "schedule";
    }

    @ResponseBody
    @Transactional
    @PutMapping("/schedule/{userId}")
    public void updateChanges(@RequestBody Schedule schedule, @PathVariable int userId) {
        schedule.getId().setUserId(userService.findUserById(userId));
        if (Objects.isNull(schedule.getId().getStartTime())){
            scheduleService.updateByTime(schedule.getId().getEndTime(), schedule);
        } else if(Objects.isNull(schedule.getId().getEndTime())) {
            scheduleService.updateByTime(schedule.getId().getStartTime(), schedule);
        } else {
            scheduleService.updateChanges(schedule);
        }
    }
    @ResponseBody
    @Transactional
    @PostMapping("/schedule/{userId}")
    public void saveChanges(@RequestBody Schedule schedule, @PathVariable int userId) {
        schedule.getId().setUserId(userService.findUserById(userId));
        scheduleService.saveChanges(schedule);
    }

    @ResponseBody
    @Transactional
    @DeleteMapping("/schedule/{userId}")
    public void deleteElement(@RequestBody Schedule schedule, @PathVariable int userId) {
        schedule.getId().setUserId(userService.findUserById(userId));
        if (Objects.isNull(schedule.getId().getDay())) {
            scheduleService.deleteAllByTime(schedule.getId().getStartTime(),
                    schedule.getId().getEndTime(),
                    schedule.getId().getUserId());
        } else if (Objects.isNull(schedule.getId().getStartTime())) {
            scheduleService.deleteAllByDay(schedule.getId().getDay(),
                    schedule.getId().getUserId());
        }
    }
}


