# Projeto-Cloud

Grupo:
Fernanda Moysés
Ian Amoedo
Jorge Felippe Magarão
Larissa Nobrega

O objetivo deste projeto é a implementação de um aplicativo que autoriza uma transação (cartão de crédito) para uma conta específica. Para isso, o projeto lidará com as seguintes operações:
1. Criação de conta
2. Autorização de transação
3. Notificação de transação

### Regras de Negócio
Para a implementação é necessário ter em mente as seguintes regras de negócio:
- O valor da transação não deve exceder o limite disponível: limite insuficiente;
- Nenhuma transação deve ser aceita quando o cartão não está ativo: cartão não ativo;
- Não deve haver mais de 3 transações em um intervalo de 2 minutos: alta-frequência-pequeno-intervalo;
- Não deve haver mais de 2 transações semelhantes (mesmo valor e comerciante) em um intervalo de 2 minutos: transação duplicada;

