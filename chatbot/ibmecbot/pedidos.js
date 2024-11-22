const axios = require('axios').default;
const { CardFactory } = require('botbuilder');

class Pedido {
    #apiEndpoint = 'url';
    #subscriptionKey = 'senha';

    constructor(botContext) {
        this.botContext = botContext;
    }

    async fetchOrderDetails(orderId) {
        try {
            const response = await axios.get(this.#buildApiUrl(orderId), {
                headers: this.#getAuthHeaders()
            });
            return response.data;
        } catch (error) {
            console.error('Erro ao buscar detalhes do pedido:', error.message);
            throw new Error('Erro na comunicaçao com a API de pedidos.');
        }
    }

    buildOrderCard(orderData) {
        const { productName, status, productId, accountId, dataOrder } = orderData;

        return CardFactory.thumbnailCard(
            `Informações do Pedido: ${productName}`,
            undefined, // Sem imagens
            ["Avançar"], // Botões
            {
                subtitle: `Situação Atual: ${status}`,
                text: `Código do Produto: ${productId}\n\nUsuário Associado: ${accountId}\n\nData de Criação: ${dataOrder}`
            }
        );
    }

    #buildApiUrl(orderId) {
        return `${this.#apiEndpoint}?orderId=${encodeURIComponent(orderId)}`;
    }

    #getAuthHeaders() {
        return {
            'ocp-apim-subscription-key': this.#subscriptionKey
        };
    }
}

module.exports.Pedido = Pedido;
