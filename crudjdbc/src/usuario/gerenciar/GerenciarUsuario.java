package usuario.gerenciar;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import usuario.Usuario;

public class GerenciarUsuario {

	private Connection conn;
	private PreparedStatement pstm;
	private String sql;
	
	public GerenciarUsuario() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_ifpr", "root", "root");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void salvar(Usuario usuario) {
		try {
			sql = "insert into tb_usuario (nome, cpf, data_nascimento) values (?, ?, ?)";
			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, usuario.getNome());
			pstm.setString(2, usuario.getCpf());
			pstm.setDate(3, Date.valueOf(usuario.getDataNascimento()));
			
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void atualizar(Usuario usuario) {
		try {
			sql = "update into tb_usuario set nome = ?, cpf = ?, data_nascimento = ? where id = ?";
			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, usuario.getNome());
			pstm.setString(2, usuario.getCpf());
			pstm.setDate(3, Date.valueOf(usuario.getDataNascimento()));
			pstm.setInt(4, usuario.getId());
			
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Usuario> listar() {
		sql = "select * from tb_usuario order by id desc limit 20";
		return executarSelect();
	}

	private List<Usuario> executarSelect() {
		List<Usuario> usuarios = new ArrayList<>();
		try {
			pstm = conn.prepareStatement(sql);
			
			ResultSet rs = pstm.executeQuery();
			while ( rs.next() )
			{
				Usuario usuario = resultToObject(rs);
				
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usuarios;
	}

	private Usuario resultToObject(ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		String nome = rs.getString("nome");
		String cpf = rs.getString("cpf");
		Date dataNasc = rs.getDate("data_nascimento");
		
		Usuario usuario = new Usuario();
		usuario.setId(id);
		usuario.setNome(nome);
		usuario.setCpf(cpf);
		usuario.setDataNascimento(dataNasc.toLocalDate());
		return usuario;
	}

	public void remover(int id) {
		try {
			sql = "delete from tb_usuario where id = ?";
			pstm = conn.prepareStatement(sql);
			
			pstm.setInt(1, id);
			
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Usuario findById(int id) {
		Usuario usuario = null;
		try {
			sql = "select * from tb_usuario where id = ?";
			pstm = conn.prepareStatement(sql);
			
			pstm.setInt(1, id);
			
			ResultSet rs = pstm.executeQuery();
			rs.next();
			usuario = resultToObject(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usuario;
	}
	
	public List<Usuario> pesquisarPorNome(String nome) {
		sql = "select * from tb_usuario where nome like '?%' order by id limit 20";
		return executarSelect();
	}
	
	public void fechar() {
		try {
			pstm.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
