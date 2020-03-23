package com.wearewaes.diffio.webuibff.v1;

import com.wearewaes.diffio.diff.api.v1.DiffResultDto;
import com.wearewaes.diffio.diff.api.v1.DiffService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/diff")
@RequiredArgsConstructor
class DiffRestController {

  private final DiffService diffService;

  /***
   * Saves the data from the request body on the left side for specified diff
   *
   * @param id The ID of the diff for which to save the data
   * @param base64encodedData Base64 encoded binary data to be saved on the left
   */
  @PutMapping("/{id}/left")
  public void saveLeftData(@PathVariable Long id, @RequestBody byte[] base64encodedData) {
    diffService.saveLeftData(id, base64encodedData);
  }

  /***
   * Saves the data from the request body on the right side for specified diff
   *
   * @param id The ID of the diff for which to save the data
   * @param base64encodedData Base64 encoded binary data to be saved on the right
   */
  @PutMapping("/{id}/right")
  public void saveRightData(@PathVariable Long id, @RequestBody byte[] base64encodedData) {
    diffService.saveRightData(id, base64encodedData);
  }

  /***
   * Computes difference of left and right side data
   *
   * @param id The ID of the diff from which to get left and right side data
   * @return result The outcome with potential offsets and their length
   */
  @GetMapping("/{id}")
  public DiffResultDto computeDifference(@PathVariable Long id) {
    return diffService.computeDifference(id);
  }
}
