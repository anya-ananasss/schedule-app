package anya.ooptasks.scheduleapp.schedule.repository;

import anya.ooptasks.scheduleapp.schedule.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Schedule.JointId> {

    @Query("SELECT DISTINCT day.id.day FROM Schedule day ORDER BY day.id.day")
    List<DayOfWeek> findAllDistinctDays();

    @Query("SELECT DISTINCT day.id.startTime FROM Schedule day ORDER BY day.id.startTime")
    List<LocalTime> findAllDistinctStartTimes();

    @Query("SELECT DISTINCT day.id.endTime FROM Schedule day ORDER BY day.id.endTime")
    List<LocalTime> findAllDistinctEndTimes();

    @Query("SELECT day FROM Schedule day ORDER BY day.id.day, day.id.startTime")
    List<Schedule> findAllOrdered();

    @Modifying
    @Query ("DELETE FROM Schedule day WHERE day.id = :id")
    void deleteAllById(Schedule.JointId id);

    @Query ("SELECT day.id FROM Schedule day")
    List<Schedule.JointId> findAllIds ();
}