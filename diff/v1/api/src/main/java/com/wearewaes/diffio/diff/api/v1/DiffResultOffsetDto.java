package com.wearewaes.diffio.diff.api.v1;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DiffResultOffsetDto {

  int offset;
  int length;
}
