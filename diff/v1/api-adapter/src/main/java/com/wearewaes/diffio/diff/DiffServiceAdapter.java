package com.wearewaes.diffio.diff;

import com.wearewaes.diffio.diff.api.v1.DiffResultDto;
import com.wearewaes.diffio.diff.api.v1.DiffService;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Adapts the V1 API {@link DiffService} to the domain {@link DiffDomainService}
 */
@Service
@RequiredArgsConstructor
class DiffServiceAdapter implements DiffService {

  private final DiffDomainService diffDomainService;
  private final DiffResultMapper diffResultMapper;

  @Override
  public void saveLeftData(Long diffId, byte[] base64encodedData) {
    diffDomainService.saveLeftData(diffId, new String(Base64.getDecoder().decode(base64encodedData)));
  }

  @Override
  public void saveRightData(Long diffId, byte[] base64encodedData) {
    diffDomainService.saveRightData(diffId, new String(Base64.getDecoder().decode(base64encodedData)));
  }

  @Override
  public DiffResultDto computeDifference(Long diffId) {
    return diffResultMapper.toDto(diffDomainService.computeDifference(diffId));
  }
}
