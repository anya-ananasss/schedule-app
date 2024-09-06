//package anya.ooptasks.scheduleapp.model;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.OneToMany;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.List;
//
//@Getter
//@Setter
//@Entity
//public class UserScheduleAssignments {
//    @Id
//    private Integer scheduleId;
//
//
//    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Schedules> daysList;
//}
