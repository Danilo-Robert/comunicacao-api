package com.luizalebs.comunicacao_api.business.service;

import com.luizalebs.comunicacao_api.infraestructure.clients.NotificacaoClient;
import com.luizalebs.comunicacao_api.infraestructure.clients.NotificacaoDTO;
import com.luizalebs.comunicacao_api.infraestructure.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final NotificacaoClient notificacaoClient;

    public void enviarMensagem(NotificacaoDTO dto){
        try{
            notificacaoClient.enviaMensagem(dto);
        } catch (BusinessException e){
            throw new BusinessException("Erro ao enviar mensagem", e);
        }
    }
}
