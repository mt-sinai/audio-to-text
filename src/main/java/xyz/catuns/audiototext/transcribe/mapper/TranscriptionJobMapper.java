package xyz.catuns.audiototext.transcribe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import xyz.catuns.audiototext.transcribe.dto.TranscriptionJobDetails;
import xyz.catuns.audiototext.transcribe.dto.TranscriptionJobList;
import xyz.catuns.audiototext.transcribe.model.TranscriptionJob;

@Mapper(componentModel = "spring")
public interface TranscriptionJobMapper {

    @Mapping(target = "jobId", source = "id")
    @Mapping(target = "fileName", source = "audioFile.fileName")
    @Mapping(target = "fileId", source = "audioFile.fileId")
    TranscriptionJobDetails mapToDetails(TranscriptionJob job);

    @Mapping(target = "jobs", source = "content")
    @Mapping(target = "page", source = "pageable.pageNumber")
    @Mapping(target = "pageSize", source = "pageable.pageSize")
    TranscriptionJobList mapToList(Page<TranscriptionJob> page);
}
