package com.wearewaes.diffio.diff;

import com.wearewaes.diffio.diff.api.v1.DiffResultDto;
import org.mapstruct.Mapper;

/**
 * Mapping between the V1 API DTO {@link DiffResultDto} and the domain compute result {@link DiffResult}
 */
@Mapper(componentModel = "spring")
interface DiffResultMapper {

  DiffResultDto toDto(DiffResult diffResult);
}
