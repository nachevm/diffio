package com.wearewaes.diffio.diff;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import com.wearewaes.diffio.diff.api.v1.DiffResultDto;
import com.wearewaes.diffio.diff.api.v1.DiffResultOffsetDto;
import java.util.Base64;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DiffServiceAdapterTest {

  public static final String DATA = "data";

  @InjectMocks
  private DiffServiceAdapter diffServiceAdapter;
  @Mock
  private DiffDomainService diffDomainService;
  @Spy
  private DiffResultMapper diffResultMapper = Mappers.getMapper(DiffResultMapper.class);

  @Test
  void saveLeftData_decodesBase64data() {
    diffServiceAdapter.saveLeftData(1L, Base64.getEncoder().encode(DATA.getBytes()));

    verify(diffDomainService).saveLeftData(1L, DATA);
  }

  @Test
  void saveRightData_decodesBase64data() {
    diffServiceAdapter.saveRightData(1L, Base64.getEncoder().encode(DATA.getBytes()));

    verify(diffDomainService).saveRightData(1L, DATA);
  }

  @Test
  void computeDifference_resultMapped() {
    doReturn(
        DiffResult.builder()
            .outcome(DiffResultOutcome.DIFFERENT_WITH_OFFSETS)
            .offsets(List.of(
                DiffResultOffset.builder().offset(0).length(2).build(),
                DiffResultOffset.builder().offset(4).length(3).build())
            ).build()
    ).when(diffDomainService).computeDifference(1L);

    DiffResultDto diffResultDto = diffServiceAdapter.computeDifference(1L);

    verify(diffDomainService).computeDifference(1L);
    assertThat(diffResultDto).isEqualTo(
        DiffResultDto.builder()
            .outcome(com.wearewaes.diffio.diff.api.v1.DiffResultOutcome.DIFFERENT_WITH_OFFSETS)
            .offsets(List.of(
                DiffResultOffsetDto.builder().offset(0).length(2).build(),
                DiffResultOffsetDto.builder().offset(4).length(3).build())
            ).build()
    );
  }
}
