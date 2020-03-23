package com.wearewaes.diffio.webuibff.v1;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.wearewaes.diffio.diff.api.v1.DiffResultDto;
import com.wearewaes.diffio.diff.api.v1.DiffResultOffsetDto;
import com.wearewaes.diffio.diff.api.v1.DiffResultOutcome;
import com.wearewaes.diffio.diff.api.v1.DiffService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DiffRestControllerIntegrationTest extends BaseRestControllerIT {

  @Mock
  private DiffService diffService;

  @BeforeEach
  void initController() {
    mockMvc = buildMockMvc(new DiffRestController(diffService));
  }

  @Test
  void saveLeftData() throws Exception {
    mockMvc.perform(put("/v1/diff/1/left").content("A")).andExpect(status().isOk());
    verify(diffService).saveLeftData(1L, "A".getBytes());
  }

  @Test
  void saveRightData() throws Exception {
    mockMvc.perform(put("/v1/diff/1/right").content("A")).andExpect(status().isOk());
    verify(diffService).saveRightData(1L, "A".getBytes());
  }

  @Test
  void computeDifference() throws Exception {
    doReturn(DiffResultDto.builder()
        .outcome(DiffResultOutcome.DIFFERENT_WITH_OFFSETS)
        .offsets(List.of(
            DiffResultOffsetDto.builder().offset(0).length(2).build(),
            DiffResultOffsetDto.builder().offset(4).length(3).build())
        ).build()
    ).when(diffService).computeDifference(1L);

    mockMvc.perform(get("/v1/diff/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("outcome").value("DIFFERENT_WITH_OFFSETS"))
        .andExpect(jsonPath("offsets[0].offset").value(0))
        .andExpect(jsonPath("offsets[0].length").value(2))
        .andExpect(jsonPath("offsets[1].offset").value(4))
        .andExpect(jsonPath("offsets[1].length").value(3));
  }
}
