package anya.ooptasks.scheduleapp.controller;


import anya.ooptasks.scheduleapp.model.Schedules;
import anya.ooptasks.scheduleapp.service.SchedulesService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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
    private final SchedulesService schedulesService;


    @GetMapping
    public String findAllDays(Model model) {
        List<Schedules> allScheduleDays = schedulesService.findAllDays();
        List<DayOfWeek> presentWeekDays = schedulesService.findAllDistinctDaysOfWeek();
        List<DayOfWeek> allDays = Arrays.stream(DayOfWeek.values()).toList();
        List<LocalTime> startTimes = schedulesService.findAllDistinctStartTimes();
        List<LocalTime> endTimes = schedulesService.findAllDistinctEndTimes();

        String[][] contents = new String[startTimes.size()][presentWeekDays.size()];

        for (int day = 0; day < presentWeekDays.size(); day++) {
            for (int time = 0; time < startTimes.size(); time++) {
                contents[time][day] = allScheduleDays.get(0).getContent();
                allScheduleDays.remove(0);
            }
        }
        schedulesService.findAllDays();

        System.out.println(allScheduleDays.size());
        for (int i = 0; i < presentWeekDays.size(); i++) {
            System.out.println(presentWeekDays.get(i));
            System.out.println(startTimes.get(i));
            System.out.println(endTimes.get(i));
            System.out.println("хыэ)");
        }

        model.addAttribute("defaultContent", contents);
        model.addAttribute("defaultDays", presentWeekDays);
        model.addAttribute("allDays", allDays);
        model.addAttribute("defaultSingleDays", allScheduleDays);
        model.addAttribute("defaultStartTimes", startTimes);
        model.addAttribute("defaultEndTimes", endTimes);

        System.out.println("Я ТУТ БЫЛ!!");
        return "index";
    }

    @ResponseBody
    @GetMapping("/get_db_content")
    public List<Schedules.JointId> findAllIds() {
        System.out.println("и пиво пил");
        return schedulesService.findAllIds();
    }

    @ResponseBody
    @PostMapping()
    public void examineNewTimeValues(@RequestBody Schedules schedules) {
        LocalTime startTime = schedules.getId().getStartTime();
        LocalTime endTime = schedules.getId().getEndTime();
        System.out.println("по усам стекло");
        schedulesService.examineNewTimeline(startTime, endTime);
    }

    @ResponseBody
    @Transactional
    @PutMapping()
    public void saveChanges(@RequestBody Schedules schedules) {
        System.out.println("а в рот не попало");
        String s = schedules.getContent();
        schedulesService.saveChanges(schedules);
    }

    @ResponseBody
    @Transactional
    @DeleteMapping()
    public void deleteElement(@RequestBody Schedules element) {
        System.out.println("ббуууба");
        schedulesService.deleteAllById(element.getId());

    }
}


//TODO: в индексе забабахать возможность откатиться к прошлой версии - все содержимое таблицы чистится, таблица в базе данных тоже, и через fetch и в цикле все заполняется значениями из origTableArr


