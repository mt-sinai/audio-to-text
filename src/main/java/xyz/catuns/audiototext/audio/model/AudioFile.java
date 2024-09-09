package xyz.catuns.audiototext.audio.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "audio_files")
public class AudioFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "file_name", unique = true, nullable = false)
    private String fileName;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "format", nullable = false)
    private String format;

    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid = UUID.randomUUID();

    @Column(name = "content_type")
    private String contentType;

    @CreationTimestamp
    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AudioFileStatus status;

    @Column(name = "user_id")
    private Long userId;


}
