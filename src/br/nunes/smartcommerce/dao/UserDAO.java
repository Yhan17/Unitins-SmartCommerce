package br.nunes.smartcommerce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.nunes.smartcommerce.model.User;




public class UserDAO extends DAO<User>{

	@Override
	public boolean create(User entity) {
		boolean retorno = false;
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO users ");
		sql.append("	(name, email, password, is_admin, created_at, updated_at) ");
		sql.append("VALUES ");
		sql.append("	( ? , ? , ? , ? , ? , ? ) ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, entity.getName());
			stat.setString(2, entity.getLogin());
			stat.setString(3, entity.getPassword());
			stat.setBoolean(4, false);
			stat.setDate(5, java.sql.Date.valueOf(LocalDate.now()));
			stat.setDate(6, java.sql.Date.valueOf(LocalDate.now()));
			
			stat.execute();
			
			conn.commit();

			System.out.println("Inclusão realizada com sucesso.");
			
			retorno = true;

		} catch (SQLException e) {
			System.out.println("Erro ao realizar a Inclusão.");
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return retorno;
	}

	@Override
	public boolean update(User entity) {
		boolean retorno = false;
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE users ");
		sql.append("	SET name=?, email=? ,password=?, is_admin= ?, updated_at=? ");
		sql.append("WHERE ");
		sql.append("	id = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, entity.getName());
			stat.setString(2, entity.getLogin());
			stat.setString(3, entity.getPassword());
			stat.setBoolean(4, entity.getIs_admin());
			stat.setDate(5, java.sql.Date.valueOf(LocalDate.now()));
			stat.setInt(6, entity.getId());
			
			stat.execute();
			
			conn.commit();

			System.out.println("Alteração realizada com sucesso.");
			
			retorno = true;

		} catch (SQLException e) {
			System.out.println("Erro ao realizar o Update.");
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return retorno;	
	}

	@Override
	public boolean delete(int id) {
		boolean retorno = false;
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM users ");
		sql.append("WHERE ");
		sql.append("	id = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			stat.execute();
			
			conn.commit();

			System.out.println("Remoção realizada com sucesso.");
			
			retorno = true;

		} catch (SQLException e) {
			System.out.println("Erro ao remover o registro.");
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return retorno;

	}

	@Override
	public List<User> findAll() {
		List<User> listaUsuario = new ArrayList<User>();
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append(" 	id, name, email, password, is_admin, created_at, updated_at ");
		sql.append("FROM ");
		sql.append("	users ");
		sql.append("	ORDER BY users.id ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			
			ResultSet rs = stat.executeQuery();
			
			User usuario = null;
			
			while(rs.next()) {
				usuario = new User();
				usuario.setId(rs.getInt("id"));
				usuario.setName(rs.getString("name"));
				usuario.setLogin(rs.getString("email"));
				usuario.setPassword(rs.getString("password"));
				usuario.setIs_admin(rs.getBoolean("is_admin"));
				usuario.setCreated_at(rs.getDate("created_at").toLocalDate());
				usuario.setUpdated_at(rs.getDate("updated_at").toLocalDate());
				
				listaUsuario.add(usuario);
			}
			
		} catch (SQLException e) {
			System.out.println("Erro ao realizar a busca");
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return listaUsuario;
	}

	@Override
	public User findById(int id) {
		User usuario = null;
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append(" 	id, name, email, password, is_admin, created_at, updated_at ");
		sql.append("FROM ");
		sql.append("	users ");
		sql.append("WHERE ");
		sql.append("	id = ? ");

		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				usuario = new User();
				usuario.setId(rs.getInt("id"));
				usuario.setName(rs.getString("name"));
				usuario.setLogin(rs.getString("email"));
				usuario.setPassword(rs.getString("password"));
				usuario.setIs_admin(rs.getBoolean("is_admin"));
				usuario.setCreated_at(rs.getDate("created_at").toLocalDate());
				usuario.setUpdated_at(rs.getDate("updated_at").toLocalDate());
			}
			
		} catch (SQLException e) {
			System.out.println("Erro ao realizar a consulta por id");
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return usuario;
	}
	
	public User verificarLoginSenha(String email, String senha) {
		User usuario = null;
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append(" 	id, name, email, password, is_admin, created_at, updated_at ");
		sql.append("FROM ");
		sql.append("	users ");
		sql.append("WHERE ");
		sql.append("	email = ? ");
		sql.append("	AND password = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, email);
			stat.setString(2, senha);
			
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				usuario = new User();
				usuario.setId(rs.getInt("id"));
				usuario.setName(rs.getString("name"));
				usuario.setLogin(rs.getString("email"));
				usuario.setPassword(rs.getString("password"));
				usuario.setIs_admin(rs.getBoolean("is_admin"));
				usuario.setCreated_at(rs.getDate("created_at").toLocalDate());
				usuario.setUpdated_at(rs.getDate("updated_at").toLocalDate());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return usuario;
	}

}
