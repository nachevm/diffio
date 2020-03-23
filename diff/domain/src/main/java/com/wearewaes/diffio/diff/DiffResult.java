package com.wearewaes.diffio.diff;

import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
class DiffResult {

  @NonNull
  DiffResultOutcome outcome;
  List<DiffResultOffset> offsets;
}
