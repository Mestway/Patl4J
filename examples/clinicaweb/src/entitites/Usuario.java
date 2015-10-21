package entitites;

import java.util.Date;

import entitites.util.TipoUsuario;



/**
 * Guardar os dados Pessoais de um usuário do sistema.
 * @author alessandro87
 *
 */
public abstract class Usuario extends ObjPersistente 
{

	/** Deve ser um email pertencente ao Gmail*/
	private String gmail;
	private String idCalendario;
	
	public Usuario(String log, String senha, String nomeCompleto, Date dataNasc, String cpf,
			String cep,
			String email, String tel,String cel) {
		super();
		this.login = log;
		this.senha = senha;
		this.nomeCompleto = nomeCompleto;
		this.dataNasc = dataNasc;
		this.cpf = cpf;
		this.cep = cep;
		this.email = email;
		this.tel = tel;
		this.cel = cel;
		this.idCalendario = "";
	}

	/**
	 * Dados para acesso a sessao
	 */
	private String login;
	private String senha;
	
	/**  Dados Pessoais*/
	private String nomeCompleto;
	private Date dataNasc;
	private String cpf;
	private String profissao;
	private String escolaridade;
	private String estadoCivil;
	private String sexo;
	private String email;

	
	/** Endereço */
	private String cep;
	private String bairro;
	private String estado;
	private String cidade;
	private String complemento;
	private String rua;
	private int numero;

	
	/** Contato */
	private String tel;
	private String cel;
	

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public Date getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public String getEscolaridade() {
		return escolaridade;
	}

	public void setEscolaridade(String escolaridade) {
		this.escolaridade = escolaridade;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGmail() {
		return gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCel() {
		return cel;
	}

	public void setCel(String cel) {
		this.cel = cel;
	}

		
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getTipoUsuario() {
		return TipoUsuario.NAO_CADASTRADO;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
	public boolean temCalendario() {
		if (idCalendario.equals("")){
			return false;
		}
		return true;
	}

	public String getIdGoogleCalendario() {
		return idCalendario;
	}

	public void setIdGoogleCalendario(String idCalendario) {
		this.idCalendario = idCalendario;
	}
}
