package xyz.catuns.audiototext.audio.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import xyz.catuns.audiototext.audio.dto.AudioFileDetails;
import xyz.catuns.audiototext.audio.dto.AudioFileList;
import xyz.catuns.audiototext.audio.model.AudioFile;

@Mapper(componentModel = "spring")
public interface AudioFileMapper {

    @BeanMapping(ignoreByDefault = true)
    AudioFileDetails mapToDetails(AudioFile audioFile);

    @Mapping(target = "files", source = "content")
    @Mapping(target = "page", source = "pageable.pageNumber")
    @Mapping(target = "pageSize", source = "pageable.pageSize", defaultExpression = "java(new ArrayList())")
    AudioFileList mapToList(Page<AudioFile> page);
}
