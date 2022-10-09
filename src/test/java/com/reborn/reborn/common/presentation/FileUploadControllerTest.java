package com.reborn.reborn.common.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reborn.reborn.common.presentation.dto.FileDto;
import com.reborn.reborn.common.application.FileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class FileUploadControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private FileService fileService;

    @Test
    @DisplayName("파일 업로드: POST /api/v1/files")
    void workoutCreate() throws Exception {
        //given
        List<MultipartFile> files = new ArrayList<>();
        MockMultipartFile file = new MockMultipartFile("image",
                "test.png",
                "image/png",
                "<<png data>>".getBytes());
        files.add(file);

        List<FileDto> fileDtos = new ArrayList<>();
        FileDto fileDto = new FileDto("test.png", "실제 경로");
        fileDtos.add(fileDto);

        when(fileService.uploadFile(files)).thenReturn(fileDtos);

        //when
        mockMvc.perform(multipart("/api/v1/files").file("file", file.getBytes()))
                .andExpect(status().isOk())
                .andDo(document("upload-file",
                        requestParts(
                                partWithName("file").description("The file to upload")
                        )
                ));
    }
}