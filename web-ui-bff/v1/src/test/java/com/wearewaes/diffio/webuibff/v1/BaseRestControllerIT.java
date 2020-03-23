package com.wearewaes.diffio.webuibff.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.validation.Validator;

/**
 * Base class to be extended for controller integration tests
 */
public abstract class BaseRestControllerIT {

  protected MockMvc mockMvc;

  protected MockMvc buildMockMvc(Object controller) {
    return mockMvcBuilder(controller).build();
  }

  protected StandaloneMockMvcBuilder mockMvcBuilder(Object controller) {
    return MockMvcBuilders.standaloneSetup(controller)
        .defaultRequest(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
        .setValidator(Mockito.mock(Validator.class))
        .alwaysDo(MockMvcResultHandlers.print());
  }

  @SneakyThrows
  protected String toJson(Object object) {
    return new ObjectMapper().writeValueAsString(object);
  }
}
