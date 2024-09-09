package xyz.catuns.audiototext.transcribe.dto;

import com.amazonaws.services.transcribe.model.TranscriptionJobStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TranscriptionJobDetails(
    Long jobId,
    String jobName,
    String fileName,
    UUID fileId,
    TranscriptionJobStatus status,
    LocalDateTime startTime,
    String languageCode,
    String transcriptionUri
) {}
