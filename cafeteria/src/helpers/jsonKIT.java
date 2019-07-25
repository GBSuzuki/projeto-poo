package helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import domain.MateriaPrima;
import domain.Receita;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class jsonKIT {

    public static void setJSON(Object src, String arquivo) {
        // Comprimido
        // Gson gson = new Gson();

        // Bonito
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

    public static ArrayList<MateriaPrima> getJsonEstoque() {
        Gson gson = new Gson();
        ArrayList<MateriaPrima> obj = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader("Estoque.json"));
            obj = gson.fromJson(br, new TypeToken<List<MateriaPrima>>() {
            }.getType());
        } catch (IOException e) {
            //e.printStackTrace();
        }
        if(obj == null)
            return new ArrayList<MateriaPrima>();
        return obj;
    }

    public static ArrayList<Receita> getJsonBancoReceitas() {
        Gson gson = new Gson();
        ArrayList<Receita> obj = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader("BancoReceitas.json"));
            obj = gson.fromJson(br, new TypeToken<List<Receita>>() {
            }.getType());
        } catch (IOException e) {
            //e.printStackTrace();
        }
        if(obj == null)
            return new ArrayList<Receita>();
        return obj;
    }
}
