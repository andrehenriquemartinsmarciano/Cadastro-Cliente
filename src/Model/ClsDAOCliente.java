package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClsDAOCliente {
    //Atributo da classe
    private Connection conexao;
    private String url="jdbc:mysql://localhost:3306/loja";
    private String usuario="root";
    private String senha="99210361";
    //Métodos
    public Connection GetConexao(){
        try { //Tentar executar
            conexao=DriverManager.getConnection(url, usuario,senha);
        } catch (SQLException ex) { //Se erro, o sistema captura o erro
            System.out.println("Exceção: "+ex);
        }
        return conexao;
    }
    public boolean inserir(ClsCliente objCliente){
        String sql="insert into cliente (codigo,nome) values(?,?)";
        int codigo = objCliente.getCodigo();
        String nome = objCliente.getNome();
        boolean resposta = false;
        conexao = this.GetConexao();
        try {
            PreparedStatement declaracao = conexao.prepareStatement(sql);
            declaracao.setInt(1, codigo);
            declaracao.setString(2, nome);
            declaracao.execute();
            resposta=true;
        } catch (SQLException ex) {
            System.out.println("Exceção: "+ex);
        }
        return resposta;
    }
    public String pesquisarPorCodigo(int codigo){
        String sql="select * from cliente where codigo="+codigo;
        String nome="";
        conexao = this.GetConexao();
        try {
            PreparedStatement declaracao = conexao.prepareStatement(sql);
            ResultSet rs = declaracao.executeQuery();
            if (rs.next())
                nome=rs.getString("nome");
        } catch (SQLException ex) {
            System.out.println("Exceção: "+ex);
        }
        return nome;
    }
    public boolean atualizar(int codigo, String novoNome){
        String sql="update cliente set nome=? where codigo=?";
        boolean resposta = false;
        conexao = this.GetConexao();
        try {
            PreparedStatement declaracao = conexao.prepareStatement(sql);
            declaracao.setString(1, novoNome);
            declaracao.setInt(2, codigo);
            declaracao.execute();
            resposta=true;
        } catch (SQLException ex) {
            System.out.println("Exceção: "+ex);
        }
        return resposta;
    }
    public boolean excluir(int codigo){
        String sql="delete from cliente where codigo=?";
        boolean resposta = false;
        conexao = this.GetConexao();
        try {
            PreparedStatement declaracao = conexao.prepareStatement(sql);
            declaracao.setInt(1, codigo);
            declaracao.execute();
            resposta=true;
        } catch (SQLException ex) {
            System.out.println("Exceção: "+ex);
        }
        return resposta;
    }
    public ArrayList pesquisarTodos(){
        String sql="select * from cliente order by nome";
        ArrayList clientes = new ArrayList();
        conexao = this.GetConexao();
        String nome;
        int codigo;
        try {
            PreparedStatement declaracao = conexao.prepareStatement(sql);
            ResultSet rs = declaracao.executeQuery();
            while (rs.next()){
                codigo=rs.getInt("codigo");
                nome=rs.getString("nome");
                ClsCliente objCliente = new ClsCliente(codigo,nome);
                clientes.add(objCliente);
            }    
        } catch (SQLException ex) {
            System.out.println("Exceção: "+ex);
        }
        return clientes;
    }
}