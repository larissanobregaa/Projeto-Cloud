const { MessageFactory } = require('botbuilder');
const {
    AttachmentPrompt,
    ChoiceFactory,
    ChoicePrompt,
    ComponentDialog,
    ConfirmPrompt,
    DialogSet,
    DialogTurnStatus,
    NumberPrompt,
    TextPrompt,
    WaterfallDialog
} = require('botbuilder-dialogs');
const { Produto } = require('../produto');
const { Extrato } = require('../extrato');
const { Pedido } = require('../pedidos');

const NAME_PROMPT = 'NAME_PROMPT';
const CARD_PROMPT = 'CARD_PROMPT';
const CHOICE_PROMPT = 'CHOICE_PROMPT';
const PRODUCT_PROFILE = 'PRODUCT_PROFILE';
const DIALOG_ID = 'PRODUCT_DIALOG';

class ProductDialog extends ComponentDialog {
    constructor(userState) {
        super(DIALOG_ID);

        this.productProfile = userState.createProperty(PRODUCT_PROFILE);

        this.addDialog(new TextPrompt(NAME_PROMPT));
        this.addDialog(new TextPrompt(CARD_PROMPT));
        this.addDialog(new ChoicePrompt(CHOICE_PROMPT));

        this.addDialog(new WaterfallDialog(DIALOG_ID, [
            this.selectOptionStep.bind(this),
            this.gatherDetailsStep.bind(this),
            this.processStep.bind(this),
            this.completeStep.bind(this)
        ]));

        this.initialDialogId = DIALOG_ID;
    }

    async run(turnContext, accessor) {
        const dialogSet = new DialogSet(accessor);
        dialogSet.add(this);

        const dialogContext = await dialogSet.createContext(turnContext);
        const results = await dialogContext.continueDialog();
        if (results.status === DialogTurnStatus.empty) {
            await dialogContext.beginDialog(this.id);
        }
    }

    async selectOptionStep(step) {
        return step.prompt(CHOICE_PROMPT, {
            prompt: 'Por favor, escolha uma opção:',
            choices: ChoiceFactory.toChoices(['Consultar Pedidos', 'Consultar Produtos', 'Extrato de Compras'])
        });
    }

    async gatherDetailsStep(step) {
        step.values.selectedOption = step.result.value;

        const promptMessage = {
            'Consultar Pedidos': 'Digite o número do pedido:',
            'Consultar Produtos': 'Insira o nome do produto:',
            'Extrato de Compras': 'Forneça o CPF para consulta:'
        }[step.values.selectedOption];

        return step.prompt(NAME_PROMPT, promptMessage);
    }

    async processStep(step) {
        const option = step.values.selectedOption;
        const inputData = step.result;

        switch (option) {
            case 'Consultar Pedidos': {
                const pedido = new Pedido();
                const response = await pedido.getPedido(inputData);

                if (!response || !response.data) {
                    await step.context.sendActivity('Pedido não encontrado. Tente novamente.');
                    return step.replaceDialog(DIALOG_ID);
                }

                const card = pedido.createPedidoCard(response.data);
                await step.context.sendActivity({ attachments: [card] });
                break;
            }
            case 'Consultar Produtos': {
                const produto = new Produto();
                const response = await produto.getProduto(inputData);

                if (!response || !response.data) {
                    await step.context.sendActivity('Produto não encontrado. Verifique e tente novamente.');
                    return step.replaceDialog(DIALOG_ID);
                }

                step.values.productDetails = response.data[0];
                const card = produto.createProductCard(response.data[0]);
                await step.context.sendActivity({ attachments: [card] });

                return step.prompt('CONFIRM_PROMPT', 'Deseja adquirir este produto?', ['Sim', 'Não']);
            }
            case 'Extrato de Compras': {
                const extrato = new Extrato();
                const response = await extrato.getUsuario(inputData);

                if (!response) {
                    await step.context.sendActivity('Nenhum dado encontrado para este CPF.');
                    return step.replaceDialog(DIALOG_ID);
                }

                const card = extrato.createExtratoCard(response);
                await step.context.sendActivity({ attachments: [card] });
                break;
            }
        }
        return step.endDialog();
    }

    async completeStep(step) {
        if (step.values.selectedOption === 'Consultar Produtos' && step.result === true) {
            const produto = new Produto();
            const { productId } = step.values.productDetails;

            return step.prompt(NAME_PROMPT, 'Digite seu CPF para concluir a compra:')
                .then(cpf => {
                    return step.prompt(CARD_PROMPT, 'Agora, insira o número do cartão:')
                        .then(async cardNumber => {
                            const transaction = await produto.validateTransaction(productId, cpf, cardNumber);
                            if (!transaction.success) {
                                await step.context.sendActivity(`Erro na compra: ${transaction.message}`);
                                return step.replaceDialog(DIALOG_ID);
                            }

                            const orderCard = produto.createOrderCard(transaction.data);
                            await step.context.sendActivity({ attachments: [orderCard] });
                            return step.endDialog();
                        });
                });
        }

        await step.context.sendActivity('Operação concluída.');
        return step.endDialog();
    }
}

module.exports.ProductDialog = ProductDialog;
