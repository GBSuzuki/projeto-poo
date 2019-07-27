package service;

import domain.Compra;
import domain.Receita;
import domain.Venda;
import domain.interfaces.ATransacao;
import service.interfaces.IBancoDeReceitas;
import service.interfaces.IEstoque;
import service.interfaces.ITranscaoService;

import javax.inject.Inject;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class TransacaoService implements ITranscaoService {

    private final IBancoDeReceitas bancoDeReceitas;
    private final IEstoque estoque;

    private Connection connect() {
        String url = "jdbc:sqlite:cafeteria.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private void insert(ATransacao transacao) {
        String sql = "INSERT INTO Transacoes (Nome, Tipo, Valor, Quantidade, Data) VALUES (?,?,?,?,?)";
        try {
            Connection con = connect();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, transacao.getNome());
            pstmt.setString(2, transacao.getTipo());
            pstmt.setFloat(3, transacao.getValorTotal());
            pstmt.setInt(4, transacao.getQuantidade());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String ts = sdf.format(timestamp);
            pstmt.setString(5, ts);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Inject
    public TransacaoService(IBancoDeReceitas bancoDeReceitas, IEstoque estoque) {
        this.bancoDeReceitas = bancoDeReceitas;
        this.estoque = estoque;
    }

    @Override
    public boolean efetuaCompra(ATransacao compra) {
        try {
            estoque.AdicionaMP(compra.getNome(), compra.getQuantidade(), compra.getValor());
            insert(compra);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean efetuaVenda(ATransacao venda) {
        Receita receita = bancoDeReceitas.getReceita(venda.getNome());
        for (Map.Entry<UUID, Integer> entry : receita.getIngredientes().entrySet())
            if (estoque.getMP(entry.getKey()).getQuantidade() < entry.getValue() * venda.getQuantidade())
                return false;
        insert(venda);
        return true;
    }

    @Override
    public ArrayList<ATransacao> getVendas(Timestamp date) {
        String sql = "SELECT * FROM Transacoes WHERE Tipo = \"Venda\" AND DATE(data) <= DATE(?)";
        ArrayList<ATransacao> retorno = new ArrayList<>();
        try {
            Connection con = connect();
            PreparedStatement pstmt = con.prepareStatement(sql);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String ts = sdf.format(date);
            pstmt.setString(1, ts);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                retorno.add(new Venda(
                        rs.getString("Nome"),
                        rs.getInt("Quantidade"),
                        rs.getInt("Valor"),
                        rs.getString("Data")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retorno;
    }

    @Override
    public ArrayList<ATransacao> getCompras(Timestamp date) {
        String sql = "SELECT * FROM Transacoes WHERE Tipo = \"Compra\" AND DATE(data) = DATE(?)";
        ArrayList<ATransacao> retorno = new ArrayList<>();
        try {
            Connection con = connect();
            PreparedStatement pstmt = con.prepareStatement(sql);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String ts = sdf.format(date);
            pstmt.setString(1, ts);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                retorno.add(new Compra(
                        rs.getString("Nome"),
                        rs.getInt("Quantidade"),
                        rs.getInt("Valor"),
                        rs.getString("Data")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retorno;
    }
}
