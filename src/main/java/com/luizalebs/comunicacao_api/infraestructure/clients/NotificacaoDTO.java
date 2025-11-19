package com.luizalebs.comunicacao_api.infraestructure.clients;

import com.luizalebs.comunicacao_api.infraestructure.enums.ModoEnvioEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificacaoDTO {

    private String nomeDestinatario;
    private String emailDestinatario;
    private String telefoneDestinatario;
    private String mensagem;
    private ModoEnvioEnum modoDeEnvio;
}
