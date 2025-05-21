package com.drive.EvaluationProject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.drive.EvaluationProject.DTO.UserRegistrationDto;
import com.drive.EvaluationProject.controller.AuthController;
import com.drive.EvaluationProject.modal.UserDetails;
import com.drive.EvaluationProject.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void whenValidRegistration_thenReturn200() throws Exception {
        UserDetails user = new UserDetails();
        user.setUsername("test");
        when(userService.registerUser(any(UserRegistrationDto.class))).thenReturn(user);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"test\",\"password\":\"pass\",\"role\":\"VIEWER\"}"))
                .andExpect(status().isOk());
    }
}
