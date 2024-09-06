package anya.ooptasks.scheduleapp.repository;

import anya.ooptasks.scheduleapp.model.Schedules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface SchedulesRepository extends JpaRepository<Schedules, Schedules.JointId> {

    @Query("SELECT DISTINCT day.id.day FROM Schedules day ORDER BY day.id.day")
    List<DayOfWeek> findAllDistinctDays();

    @Query("SELECT DISTINCT day.id.startTime FROM Schedules day ORDER BY day.id.startTime")
    List<LocalTime> findAllDistinctStartTimes();

    @Query("SELECT DISTINCT day.id.endTime FROM Schedules day ORDER BY day.id.endTime")
    List<LocalTime> findAllDistinctEndTimes();

    @Query("SELECT day FROM Schedules day ORDER BY day.id.day, day.id.startTime")
    List<Schedules> findAllOrdered();

    @Modifying
    @Query ("DELETE FROM Schedules day WHERE day.id = :id")
    void deleteAllById(Schedules.JointId id);

    @Query ("SELECT day.id FROM Schedules day")
    List<Schedules.JointId> findAllIds ();
}