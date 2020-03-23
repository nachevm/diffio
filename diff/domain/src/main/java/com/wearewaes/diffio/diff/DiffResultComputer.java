package com.wearewaes.diffio.diff;

import static com.wearewaes.diffio.diff.DiffResultOutcome.DIFFERENT_LENGTH;
import static com.wearewaes.diffio.diff.DiffResultOutcome.DIFFERENT_WITH_OFFSETS;
import static com.wearewaes.diffio.diff.DiffResultOutcome.EQUAL;

import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Internal utility component to compute the differences in a diff entry
 */
@Component
class DiffResultComputer {

  /**
   * Computes difference of left and right side data if there is any difference
   *
   * @param diff The diff from which to get left and right side data
   * @return The outcome with potential offsets and their length
   */
  DiffResult computeDifference(@NonNull Diff diff) {
    return StringUtils.equals(diff.getLeftData(), diff.getRightData()) ?
        DiffResult.builder().outcome(EQUAL).build() :
        computeUnequalDiff(diff);
  }

  /**
   * Computes difference of left and right side data when they're not equal
   *
   * @param diff The diff from which to get left and right side data
   * @return The outcome with potential offsets and their length
   */
  private DiffResult computeUnequalDiff(Diff diff) {
    return diff.isLeftAndRightDataDifferentLength() ?
        DiffResult.builder().outcome(DIFFERENT_LENGTH).build() :
        DiffResult.builder()
            .outcome(DIFFERENT_WITH_OFFSETS)
            .offsets(computeOffsetsForDataOfSameLength(diff.getLeftData(), diff.getRightData()))
            .build();
  }

  /**
   * Computes differences (offsets with their length) for different texts which are of same length by comparing each character
   *
   * @param leftData Text on the left side
   * @param rightData Text on the right side
   * @return List of offsets with their length
   */
  private List<DiffResultOffset> computeOffsetsForDataOfSameLength(@NonNull String leftData, @NonNull String rightData) {
    List<DiffResultOffset> diffResultOffsets = new ArrayList<>();
    int offset = 0;
    int length = 0;
    boolean offsetStarted = false;
    for (int i = 0; i < leftData.length(); i++) {
      if (leftData.charAt(i) != rightData.charAt(i)) {
        if (!offsetStarted) {
          offsetStarted = true;
          offset = i;
          length = 0;
        }
        length++;
      } else if (offsetStarted) {
        offsetStarted = false;
        diffResultOffsets.add(DiffResultOffset.builder().offset(offset).length(length).build());
      }
    }
    if (offsetStarted) {
      diffResultOffsets.add(DiffResultOffset.builder().offset(offset).length(length).build());
    }
    return diffResultOffsets;
  }
}
