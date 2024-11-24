package br.edu.ibmec.cloud.ecommerce.service;

import org.springframework.stereotype.Service;

@Service
public class FraudDetectionService {
    public boolean isFraudulentTransaction(int userId, double value) {
        // Exemplo: bloquear transações acima de R$10.000,00
        return value > 10000;
    }
}