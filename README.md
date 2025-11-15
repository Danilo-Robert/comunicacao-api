## Endpoints da Aplicação

- **POST /comunicacao/agendar**: Agenda um novo envio de mensagem.

O dto deve ser preenchido com os seguintes dados: {"dataHoraEnvio": " " , "nomeDestinatario": " " , "emailDestinatario": " " , "telefoneDestinatario": " " , "mensagem": " " , "modoDeEnvio": " " }

- **GET /comunicacao**: Retorna o status da mensagem.
- **PATCH /comunicacao/cancelar**: Altera o status da mensagem.
