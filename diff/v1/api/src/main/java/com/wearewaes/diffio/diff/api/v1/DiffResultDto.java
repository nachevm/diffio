package com.wearewaes.diffio.diff.api.v1;

import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class DiffResultDto {

  @NonNull
  DiffResultOutcome outcome;
  List<DiffResultOffsetDto> offsets;
}
