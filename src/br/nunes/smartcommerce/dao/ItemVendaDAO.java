package br.nunes.smartcommerce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.nunes.smartcommerce.model.Brand;
import br.nunes.smartcommerce.model.ItemVenda;
import br.nunes.smartcommerce.model.Produto;
import br.nunes.smartcommerce.model.Venda;

public class ItemVendaDAO  extends DAO<ItemVenda> {
	
	@Override
	public boolean create(ItemVenda itemVenda) {
		
		boolean retorno = false;
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO public.itemVenda ");
		sql.append("	(valor, idproduto, idlivro) ");
		sql.append("VALUES ");
		sql.append("	(?, ?, ?) ");
		
		PreparedStatement stat = null;
		
		try {
			stat = conn.prepareStatement(sql.toString());
			
			stat.setFloat(1, itemVenda.getValor());
			stat.setInt(2, itemVenda.getVenda().getId());
			stat.setInt(3, itemVenda.getProduto().getId());
			stat.execute();
			
			conn.commit();
			
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

	public List<ItemVenda> findByVenda(Venda venda) {
		List<ItemVenda> listaItemVenda = new ArrayList<ItemVenda>();
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  itemvenda.id, ");
		sql.append("  itemvenda.valor, ");
		sql.append("  itemvenda.idproduto, ");
		sql.append("  produtos.name, ");
		sql.append("  produtos.description, ");
		sql.append("  produtos.price, ");
		sql.append("  produtos.stock, ");
		sql.append("  produtos.release_date ");
		sql.append("FROM ");
		sql.append("  itemvenda , ");
		sql.append("  produtos  ");
		sql.append("WHERE ");
		sql.append("  itemvenda.idproduto = produtos.id AND ");
		sql.append("  itemvenda.idvenda = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, venda.getId());
			
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				ItemVenda item = new ItemVenda();
				item.setId(rs.getInt("id"));
				item.setValor(rs.getFloat("valor"));
				
				Produto produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setName(rs.getString("name"));
				produto.setDescription(rs.getString("description"));
				produto.setPrice(rs.getFloat("price"));
				produto.setStock(rs.getInt("stock"));
				
				item.setProduto(produto);
				
				item.setVenda(venda);
				
				listaItemVenda.add(item);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return listaItemVenda;
	}
	
	@Override
	public boolean update(ItemVenda itemVenda) {
		return false;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public List<ItemVenda> findAll() {
		return null;
	}

	@Override
	public ItemVenda findById(int id) {
		return null;
	}	
}
