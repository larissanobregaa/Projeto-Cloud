# Projeto-Cloud
O objetivo deste projeto é a implementação de um aplicativo que autoriza uma transação (cartão de crédito) para uma conta específica. Para isso, o projeto lidará com as seguintes operações:
1. Criação de conta
2. Autorização de transação
3. Notificação de transação

### Regras de Negócio
Para a implementação é necessário ter em mente as seguintes regras de negócio:
1. O valor da transação não deve exceder o limite disponível: limite insuficiente
2. Nenhuma transação deve ser aceita quando o cartão não está ativo: cartão não ativo
3. Não deve haver mais de 3 transações em um intervalo de 2 minutos: alta-frequência-pequeno-intervalo
4. Não deve haver mais de 2 transações semelhantes (mesmo valor e comerciante) em um intervalo de 2 minutos: transação duplicada



- Fazer os casos de uso e descrição dos casos de uso do projeto.
- Criar o dashboard de acompanhamento de casos de uso no github do projeto.
- Criar os scripts SQL (não esquecer os relacionamentos).
