package xyz.catuns.audiototext.audio.dto;

import xyz.catuns.audiototext.audio.model.AudioFileStatus;

import java.time.LocalDateTime;

public record AudioFileDetails(
        String fileName,
        Long fileSize,
        String contentType,
        LocalDateTime uploadDate,
        AudioFileStatus status
) {}
