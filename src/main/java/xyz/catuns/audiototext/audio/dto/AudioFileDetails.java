package xyz.catuns.audiototext.audio.dto;

import xyz.catuns.audiototext.audio.model.AudioFileStatus;

import java.time.LocalDate;

public record AudioFileDetails(
        String fileName,
        Long fileSize,
        Integer duration,
        String format,
        String contentType,
        LocalDate uploadDate,
        AudioFileStatus status
) {}
