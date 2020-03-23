package com.wearewaes.diffio.diff;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
class DiffDomainService {

  private final DiffRepository diffRepository;
  private final DiffResultComputer diffResultComputer;

  /***
   * Saves the data on the left side for the specified diff. Creates a new entry if diff with that id does not exist.
   *
   * @param id The ID of the diff for which to save the data
   * @param data Text to be saved on the left
   * @return the saved diff entry
   */
  @Transactional
  public Diff saveLeftData(@NonNull Long id, String data) {
    log.info("Saving on the left: id:{} data:{}", id, data);
    return diffRepository.findById(id)
        .map(diff -> diff.setLeftData(data))
        .orElseGet(() -> diffRepository.save(Diff.builder().id(id).leftData(data).build()));
  }

  /***
   * Saves the data on the right side for the specified diff. Creates a new entry if diff with that id does not exist.
   *
   * @param id The ID of the diff for which to save the data
   * @param data Text to be saved on the right
   * @return the saved diff entry
   */
  @Transactional
  public Diff saveRightData(@NonNull Long id, String data) {
    log.info("Saving on the right: id:{} data:{}", id, data);
    return diffRepository.findById(id)
        .map(diff -> diff.setRightData(data))
        .orElseGet(() -> diffRepository.save(Diff.builder().id(id).rightData(data).build()));
  }

  /***
   * Computes difference of left and right side data
   *
   * @throws java.util.NoSuchElementException if diff entry cannot be found
   * @param id The ID of the diff from which to get left and right side data
   * @return result The outcome with potential offsets and their length
   */
  @Transactional(readOnly = true)
  public DiffResult computeDifference(@NonNull Long id) {
    return diffRepository.findById(id).map(diffResultComputer::computeDifference).orElseThrow();
  }
}
