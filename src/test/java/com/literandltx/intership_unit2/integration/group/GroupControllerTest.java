package com.literandltx.intership_unit2.integration.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.literandltx.intership_unit2.dto.group.GroupRequest;
import com.literandltx.intership_unit2.dto.group.GroupResponse;
import com.literandltx.intership_unit2.model.Group;
import com.literandltx.intership_unit2.repository.GroupRepository;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.junit.jupiter.api.Test;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GroupControllerTest {
    protected static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GroupRepository groupRepository;

    @BeforeAll
    static void beforeAll(
            @Autowired DataSource dataSource,
            @Autowired WebApplicationContext applicationContext
    ) throws SQLException {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .build();
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
        }
    }

    @Test
    void saveGroup_ValidInput_Success() throws Exception {
        // Given
        GroupRequest request = new GroupRequest();
        request.setTitle("test2_group_title");

        String jsonRequest = objectMapper.writeValueAsString(request);

        // When
        MvcResult result = mockMvc.perform(post("/v1/groups")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        GroupResponse actual = objectMapper.readValue(result.getResponse().getContentAsString(), GroupResponse.class);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(request.getTitle(), actual.getTitle());

        // Clean up
        groupRepository.deleteById(actual.getId());
    }

    @Test
    void saveGroup_InvalidInputNull_Failed() throws Exception {
        // Given
        GroupRequest request = new GroupRequest();
        String jsonRequest = objectMapper.writeValueAsString(request);

        // When
        MvcResult result = mockMvc.perform(post("/v1/groups")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        // Then
        int status = result.getResponse().getStatus();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), status);
    }

    @Test
    void saveGroup_InvalidInputBlank_Failed() throws Exception {
        // Given
        GroupRequest request = new GroupRequest();
        request.setTitle("");

        String jsonRequest = objectMapper.writeValueAsString(request);

        // When
        MvcResult result = mockMvc.perform(post("/v1/groups")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        // Then
        int status = result.getResponse().getStatus();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), status);
    }

    @Test
    void findAllGroup_Valid_Success() throws Exception {
        // Given & When
        MvcResult result = mockMvc.perform(get("/v1/groups")
                        .contentType(MediaType.ALL_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        GroupResponse[] actual = objectMapper.readValue(result.getResponse().getContentAsString(), GroupResponse[].class);

        // Then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(3, actual.length);
    }

    @Test
    void deleteById_Valid_Success() throws Exception {
        // Given
        Group group = new Group();
        group.setTitle("test2_group_title");

        Group save = groupRepository.save(group);

        // When
        MvcResult result = mockMvc.perform(delete("/v1/groups")
                        .param("id", save.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        int status = result.getResponse().getStatus();

        Assertions.assertEquals(HttpStatus.OK.value(), status);
    }

    @Test
    void deleteById_Invalid_Failed() {
        // Given
        int id = 123123;

        // When
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            mockMvc.perform(delete("/v1/groups")
                            .param("id", String.valueOf(id))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError())
                    .andReturn();
        });

        // Then
        assertEquals("Cannot find group by id: " + id, exception.getCause().getMessage());
    }

    @Test
    void updateById_Valid_Success() throws Exception {
        // Given
        int id = 1;
        Group model = new Group();
        model.setTitle("updated_title1");

        String jsonRequest = objectMapper.writeValueAsString(model);

        // When
        MvcResult result = mockMvc.perform(put("/v1/groups")
                        .content(jsonRequest)
                        .param("id", String.valueOf(id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        GroupResponse actual = objectMapper.readValue(result.getResponse().getContentAsString(), GroupResponse.class);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(id, actual.getId());
        Assertions.assertEquals(model.getTitle(), actual.getTitle());
    }

    @Test
    void updateById_Invalid_Failed() throws Exception {
        // Given
        int id = 123132;
        Group model = new Group();
        model.setTitle("test_updated_title1");

        String jsonRequest = objectMapper.writeValueAsString(model);

        // When
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            mockMvc.perform(put("/v1/groups")
                            .content(jsonRequest)
                            .param("id", String.valueOf(id))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        });

        // Then
        assertEquals("Cannot find group by id: " + id, exception.getCause().getMessage());
    }

}
