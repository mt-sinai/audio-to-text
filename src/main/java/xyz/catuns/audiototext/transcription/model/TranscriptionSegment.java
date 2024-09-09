package xyz.catuns.audiototext.transcription.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "transcription_segments")
public class TranscriptionSegment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    @JoinColumn(name = "transcription_id")
    private Transcription transcription;

    @Column(name = "start_time", nullable = false)
    private Double startTime;

    @Column(name = "end_time", nullable = false)
    private Double endTime;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "confidence")
    private Double confidence;

    @Column(name = "speaker_label")
    private String speakerLabel;

}
