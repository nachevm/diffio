package com.wearewaes.diffio.diff;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DiffResultComputerTest {

  private final DiffResultComputer diffResultComputer = new DiffResultComputer();

  @Test
  void computeDifference_equalOutcomeIfLeftAndRightDataAreTheSame() {
    assertThat(diffResultComputer.computeDifference(Diff.builder().leftData("A").rightData("A").build()))
        .isEqualTo(DiffResult.builder().outcome(DiffResultOutcome.EQUAL).build());
  }

  @Test
  void computeDifference_equalOutcomeIfLeftAndRightDataAreNull() {
    assertThat(diffResultComputer.computeDifference(Diff.builder().build()))
        .isEqualTo(DiffResult.builder().outcome(DiffResultOutcome.EQUAL).build());
  }

  @Test
  void computeDifference_differentLengthOutcomeIfOnlyLeftDataIsNull() {
    assertThat(diffResultComputer.computeDifference(Diff.builder().rightData("A").build()))
        .isEqualTo(DiffResult.builder().outcome(DiffResultOutcome.DIFFERENT_LENGTH).build());
  }

  @Test
  void computeDifference_differentLengthOutcomeIfOnlyRightDataIsNull() {
    assertThat(diffResultComputer.computeDifference(Diff.builder().leftData("A").build()))
        .isEqualTo(DiffResult.builder().outcome(DiffResultOutcome.DIFFERENT_LENGTH).build());
  }

  @Test
  void computeDifference_differentLengthOutcomeIfBothNotNullAndDifferentLength() {
    assertThat(diffResultComputer.computeDifference(Diff.builder().leftData("Left").rightData("Right").build()))
        .isEqualTo(DiffResult.builder().outcome(DiffResultOutcome.DIFFERENT_LENGTH).build());
  }

  @ParameterizedTest
  @MethodSource("getDiffsWithExpectedOffsets")
  void computeDifference_differentWithOffsetsOutcomeIfSameLengthButDifferentData(String leftData, String rightData,
      List<DiffResultOffset> expectedOffsets) {
    assertThat(diffResultComputer.computeDifference(Diff.builder().leftData(leftData).rightData(rightData).build()))
        .isEqualTo(DiffResult.builder().outcome(DiffResultOutcome.DIFFERENT_WITH_OFFSETS).offsets(expectedOffsets).build());
  }

  private static Stream<Arguments> getDiffsWithExpectedOffsets() {
    return Stream.of(
        Arguments.of("aaa", "bbb", List.of(DiffResultOffset.builder().offset(0).length(3).build())),
        Arguments.of("acc dc", "abb dc", List.of(DiffResultOffset.builder().offset(1).length(2).build())),
        Arguments.of("a1a bbb zzz yyy", "a2a ccc zzz xxx", List.of(
            DiffResultOffset.builder().offset(1).length(1).build(),
            DiffResultOffset.builder().offset(4).length(3).build(),
            DiffResultOffset.builder().offset(12).length(3).build()
        ))
    );
  }
}
