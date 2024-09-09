package xyz.catuns.audiototext.transcription.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import xyz.catuns.audiototext.transcribe.model.TranscriptionJob;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transcriptions")
public class Transcription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                        CascadeType.DETACH, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", referencedColumnName = "id")
    private TranscriptionJob job;

    @Column(name = "text_content", columnDefinition = "TEXT")
    private String textContent;

    @Column(name = "confidence_score")
    private Double confidenceScore;

    @Column(name = "speaker_labels", columnDefinition = "JSON")
    private String speakerLabels;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
