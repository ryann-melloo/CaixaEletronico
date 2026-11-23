package br.gov.sp.cps.view;

import br.gov.sp.cps.controllers.CaixaEletronicoController;

import javax.swing.*;

public class CaixaEletronico extends JFrame {

    private static final long serialVersionUID = 1L;

    JLabel lblBanco, lblTituloSaldo, lblSaldo;
    JLabel lblValor;
    JTextField txtValor;
    JButton btnDepositar, btnSacar, btnExtrato;
    JTextArea txtExtrato;

    public CaixaEletronico() {
        setTitle("Banco ETEC - Caixa Eletrônico");
        setSize(500, 440);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        lblBanco = new JLabel("BANCO ETEC · CAIXA ELETRÔNICO");
        lblBanco.setBounds(130, 15, 250, 25);
        add(lblBanco);

        lblTituloSaldo = new JLabel("SALDO DISPONÍVEL:");
        lblTituloSaldo.setBounds(30, 50, 150, 25);
        add(lblTituloSaldo);

        lblSaldo = new JLabel("R$ 1.000,00");
        lblSaldo.setBounds(180, 50, 200, 25);
        add(lblSaldo);

        lblValor = new JLabel("Valor da operação:");
        lblValor.setBounds(30, 95, 130, 25);
        add(lblValor);

        txtValor = new JTextField();
        txtValor.setBounds(160, 95, 220, 25);
        add(txtValor);

        btnDepositar = new JButton("Depositar");
        btnDepositar.setBounds(30, 140, 130, 30);
        add(btnDepositar);

        btnSacar = new JButton("Sacar");
        btnSacar.setBounds(180, 140, 130, 30);
        add(btnSacar);

        btnExtrato = new JButton("Extrato");
        btnExtrato.setBounds(330, 140, 120, 30);
        add(btnExtrato);

        txtExtrato = new JTextArea();
        txtExtrato.setEditable(false);

        JScrollPane scrollExtrato = new JScrollPane(txtExtrato);
        scrollExtrato.setBounds(30, 190, 420, 190);
        add(scrollExtrato);

        CaixaEletronicoController controller = new CaixaEletronicoController(
                lblSaldo,
                txtValor,
                txtExtrato
        );

        btnDepositar.addActionListener(controller.acaoDepositar());
        btnSacar.addActionListener(controller.acaoSacar());
        btnExtrato.addActionListener(controller.acaoExtrato());
    }

    public static void main(String[] args) {
        CaixaEletronico tela = new CaixaEletronico();
        tela.setVisible(true);
    }
}
