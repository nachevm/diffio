package com.wearewaes.diffio.diff;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
class DiffResultOffset {

  int offset;
  int length;
}
