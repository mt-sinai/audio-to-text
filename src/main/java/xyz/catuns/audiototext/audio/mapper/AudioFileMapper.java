package xyz.catuns.audiototext.audio.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import xyz.catuns.audiototext.audio.dto.AudioFileDetails;
import xyz.catuns.audiototext.audio.model.AudioFile;

@Mapper(componentModel = "spring")
public interface AudioFileMapper {

    @BeanMapping(ignoreByDefault = true)
    AudioFileDetails mapToDetails(AudioFile audioFile);

    @BeanMapping(ignoreByDefault = true)
    Page<AudioFileDetails> mapToPage(Page<AudioFile> page);
}
