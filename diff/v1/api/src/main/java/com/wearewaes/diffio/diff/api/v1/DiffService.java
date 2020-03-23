package com.wearewaes.diffio.diff.api.v1;

public interface DiffService {

  /***
   * Saves the data on the left side for the specified diff
   *
   * @param diffId The ID of the diff for which to save the data
   * @param base64encodedData Base64 encoded binary data to be saved on the left
   */
  void saveLeftData(Long diffId, byte[] base64encodedData);

  /***
   * Saves the data on the right side for the specified diff
   *
   * @param diffId The ID of the diff for which to save the data
   * @param base64encodedData Base64 encoded binary data to be saved on the right
   */
  void saveRightData(Long diffId, byte[] base64encodedData);

  /***
   * Computes difference of left and right side data
   *
   * @param diffId The ID of the diff from which to get left and right side data
   * @return result The outcome with potential offsets and their length
   */
  DiffResultDto computeDifference(Long diffId);
}
