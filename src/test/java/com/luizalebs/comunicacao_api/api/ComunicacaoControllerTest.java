package com.luizalebs.comunicacao_api.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.business.service.ComunicacaoService;
import com.luizalebs.comunicacao_api.infraestructure.enums.ModoEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ComunicacaoController.class)
class ComunicacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComunicacaoService service;

    @Autowired
    private ObjectMapper objectMapper;

    private ComunicacaoInDTO inDTO;
    private ComunicacaoOutDTO outDTO;

    @BeforeEach
    void setup() {

        inDTO = ComunicacaoInDTO.builder()
                .dataHoraEnvio(LocalDateTime.now())
                .emailDestinatario("teste@email.com")
                .nomeDestinatario("Danilo")
                .telefoneDestinatario("999999999")
                .mensagem("Oi!")
                .modoDeEnvio(ModoEnvioEnum.EMAIL)
                .build();

        outDTO = new ComunicacaoOutDTO();
        outDTO.setEmailDestinatario("teste@email.com");
        outDTO.setStatusEnvio(StatusEnvioEnum.PENDENTE);
    }

    @Test
    @DisplayName("Deve agendar comunicação com sucesso")
    void agendar() throws Exception {

        when(service.agendarComunicacao(any())).thenReturn(outDTO);

        mockMvc.perform(post("/comunicacao/agendar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.emailDestinatario").value("teste@email.com"))
                .andExpect(jsonPath("$.statusEnvio").value("PENDENTE"));

        verify(service).agendarComunicacao(any());
    }

    @Test
    @DisplayName("Deve buscar Status da comunicação com sucesso")
    void buscarStatus() throws Exception {

        when(service.buscarStatusComunicacao("teste@email.com"))
                .thenReturn(outDTO);

        mockMvc.perform(get("/comunicacao")
                        .param("emailDestinatario", "teste@email.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.emailDestinatario").value("teste@email.com"));

        verify(service).buscarStatusComunicacao("teste@email.com");
    }

    @Test
    @DisplayName("Deve alterar o status da comunicação com sucesso")
    void cancelarStatus() throws Exception {

        outDTO.setStatusEnvio(StatusEnvioEnum.CANCELADO);

        when(service.alterarStatusComunicacao("teste@email.com"))
                .thenReturn(outDTO);

        mockMvc.perform(patch("/comunicacao/cancelar")
                        .param("emailDestinatario", "teste@email.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusEnvio").value("CANCELADO"));

        verify(service).alterarStatusComunicacao("teste@email.com");
    }
}