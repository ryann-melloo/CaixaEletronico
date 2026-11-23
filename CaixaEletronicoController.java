package br.gov.sp.cps.controllers;

import br.gov.sp.cps.model.ContaCaixaEletronico;
import br.gov.sp.cps.model.ContaCaixaEletronico.ResultadoOperacao;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CaixaEletronicoController {

    private final ContaCaixaEletronico conta;
    private final JLabel lblSaldo;
    private final JTextField txtValor;
    private final JTextArea txtExtrato;

    public CaixaEletronicoController(JLabel lblSaldo, JTextField txtValor, JTextArea txtExtrato) {
        this.conta = new ContaCaixaEletronico();
        this.lblSaldo = lblSaldo;
        this.txtValor = txtValor;
        this.txtExtrato = txtExtrato;
        atualizarTela();
    }

    private void atualizarTela() {
        lblSaldo.setText(conta.getSaldoFormatado());
        txtExtrato.setText(conta.getExtrato());
    }

    private Double lerValorDigitado() {
        try {
            String texto = txtValor.getText().trim().replace(",", ".");
            return Double.parseDouble(texto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Erro: digite um número válido.");
            return null;
        }
    }

    public ActionListener acaoDepositar() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Double valor = lerValorDigitado();
                if (valor == null) {
                    return;
                }

                ResultadoOperacao resultado = conta.depositar(valor);
                if (!resultado.isSucesso()) {
                    JOptionPane.showMessageDialog(null, resultado.getMensagem());
                    return;
                }

                atualizarTela();
                txtValor.setText("");
                txtValor.requestFocus();
                JOptionPane.showMessageDialog(null, resultado.getMensagem());
            }
        };
    }

    public ActionListener acaoSacar() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Double valor = lerValorDigitado();
                if (valor == null) {
                    return;
                }

                ResultadoOperacao resultado = conta.sacar(valor);
                if (!resultado.isSucesso()) {
                    JOptionPane.showMessageDialog(null, resultado.getMensagem());
                    return;
                }

                atualizarTela();
                txtValor.setText("");
                txtValor.requestFocus();
            }
        };
    }

    public ActionListener acaoExtrato() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtExtrato.setText(conta.getExtrato());
            }
        };
    }
}
