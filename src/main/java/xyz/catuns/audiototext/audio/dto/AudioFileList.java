package xyz.catuns.audiototext.audio.dto;

import java.util.List;

public record AudioFileList(
        int page,
        int totalPages,
        int pageSize,
        List<AudioFileDetails> files
) {}
