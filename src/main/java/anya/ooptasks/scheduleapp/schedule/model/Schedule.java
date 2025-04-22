package anya.ooptasks.scheduleapp.schedule.model;

import anya.ooptasks.scheduleapp.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    @EmbeddedId
    private JointId id;
    private String content;

    @Embeddable
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JointId implements Serializable {
        @Enumerated(EnumType.ORDINAL)
        private DayOfWeek day;
        @Column(columnDefinition = "time without time zone")
        @DateTimeFormat(pattern = "H:mm")
        private LocalTime startTime;
        @Column(columnDefinition = "time without time zone")
        @DateTimeFormat(pattern = "H:mm")
        private LocalTime endTime;
        @ManyToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        private User userId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof JointId jointId)) return false;
            return Objects.equals(day, jointId.day) &&
                    Objects.equals(startTime, jointId.startTime) &&
                    Objects.equals(endTime, jointId.endTime);
        }

        @Override
        public int hashCode() {
            return Objects.hash(day, startTime, endTime);
        }
    }


}


