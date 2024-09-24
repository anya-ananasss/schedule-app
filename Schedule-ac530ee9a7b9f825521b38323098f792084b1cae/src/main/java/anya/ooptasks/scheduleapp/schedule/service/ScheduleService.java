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

    public void createDefaultSchedule (User user){
       Schedule schedule = new Schedule(new Schedule.JointId(DayOfWeek.MONDAY, LocalTime.parse("08:00"), LocalTime.parse("09:30"), user),
               " ");
       repository.save(schedule);
    }

    public void saveChanges(Schedule updated) {
        repository.save(updated);
    }
    public void updateChanges(Schedule updated) {
        repository.updateSch(updated.getId(), updated.getContent());
    }

    public void updateByTime(LocalTime time, Schedule schedule) {
        repository.updateAllByTime(time, schedule.getId().getStartTime(),
                schedule.getId().getEndTime(), schedule.getContent());
    }


    public List<Schedule> findAllDays(User userId) {
        return repository.findAllOrdered(userId);
    }

    public void deleteAllByTime(LocalTime startTime, LocalTime endTime, User userId) {
        repository.deleteAllByTime(startTime, endTime, userId);
    }
   public void deleteAllByDay(DayOfWeek day, User userId) {
        repository.deleteAllByDay(day, userId);
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

}
