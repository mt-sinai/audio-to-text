package xyz.catuns.audiototext.transcribe.model;

import com.amazonaws.services.transcribe.model.TranscriptionJobStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import xyz.catuns.audiototext.audio.model.AudioFile;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transcription_jobs")
public class TranscriptionJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "audio_file_id")
    private AudioFile audioFile;

    @Column(name = "job_name", nullable = false, unique = true)
    private String jobName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TranscriptionJobStatus status;

    @CreationTimestamp
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "language_code", nullable = false)
    private String languageCode;

    @Override
    public String toString() {
        return "TranscriptionJob{" +
                "id=" + id +
                ", audioFile=" + audioFile.getFileId() +
                ", jobName='" + jobName + '\'' +
                ", status=" + status +
                ", startTime=" + startTime +
                ", languageCode='" + languageCode + '\'' +
                '}';
    }
}
