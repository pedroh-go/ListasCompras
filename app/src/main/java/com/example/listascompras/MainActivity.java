package com.example.listascompras;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private TextView footer;
    private EditText editText;
    private CheckBox checkBox;
    private Spinner spinner;
    private ArrayList<Produto> produtos;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listview);
        footer = (TextView)findViewById(R.id.footer);
        editText = (EditText) findViewById(R.id.edittext);
        checkBox = (CheckBox) findViewById(R.id.checkboxZ);
        spinner = (Spinner) findViewById(R.id.spinner);

        produtos = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, produtos);
        listView.setAdapter(arrayAdapter);

        //Cancelar a escolkha do produto quando o usuário de um clique longo
        AdapterView.OnItemLongClickListener itemLongClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> lisView, View view, int position, long id) {
                final int localPosition = position;

                //Janela de diálogo para o usuário confirmar a remoção
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Remover Produto da Lista")
                        .setMessage("Você realmente deseja remover o produto da lista?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            //caso confirme a remoção
                            @Override
                            public void onClick(DialogInterface dialoge, int whichButton) {
                                produtos.remove(localPosition);
                                arrayAdapter.notifyDataSetChanged();
                                updateFooter();
                            }}).setNegativeButton(android.R.string.no, null).show();
                return true;
            }
        };
        listView.setOnItemLongClickListener(itemLongClickListener);
    }

    //Atualizar informações Soma
    public void updateFooter(){
        float total = 0;
        float urgente = 0;
        for(int i = 0; i < produtos.size(); i++){
            Produto produto = produtos.get(i);
            if(produto.isUrgente()){
                urgente += produto.getPreco();
            }
            total +=produto.getPreco();
        }
        footer.setText("Total = "+total+ " : Urgente = "+urgente);
    }

    //Adicionar um item na listView
    public void addProduto(View view){
        String item = String.valueOf(spinner.getSelectedItemId());
        boolean checked = checkBox.isChecked();
        float preco = Float.parseFloat(editText.getText().toString());

        Produto produto = new Produto(preco, item, checked);
        produtos.add(produto);
        arrayAdapter.notifyDataSetChanged();
        updateFooter();
    }
}









