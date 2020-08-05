package br.nunes.smartcommerce.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.nunes.smartcommerce.model.ItemVenda;
import br.nunes.smartcommerce.model.User;
import br.nunes.smartcommerce.model.Venda;

public class VendaDAO extends DAO<Venda> {

	@Override
	public boolean create(Venda venda) {
		
		boolean retorno = false;
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO public.venda");
		sql.append(" (data, idusuario) ");
		sql.append("VALUES ");
		sql.append(" (?, ?) " );
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			stat.setDate(1, Date.valueOf(venda.getData()));
			stat.setInt(2, venda.getUsuario().getId());
			
			stat.execute();
			
			// obtendo o id gerado pela tabela do banco de dados
			ResultSet rs = stat.getGeneratedKeys();
			rs.next();
			venda.setId(rs.getInt("id"));
			// inserindo os itens de venda
			
			ItemVendaDAO dao = new ItemVendaDAO();
			for (ItemVenda itemVenda : venda.getListaItemVenda()) {
				// informando quem eh o pai da crianca
				itemVenda.setVenda(venda);
				// salvando no banco de dados
//				if (dao.create(itemVenda) == false) {
//					throw new Exception("Erro ao incluir um item de venda");
//				}
				if (createItemVenda(itemVenda, conn) == false) {
					throw new Exception("Erro ao incluir um item de venda");
				}				
				
			}
			
			conn.commit();

			System.out.println("Inclusão realizada com sucesso.");
			
			retorno = true;

		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return retorno;	
		
	}
	
	// 
	private boolean createItemVenda(ItemVenda itemVenda, Connection conn) {
		
		boolean retorno = false;
//		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO public.itemVenda ");
		sql.append("	(valor, idvenda, idproduto) ");
		sql.append("VALUES ");
		sql.append("	(?, ?, ?) ");
		
		PreparedStatement stat = null;
		
		try {
			stat = conn.prepareStatement(sql.toString());
			
			stat.setFloat(1, itemVenda.getValor());
			stat.setInt(2, itemVenda.getVenda().getId());
			stat.setInt(3, itemVenda.getProduto().getId());
			stat.execute();
			
//			conn.commit();
			
			retorno = true;
		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
	//		closeConnection(conn);
		}
		return retorno;	
		
	}
	
	public List<Venda> findByUsuario(int idUsuario) {
		List<Venda> listaVenda = new ArrayList<Venda>();
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  v.id, ");
		sql.append("  v.data, ");
		sql.append("  u.id as idusuario, ");
		sql.append("  u.name, ");
		sql.append("  u.email,  ");
		sql.append("  u.password, ");
		sql.append("  u.is_admin, ");			
		sql.append("FROM ");
		sql.append("  public.venda v, ");
		sql.append("  public.user u ");
		sql.append("WHERE ");
		sql.append("  v.idusuario = u.id AND ");
		sql.append("  u.id = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());

			stat.setInt(1, idUsuario);
			
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				Venda venda = new Venda();
				venda.setId(rs.getInt("id"));
				venda.setData( rs.getDate("data").toLocalDate() );
				venda.setUsuario(new User());
				venda.getUsuario().setId(rs.getInt("idusuario"));
				venda.getUsuario().setName(rs.getString("name"));
				venda.getUsuario().setLogin(rs.getString("login"));
				venda.getUsuario().setPassword(rs.getString("password"));
				venda.getUsuario().setIs_admin((rs.getBoolean("is_admin")));
				// e os itens de venda?!!?
				ItemVendaDAO dao = new ItemVendaDAO();
				venda.setListaItemVenda(dao.findByVenda(venda));
				listaVenda.add(venda);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return listaVenda;
	}

	@Override
	public List<Venda> findAll() {
		List<Venda> listaVenda = new ArrayList<Venda>();
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  v.id, ");
		sql.append("  v.data, ");
		sql.append("  u.idusuario, ");
		sql.append("  u.name, ");
		sql.append("  u.email,  ");
		sql.append("  u.password, ");
		sql.append("  u.is_admin, ");				
		sql.append("FROM ");
		sql.append("  public.venda v, ");
		sql.append("  public.user u ");
		sql.append("WHERE ");
		sql.append("  v.idusuario = u.id ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
					
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				Venda venda = new Venda();
				venda.setId(rs.getInt("id"));
				venda.setData( rs.getDate("data").toLocalDate() );
				venda.setUsuario(new User());
				venda.getUsuario().setId(rs.getInt("idusuario"));
				venda.getUsuario().setName(rs.getString("nome"));
				venda.getUsuario().setLogin(rs.getString("login"));
				venda.getUsuario().setPassword(rs.getString("password"));
				venda.getUsuario().setIs_admin((rs.getBoolean("is_admin")));

				// e os itens de venda?!!?
				//venda.setListaItemVenda(listaItemVenda);
				
				listaVenda.add(venda);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return listaVenda;
	}
	
	@Override
	public Venda findById(int id) {
		Venda venda = null;
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  v.id, ");
		sql.append("  v.data, ");
		sql.append("  u.idusuario, ");
		sql.append("  u.name, ");
		sql.append("  u.email,  ");
		sql.append("  u.password, ");
		sql.append("  u.is_admin, ");				
		sql.append("FROM ");
		sql.append("  public.venda v, ");
		sql.append("  public.user u ");
		sql.append("WHERE ");
		sql.append("  v.idusuario = u.id AND ");
		sql.append("  u.id = ? ");

		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			ResultSet rs = stat.executeQuery();
			while(rs.next()) {
				venda = new Venda();
				venda.setId(rs.getInt("id"));
				venda.setData( rs.getDate("data").toLocalDate() );
				venda.setUsuario(new User());
				venda.getUsuario().setId(rs.getInt("idusuario"));
				venda.getUsuario().setName(rs.getString("nome"));
				venda.getUsuario().setLogin(rs.getString("login"));
				venda.getUsuario().setPassword(rs.getString("password"));
				venda.getUsuario().setIs_admin((rs.getBoolean("is_admin")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeStatement(stat);
			closeConnection(conn);
		}
		return venda;
	}			

	
	@Override
	public boolean update(Venda venda)  {
		return false;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}
}
