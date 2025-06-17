package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import console.Console;
import usuario.Usuario;
import usuario.gerenciar.GerenciarUsuario;

public class ProgramaPrincipal {

	private GerenciarUsuario gerenciarUsuario;
	private Console console;
	
	private static final int CADASTRAR = 1;
	private static final int ALTERAR = 2;
	private static final int LISTAR = 3;
	private static final int REMOVER = 4;
	private static final int SAIR = 9;
	
	
	public ProgramaPrincipal() {
		gerenciarUsuario = new GerenciarUsuario();
		console = new Console();
	}
	
	public static void main(String[] args) {
		new ProgramaPrincipal().executar();
	}
	
	private void executar() {
		
		int opcao = 0;
		
		do {
			imprimirMenu();
			
			opcao = console.readInt("Digite uma opção: ");
			
			if (opcao == CADASTRAR)	{
				cadastrar();
			} else if (opcao == ALTERAR) {
				alterar();
			} else if (opcao == LISTAR) {
				listar();
			} else if (opcao == REMOVER) {
				remover();
			}
			
		} while (opcao != SAIR);
		
		gerenciarUsuario.fechar();
		System.out.println("Terminando o programa, bye");
	}

	private void remover() {
		System.out.println("\nRemover usuário\n");
		
		int idParaExcluir = console.readInt("Digite o ID para excluir: ");
		
		gerenciarUsuario.remover(idParaExcluir);
		System.out.println("\nUsuário excluído com sucesso");
	}

	private void listar() {
		System.out.println("\nListar usuários\n");
		
		List<Usuario> usuarios = gerenciarUsuario.listar();
		imprimirLista(usuarios);
	}

	private void imprimirLista(List<Usuario> usuarios) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		for (Usuario usuario : usuarios) {
			System.out.println("\nID: " + usuario.getId());
			System.out.println("Nome: " + usuario.getNome());
			System.out.println("CPF: " + usuario.getCpf());
			System.out.println("Data de nascimento: " + dtf.format(usuario.getDataNascimento()));
		}
	}

	private void alterar() {
		System.out.println("\nAlterar usuário\n");
		
		int idParaAlterar = console.readInt("Digite o ID para alterar: ");
		
		Usuario usuario = gerenciarUsuario.findById(idParaAlterar);
		
		lerDadosUsuario(usuario);
		
		gerenciarUsuario.atualizar(usuario);
		
		System.out.println("\nUsuário alterado com sucesso\n");
	}

	private void cadastrar() {
		System.out.println("\nCadastrar novo usuário\n");
		
		Usuario usuario = new Usuario();
		
		lerDadosUsuario(usuario);
		
		gerenciarUsuario.salvar(usuario);
		
		System.out.println("\nUsuário criado com sucesso\n");
	}

	private void lerDadosUsuario(Usuario usuario) {
		String nome = console.readLine("Digite o nome: ");
		String cpf = console.readString("Digite o CPF: ");
		LocalDate dataNascimento = console.readLocalDate("Digite a data de nascimento: ");
		
		usuario.setNome(nome);
		usuario.setCpf(cpf);
		usuario.setDataNascimento(dataNascimento);
	}

	private void imprimirMenu() {
		System.out.println("");
		System.out.println("--- SUPER CRUD ---");
		System.out.println("");
		System.out.println("1 - Cadastrar usuário");
		System.out.println("2 - Alterar usuário");
		System.out.println("3 - Listar usuários");
		System.out.println("4 - Remover usuário");
		System.out.println("9 - Sair");
	}
	
}
