package views;

import dao.HospedeDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import models.Hospede;

public class HospedeView {

    public static void gerenciarHospede() {
        Scanner sc = new Scanner(System.in);
        HospedeDAO hospedeDAO = new HospedeDAO();
        boolean run = true;

        while (run) {
            System.out.println("Gerenciamento de Hóspedes");
            System.out.println("1. Criar Hóspede");
            System.out.println("2. Visualizar Hóspede");
            System.out.println("3. Atualizar Hóspede");
            System.out.println("4. Remover Hóspede");
            System.out.println("5. Listar todos os Hóspedes");
            System.out.println("6. Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");

            String opcaoStr = sc.nextLine();  
            int opcao;
            try {
                opcao = Integer.parseInt(opcaoStr);  
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Digite um número.");
                continue;  // Se der erro, volta ao começo do loop
            }

            switch (opcao) {
                case 1:
                    criarHospede(sc, hospedeDAO);
                    break;
                case 2:
                    visualizarHospede(sc, hospedeDAO);
                    break;
                case 3:
                    atualizarHospede(sc, hospedeDAO);
                    break;
                case 4:
                    removerHospede(sc, hospedeDAO);
                    break;
                case 5:
                    listarHospedes(hospedeDAO);
                    break;
                case 6:
                    System.out.println("Voltando ao menu principal...");
                    return;  
                default:
                    System.out.println("Opção Inválida. Tente novamente!");
                    break;
            }
        }
        sc.close(); 
    }

    private static void criarHospede(Scanner sc, HospedeDAO hospedeDAO) {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Documento: ");
        String documento = sc.nextLine();
        System.out.print("Telefone: ");
        String telefone = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();
        System.out.print("Alimento Restrito: ");
        String alimentoRestrito = sc.nextLine();
        System.out.println("Criando um novo Hóspede...");

        
        Hospede hospede = new Hospede(nome, documento, telefone, email, senha, alimentoRestrito);

        try {
            hospedeDAO.criarHospede(hospede);
            System.out.println("Hóspede criado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao criar o hóspede: " + e.getMessage());
        }
    }

    private static void visualizarHospede(Scanner sc, HospedeDAO hospedeDAO) {
        System.out.print("Informe o documento do hóspede a ser visualizado: ");
        String documento = sc.nextLine();

        try {
            Hospede hospede = hospedeDAO.vizualizarHospede(documento);
            if (hospede != null) {
                System.out.println("Hóspede encontrado: " + hospede);
            } else {
                System.out.println("Hóspede não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao visualizar o hóspede: " + e.getMessage());
        }
    }

    private static void atualizarHospede(Scanner sc, HospedeDAO hospedeDAO) {
        System.out.print("Informe o documento do hóspede a ser atualizado: ");
        String documento = sc.nextLine();

        try {
            Hospede hospedeExistente = hospedeDAO.vizualizarHospede(documento);
            if (hospedeExistente == null) {
                System.out.println("Hóspede não encontrado!");
                return;
            }

            System.out.print("Novo Nome do Hóspede: ");
            String nome = sc.nextLine();
            System.out.print("Novo Telefone do Hóspede: ");
            String telefone = sc.nextLine();
            System.out.print("Novo Email do Hóspede: ");
            String email = sc.nextLine();
            System.out.print("Nova Senha do Hóspede: ");
            String senha = sc.nextLine();
            System.out.print("Novo Alimento Restrito: ");
            String alimentoRestrito = sc.nextLine();
            System.out.println("Atualizando Hóspede...");

            
            Hospede hospedeAtualizado = new Hospede(nome, documento, telefone, email, senha, alimentoRestrito);
            hospedeDAO.atualizarHospede(hospedeAtualizado);
            System.out.println("Hóspede atualizado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o hóspede: " + e.getMessage());
        }
    }

    private static void listarHospedes(HospedeDAO hospedeDAO) {
        System.out.println("Listando todos os Hóspedes...");
        try {
            List<Hospede> hospedes = hospedeDAO.listarHospedes();
            if (hospedes.isEmpty()) {
                System.out.println("Nenhum hóspede encontrado!");
            } else {
                for (Hospede hospede : hospedes) {
                    System.out.println(hospede);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar os hóspedes: " + e.getMessage());
        }
    }

    private static void removerHospede(Scanner sc, HospedeDAO hospedeDAO) {
        System.out.print("Informe o documento do hóspede a ser removido: ");
        String documento = sc.nextLine();

        try {
            hospedeDAO.removerHospede(documento);
            System.out.println("Hóspede removido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao remover o hóspede: " + e.getMessage());
        }
    }
}
