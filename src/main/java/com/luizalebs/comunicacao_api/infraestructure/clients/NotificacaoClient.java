package com.luizalebs.comunicacao_api.infraestructure.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notificacao", url = "${notificacao.url}")
public interface NotificacaoClient {

    @PostMapping("/email")
    void enviaMensagem(@RequestBody NotificacaoDTO dto);
}
