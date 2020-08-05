package br.nunes.smartcommerce.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.catalina.core.ApplicationPart;

import br.nunes.smartcommerce.application.Session;
import br.nunes.smartcommerce.application.Util;
import br.nunes.smartcommerce.dao.ProdutoDAO;
import br.nunes.smartcommerce.model.Brand;
import br.nunes.smartcommerce.model.Produto;
import br.nunes.smartcommerce.model.User;

@Named("registerProductController")
@ViewScoped
public class AdminRegisterProductController extends Controller<Produto> {

	private List<Produto> listaProduto;
	private static final long serialVersionUID = -7912559731952920320L;
	
	private User usuarioLogado;
	
	private ApplicationPart imagem;

	public AdminRegisterProductController() {
		super(new ProdutoDAO());
		// TODO Auto-generated constructor stub
	}

	public List<Produto> getListaProduto() {
		if (listaProduto == null) {
			ProdutoDAO dao = new ProdutoDAO();
			listaProduto = dao.findAll();
		}

		return listaProduto;
	}
	
	public String encerrarSessao() {
		// encerrando a sessao
		Session.getInstance().invalidateSession();
		return "/index.xhtml?faces-redirect=true";
	}

	public User getUsarioLogado() {
		if (usuarioLogado == null) // buscando o usuario da sessao
			usuarioLogado = (User) Session.getInstance().getAttribute("usuarioLogado");	
		return usuarioLogado;
	}
	
	public String include() {
		if (validarDados()) {	
			String caminhoImagem = "";
			if (imagem != null && imagem.getSubmittedFileName() != null) {
				caminhoImagem = "C:\\Users\\shika\\OneDrive\\Documentos\\Eclipse\\Workspace\\Dev-Server1\\SmartCommerce\\WebContent\\uploads\\" + imagem.getSubmittedFileName();
				

				try {
					// cria um espaço de memória que vai armazenar o conteúdo da imagem PORQUE A IMAGEM é UM ARRAY DE BYTES
					byte[] bytesImagem = new byte[(int) imagem.getSize()];
					// lê o conteudo da imagem e joga dentro do array de bytes
					imagem.getInputStream().read(bytesImagem);
					// cria uma referência para o arquivo que será criado no lado do server
					File f = new File(caminhoImagem);
					// cria o objeto que irá manipular o arquivo criado
					FileOutputStream fos = new FileOutputStream(f);
					// escreve o conteúdo da imagem (upload) dentro do arquivo no servidor
					fos.write(bytesImagem);

					fos.close();
					
					getEntity().setImage(caminhoImagem);

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if (dao.create(getEntity())) {
				try {
					limpar();
				   
					FacesContext.getCurrentInstance().addMessage(
					null, new FacesMessage("Inserção Realizada com Sucesso!"));
					
					
					FacesContext.getCurrentInstance()
						    .getExternalContext()
						    .getFlash().setKeepMessages(true);
				
					Thread.sleep(1000);
					return "/admin_page/product_home.xhtml?faces-redirect=true";
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				Util.addInfoMessage("Erro ao Inserir no banco de dados.");
				return "";
			}
		}
		return "";
	}
	
	public String update() {
		if (validarDados()) {		
			if (dao.update(getEntity())) {
				try {
					limpar();
					
					FacesContext.getCurrentInstance().addMessage(
							null, new FacesMessage("Alteração Realizada com Sucesso!"));
					
					
					FacesContext.getCurrentInstance()
					.getExternalContext()
					.getFlash().setKeepMessages(true);
					
					Thread.sleep(1000);
					return "/admin_page/product_home.xhtml?faces-redirect=true";
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} else {
				Util.addInfoMessage("Erro ao Alteração no banco de dados.");
				return "";
			}
		}
		return "";
	}
	
	public Brand[] getListaTipoProduto() {
		return Brand.values();
	}

	@Override
	public Produto getEntity() {

		if (entity == null) {
			entity = new Produto();
		}
		return entity;
		
	}
	public ApplicationPart getImagem() {
		return imagem;
	}

	public void setImagem(ApplicationPart imagem) {
		this.imagem = imagem;
	}

	@Override
	public boolean validarDados() {
		if (getEntity().getName().isBlank() ) {
			Util.addErrorMessage("O campo nome deve ser informado.");
			return false;
		}
				
		return true;
	}
}
