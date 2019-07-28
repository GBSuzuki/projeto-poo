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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class PersistenceService implements IPersistenceService {
    @Override
    public ArrayList<MateriaPrima> getEstoque() {
        return getJsonEstoque("Estoque.json");
    }

    @Override
    public ArrayList<Receita> getBancoReceitas() {
        return getJsonReceitas("BancoReceitas.json");
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

    private ArrayList<Receita> getJsonReceitas(String arquivo)
    {
        Gson gson = new Gson();
        ArrayList<Receita> obj = null;
        try {
            var fooType = new TypeToken<ArrayList<Receita>>() {}.getType();
            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            obj = gson.fromJson(br, fooType);
        }
        finally {
            if(obj == null)
                return new ArrayList<Receita>();
            return obj;
        }
    }

    private ArrayList<MateriaPrima> getJsonEstoque(String arquivo)
    {
        Gson gson = new Gson();
        ArrayList<MateriaPrima> obj = null;
        try {
            var fooType = new TypeToken<ArrayList<MateriaPrima>>() {}.getType();
            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            obj = gson.fromJson(br, fooType);
        }
        finally {
            if(obj == null)
                return new ArrayList<MateriaPrima>();
            return obj;
        }
    }
}
