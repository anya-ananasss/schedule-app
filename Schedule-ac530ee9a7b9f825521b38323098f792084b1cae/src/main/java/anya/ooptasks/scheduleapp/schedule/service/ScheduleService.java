package anya.ooptasks.scheduleapp.schedule.service;


import anya.ooptasks.scheduleapp.schedule.model.Schedule;
import anya.ooptasks.scheduleapp.schedule.repository.ScheduleRepository;
import anya.ooptasks.scheduleapp.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;


@Service
@AllArgsConstructor
public class ScheduleService {
    private final ScheduleRepository repository;

    public void examineNewTimeline(LocalTime startTime, LocalTime endTime, User userId) {
        List<LocalTime> endTimes = findAllDistinctEndTimes(userId);
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
    }

    public void createDefaultSchedule (User user){
       Schedule schedule = new Schedule(new Schedule.JointId(DayOfWeek.MONDAY, LocalTime.parse("08:00"), LocalTime.parse("09:30"), user),
               " ");
       repository.save(schedule);
    }

    public void saveChanges(Schedule updated) {
        repository.save(updated);
    }

    public List<Schedule> findAllDays(User userId) {
        return repository.findAllOrdered(userId);
    }

    public void deleteAllById(Schedule.JointId id) {
        repository.deleteAllById(id);
    }

    public List<DayOfWeek> findAllDistinctDaysOfWeek(User userId) {
        return repository.findAllDistinctDays(userId);
    }

    public List<LocalTime> findAllDistinctStartTimes(User userId) {
        return repository.findAllDistinctStartTimes(userId);
    }

    public List<LocalTime> findAllDistinctEndTimes(User userId) {
        return repository.findAllDistinctEndTimes(userId);
    }

    public List<Schedule.JointId> findAllIds(User userId) {
        return repository.findAllIds(userId);
    }


}
