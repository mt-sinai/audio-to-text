package xyz.catuns.audiototext.transcribe.mapper;

import org.mapstruct.Mapper;
import xyz.catuns.audiototext.transcribe.dto.TranscriptionJobDetails;
import xyz.catuns.audiototext.transcribe.model.TranscriptionJob;

@Mapper(componentModel = "spring")
public interface TranscriptionJobMapper {

    TranscriptionJobDetails mapToDetails(TranscriptionJob job);
}
