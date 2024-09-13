package anya.ooptasks.scheduleapp.schedule.service;


import anya.ooptasks.scheduleapp.schedule.model.Schedule;
import anya.ooptasks.scheduleapp.schedule.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;


@Service
@AllArgsConstructor
public class ScheduleService {
    private final ScheduleRepository repository;

    public void examineNewTimeline(LocalTime startTime, LocalTime endTime) {
        List<LocalTime> endTimes = findAllDistinctEndTimes();
        if (startTime == null || endTime == null) {
            throw new RuntimeException("Start or end time is null");
        }
        if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
            throw new RuntimeException("Start time must be less than end time");
        }

        if (endTimes.size() > 1) {
            LocalTime prevEndTime = endTimes.get(endTimes.size() - 1);
            if (prevEndTime.isAfter(startTime)) {
                throw new RuntimeException("Current start time must be less than previous end time");
            }

        }

        System.out.println("эй гайс у меня все найс");
    }

    public void saveChanges(Schedule updated) {
        repository.save(updated);
    }

    public List<Schedule> findAllDays() {
        System.out.println("приплыли");
        return repository.findAllOrdered();
    }

    public void deleteAllById(Schedule.JointId id) {
        repository.deleteAllById(id);
    }

    public List<DayOfWeek> findAllDistinctDaysOfWeek() {
        return repository.findAllDistinctDays();
    }

    public List<LocalTime> findAllDistinctStartTimes() {
        return repository.findAllDistinctStartTimes();
    }

    public List<LocalTime> findAllDistinctEndTimes() {
        return repository.findAllDistinctEndTimes();
    }

    public List<Schedule.JointId> findAllIds() {
        return repository.findAllIds();
    }


}
