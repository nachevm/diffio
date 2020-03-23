package com.wearewaes.diffio.diff;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wearewaes.diffio.BaseIT;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
class DiffDomainServiceIntegrationTest extends BaseIT {

  private final DiffDomainService diffDomainService;
  private final DiffRepository diffRepository;

  @BeforeEach
  void resetDb() {
    diffRepository.deleteAll();
  }

  @Test
  void saveLeftData_createNewDifIfIdNotFound() {
    diffDomainService.saveLeftData(1L, "A");

    assertThat(diffRepository.findById(1L).get().getLeftData()).isEqualTo("A");
  }

  @Test
  void saveLeftData_updateDifIfIdFound() {
    diffRepository.save(Diff.builder().id(1L).build());

    diffDomainService.saveLeftData(1L, "A");

    assertThat(diffRepository.findById(1L).get().getLeftData()).isEqualTo("A");
  }

  @Test
  void saveRightData_createNewDifIfIdNotFound() {
    diffDomainService.saveRightData(1L, "A");

    assertThat(diffRepository.findById(1L).get().getRightData()).isEqualTo("A");
  }

  @Test
  void saveRightData_updateDifIfIdFound() {
    diffRepository.save(Diff.builder().id(1L).build());

    diffDomainService.saveRightData(1L, "A");

    assertThat(diffRepository.findById(1L).get().getRightData()).isEqualTo("A");
  }

  @Test
  void computeDifference_exceptionIfDiffWithIdNotFound() {
    assertThrows(NoSuchElementException.class, () -> diffDomainService.computeDifference(1L));
  }

  @Test
  void computeDifference_equalOutcomeIfLeftAndRightDataAreTheSame() {
    diffRepository.save(Diff.builder().id(1L).leftData("A").rightData("A").build());

    assertThat(diffDomainService.computeDifference(1L)).isEqualTo(DiffResult.builder().outcome(DiffResultOutcome.EQUAL).build());
  }
}
