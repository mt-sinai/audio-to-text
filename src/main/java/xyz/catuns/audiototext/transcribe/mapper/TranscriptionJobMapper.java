package xyz.catuns.audiototext.transcribe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import xyz.catuns.audiototext.transcribe.dto.TranscriptionJobDetails;
import xyz.catuns.audiototext.transcribe.model.TranscriptionJob;

@Mapper(componentModel = "spring")
public interface TranscriptionJobMapper {

    @Mapping(target = "jobId", source = "id")
    @Mapping(target = "fileName", source = "audioFile.fileName")
    @Mapping(target = "fileId", source = "audioFile.fileId")
    TranscriptionJobDetails mapToDetails(TranscriptionJob job);
}
