package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import domain.MateriaPrima;
import domain.Receita;
import service.interfaces.IPersistenceService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersistenceService implements IPersistenceService {
    @Override
    public ArrayList<MateriaPrima> getEstoque() {
        return getJson("Estoque.json", MateriaPrima.class);
    }

    @Override
    public ArrayList<Receita> getBancoReceitas() {
        return getJson("BancoReceitas.json", Receita.class);
    }

    @Override
    public void setEstoque(ArrayList<MateriaPrima> materiaPrimas) {
        setJSON(materiaPrimas, "Estoque.json");
    }

    @Override
    public void setBancoReceitas(ArrayList<Receita> receitas) {
        setJSON(receitas, "BancoReceitas.json");
    }

    private void setJSON(Object src, String arquivo) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String json = gson.toJson(src);

        try {
            FileWriter writer = new FileWriter(arquivo);
            writer.write(json);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private <TOut extends Object> ArrayList<TOut> getJson(String arquivo, Class<TOut> type)
    {
        Gson gson = new Gson();
        ArrayList<TOut> obj = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            obj = gson.fromJson(br, obj.getClass());
        }
        finally {
            if(obj == null)
                return new ArrayList<TOut>();
            return obj;
        }
    }
}
