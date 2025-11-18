package com.luizalebs.comunicacao_api.business.service;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.business.mapper.ComunicacaoConverter;
import com.luizalebs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.repositories.ComunicacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ComunicacaoServiceTest {

    @Mock
    private ComunicacaoRepository repository;

    @Mock
    private ComunicacaoConverter converter;

    @Autowired
    @InjectMocks
    private ComunicacaoService service;

    private ComunicacaoInDTO inDTO;
    private ComunicacaoEntity entity;
    private ComunicacaoOutDTO outDTO;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        inDTO = new ComunicacaoInDTO();
        inDTO.setEmailDestinatario("teste@email.com");

        entity = new ComunicacaoEntity();
        entity.setEmailDestinatario("teste@email.com");

        outDTO = new ComunicacaoOutDTO();
        outDTO.setEmailDestinatario("teste@email.com");
    }

    @Test
    @DisplayName("Deve agendar comunicação com sucesso")
    void agendarComunicacao() {
        when(converter.paraComunicacaoEntity(inDTO)).thenReturn(entity);
        when(converter.paraDTO(entity)).thenReturn(outDTO);

        ComunicacaoOutDTO resultado = service.agendarComunicacao(inDTO);

        assertNotNull(resultado);
        assertEquals(StatusEnvioEnum.PENDENTE, inDTO.getStatusEnvio());
        verify(converter).paraComunicacaoEntity(inDTO);
        verify(repository).save(entity);
        verify(converter).paraDTO(entity);
    }

    @Test
    @DisplayName("Deve buscar Status da comunicação com sucesso")
    void buscarStatusComunicacao() {
        when(repository.findByEmailDestinatario("teste@email.com")).thenReturn(entity);
        when(converter.paraDTO(entity)).thenReturn(outDTO);

        ComunicacaoOutDTO resultado = service.buscarStatusComunicacao("teste@email.com");

        assertNotNull(resultado);
        verify(repository).findByEmailDestinatario("teste@email.com");
        verify(converter).paraDTO(entity);
    }

    @Test
    @DisplayName("Deve alterar o status da comunicação com sucesso")
    void alterarStatusComunicacao() {
        when(repository.findByEmailDestinatario("teste@email.com")).thenReturn(entity);
        when(converter.paraDTO(entity)).thenReturn(outDTO);

        ComunicacaoOutDTO resultado = service.alterarStatusComunicacao("teste@email.com");

        assertNotNull(resultado);
        assertEquals(StatusEnvioEnum.CANCELADO, entity.getStatusEnvio());
        verify(repository).findByEmailDestinatario("teste@email.com");
        verify(repository).save(entity);
        verify(converter).paraDTO(entity);
    }
}