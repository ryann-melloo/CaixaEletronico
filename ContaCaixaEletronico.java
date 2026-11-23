package br.gov.sp.cps.model;

import java.util.Locale;

public class ContaCaixaEletronico {

    // RN01: A conta começa com saldo de R$ 1.000,00
    // RN09: O saldo é sempre calculado pelo sistema, nunca digitado
    private double saldo = 1000.0;
    private final StringBuilder extrato = new StringBuilder();

    public double getSaldo() {
        return saldo;
    }

    public String getSaldoFormatado() {
        return "R$ " + String.format(Locale.forLanguageTag("pt-BR"), "%,.2f", saldo);
    }

    public String formatarMoeda(double valor) {
        return "R$ " + String.format(Locale.forLanguageTag("pt-BR"), "%,.2f", valor);
    }

    public String getExtrato() {
        if (extrato.length() == 0) {
            return "Nenhuma operação realizada até o momento.\n";
        }
        return extrato.toString();
    }

    // Depósito: aceita centavos e exige valor maior que zero (RN02 e RN08)
    public ResultadoOperacao depositar(double valor) {
        if (valor <= 0) {
            return new ResultadoOperacao(false, "Recusado: o valor deve ser maior que zero.");
        }

        saldo += valor;
        extrato.append("Depósito de ")
                .append(formatarMoeda(valor))
                .append(" | Saldo: ")
                .append(getSaldoFormatado())
                .append("\n");

        return new ResultadoOperacao(true, "Depósito realizado com sucesso!");
    }

    // Saque: valida na ordem exigida antes de mexer no saldo ou no extrato
    public ResultadoOperacao sacar(double valor) {
        // 1. Maior que zero (RN02)
        if (valor <= 0) {
            return new ResultadoOperacao(false, "Recusado: o valor deve ser maior que zero.");
        }

        // 2. Saldo suficiente (RN03)
        if (valor > saldo) {
            return new ResultadoOperacao(false, "Recusado: saldo insuficiente.");
        }

        // 3. Limite máximo de R$ 1.000,00 por operação (RN04)
        if (valor > 1000.0) {
            return new ResultadoOperacao(false, "Recusado: o saque máximo por operação é de R$ 1.000,00.");
        }

        // 4. Múltiplo de 10 (RN05 e RN06: apenas notas de R$ 100, R$ 50 e R$ 10)
        if (valor % 1 != 0 || ((int) valor) % 10 != 0) {
            return new ResultadoOperacao(false, "Recusado: o valor não é múltiplo de 10.");
        }

        // RN07: Menor quantidade possível de notas
        int restante = (int) valor;
        int notas100 = restante / 100;
        restante = restante % 100;

        int notas50 = restante / 50;
        restante = restante % 50;

        int notas10 = restante / 10;

        saldo -= valor;

        StringBuilder detalheSaque = new StringBuilder();
        detalheSaque.append("Saque de ").append(formatarMoeda(valor)).append("\n");
        if (notas100 > 0) {
            detalheSaque.append("  ").append(notas100).append("x R$ 100  ");
        }
        if (notas50 > 0) {
            detalheSaque.append(notas50).append("x R$ 50  ");
        }
        if (notas10 > 0) {
            detalheSaque.append(notas10).append("x R$ 10");
        }
        detalheSaque.append("\n");

        extrato.append(detalheSaque.toString());

        return new ResultadoOperacao(true, "Saque realizado com sucesso!");
    }

    public static class ResultadoOperacao {
        private final boolean sucesso;
        private final String mensagem;

        public ResultadoOperacao(boolean sucesso, String mensagem) {
            this.sucesso = sucesso;
            this.mensagem = mensagem;
        }

        public boolean isSucesso() {
            return sucesso;
        }

        public String getMensagem() {
            return mensagem;
        }
    }
}
