package br.nunes.smartcommerce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.nunes.smartcommerce.model.Brand;
import br.nunes.smartcommerce.model.Produto;

public class ProdutoDAO extends DAO<Produto> {

	@Override
	public boolean create(Produto entity) {
		boolean retorno = false;
		Connection conn = getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO produtos ");
		sql.append("	(name, description, brand, price, stock, release_date, image, created_at, updated_at ) ");
		sql.append("VALUES ");
		sql.append("	( ? , ? , ? , ? , ?, ?, ?, ?, ?) ");

		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, entity.getName());
			stat.setString(2, entity.getDescription());
			stat.setInt(3, entity.getBrand().getId());
			// Trata os problemas relacionados aos valores Nulos do preço do Smartphone
			if (entity.getPrice() != null)
				stat.setFloat(4, entity.getPrice());
			else
				stat.setNull(4, java.sql.Types.FLOAT);

			// Trata os problemas relacionados aos valores Nulos do preço do Smartphone
			if (entity.getStock() != null)
				stat.setInt(5, entity.getStock());
			else
				stat.setNull(5, java.sql.Types.INTEGER);

			stat.setDate(6, java.sql.Date.valueOf(entity.getRelease_date()));
			stat.setString(7, entity.getImage());
			stat.setDate(8, java.sql.Date.valueOf(LocalDate.now()));
			stat.setDate(9, java.sql.Date.valueOf(LocalDate.now()));

			stat.execute();

			conn.commit();

			System.out.println("Inclusão realizada com sucesso.");

			retorno = true;

		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return retorno;
	}

	@Override
	public boolean update(Produto entity) {
		boolean retorno = false;
		Connection conn = getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE produtos ");
		sql.append("	set name = ?, description = ?, brand = ?, price = ?, stock = ?, release_date = ?, image = ?, updated_at= ?  ");
		sql.append("WHERE ");
		sql.append("	id = ? ");

		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, entity.getName());
			stat.setString(2, entity.getDescription());
			stat.setInt(3, entity.getBrand().getId());
			// Trata os problemas relacionados aos valores Nulos do preço do Smartphone
			if (entity.getPrice() != null)
				stat.setFloat(4, entity.getPrice());
			else
				stat.setNull(4, java.sql.Types.FLOAT);

			// Trata os problemas relacionados aos valores Nulos do preço do Smartphone
			if (entity.getStock() != null)
				stat.setInt(5, entity.getStock());
			else
				stat.setNull(5, java.sql.Types.INTEGER);

			stat.setDate(6, java.sql.Date.valueOf(entity.getRelease_date()));
			stat.setString(7, entity.getImage());
			stat.setDate(8, java.sql.Date.valueOf(LocalDate.now()));
			stat.setInt(9, entity.getId());
			stat.execute();

			conn.commit();

			System.out.println("Alteração realizada com sucesso.");

			retorno = true;

		} catch (SQLException e) {
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
		sql.append("DELETE FROM produtos ");
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
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return retorno;
	}

	@Override
	public List<Produto> findAll() {
		List<Produto> listaProdutos = new ArrayList<Produto>();
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append(" 	id, name, description, brand, price, stock, release_date, image, created_at, updated_at  ");
		sql.append("FROM ");
		sql.append("	produtos ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			
			ResultSet rs = stat.executeQuery();
			
			Produto produto = null;
			
			while(rs.next()) {
				produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setName(rs.getString("name"));
				produto.setDescription(rs.getString("description"));
				produto.setBrand(Brand.valueOf(rs.getInt("brand")));
				produto.setPrice(rs.getFloat("price"));
				produto.setStock(rs.getInt("stock"));
				produto.setRelease_date(rs.getDate(("release_date")).toLocalDate());
				produto.setImage(rs.getString("image"));
				produto.setCreated_at(rs.getDate(("created_at")).toLocalDate());
				produto.setUpdated_at(rs.getDate(("updated_at")).toLocalDate());
				listaProdutos.add(produto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return listaProdutos;
	}

	@Override
	public Produto findById(int id) {
		Produto produto = null;
		
Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append(" 	id, name, description, brand, price, stock, release_date, image, created_at, updated_at  ");
		sql.append("FROM ");
		sql.append("	produtos ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			
			ResultSet rs = stat.executeQuery();
			
			
			while(rs.next()) {
				produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setName(rs.getString("name"));
				produto.setDescription(rs.getString("description"));
				produto.setBrand(Brand.valueOf(rs.getInt("brand")));
				produto.setPrice(rs.getFloat("price"));
				produto.setStock(rs.getInt("stock"));
				produto.setRelease_date(rs.getDate(("release_date")).toLocalDate());
				produto.setImage(rs.getString("image"));
				produto.setCreated_at(rs.getDate(("created_at")).toLocalDate());
				produto.setUpdated_at(rs.getDate(("updated_at")).toLocalDate());

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}

		return produto;
	}

}
