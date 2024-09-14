package anya.ooptasks.scheduleapp.schedule.repository;

import anya.ooptasks.scheduleapp.schedule.model.Schedule;
import anya.ooptasks.scheduleapp.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Schedule.JointId> {

    @Query("SELECT DISTINCT day.id.day FROM Schedule day WHERE day.userId = :userId ORDER BY day.id.day")
    List<DayOfWeek> findAllDistinctDays(User userId);

    @Query("SELECT DISTINCT day.id.startTime FROM Schedule day WHERE day.userId = :userId ORDER BY day.id.startTime")
    List<LocalTime> findAllDistinctStartTimes(User userId);

    @Query("SELECT DISTINCT day.id.endTime FROM Schedule day WHERE day.userId = :userId ORDER BY day.id.endTime")
    List<LocalTime> findAllDistinctEndTimes(User userId);

    @Query("SELECT day FROM Schedule day WHERE day.userId = :userId ORDER BY day.id.day, day.id.startTime")
    List<Schedule> findAllOrdered(User userId);

    @Modifying
    @Query ("DELETE FROM Schedule day WHERE day.id = :id and day.userId = :userId")
    void deleteAllById(Schedule.JointId id, User userId);

    @Query ("SELECT day.id FROM Schedule day WHERE day.userId = :userId")
    List<Schedule.JointId> findAllIds (User userId);
}