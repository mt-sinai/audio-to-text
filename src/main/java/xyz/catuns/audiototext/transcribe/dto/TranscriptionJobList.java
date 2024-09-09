package xyz.catuns.audiototext.transcribe.dto;

import java.util.List;

public record TranscriptionJobList(
        int page,
        int totalPages,
        int pageSize,
        List<TranscriptionJobDetails> jobs
) {}
