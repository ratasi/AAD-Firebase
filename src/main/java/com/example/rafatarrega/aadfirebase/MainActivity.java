package com.example.rafatarrega.aadfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rafatarrega.aadfirebase.model.Usuarios;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText text_nickname;
    EditText text_nombre;
    EditText text_apellido;
    EditText text_direccion;
    EditText text_mail;
    Button btnAnyadir;
    ListView lista;
    Button btnMostrar;
    Button btnModificar;
    DatabaseReference bbdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_nombre = (EditText) findViewById(R.id.nombre);
        text_nickname = (EditText) findViewById(R.id.nickname);
        text_apellido = (EditText) findViewById(R.id.apellido);
        text_direccion = (EditText) findViewById(R.id.direccion);
        text_mail = (EditText) findViewById(R.id.mail);
        btnAnyadir = (Button) findViewById(R.id.btnAnyadir);
        lista = (ListView) findViewById(R.id.ListView);
        btnMostrar = (Button) findViewById(R.id.btnMostrar);
        btnModificar = (Button) findViewById(R.id.btnModificar);

        bbdd = FirebaseDatabase.getInstance().getReference("usuarios");

        bbdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayAdapter<String> adaptador;
                ArrayList<String> listado = new ArrayList<>();

                for (DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                   Usuarios usuarios =  datasnapshot.getValue(Usuarios.class);

                   String nickname = usuarios.getNickname();
                   listado.add(nickname);

                }

                adaptador = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, listado);
                lista.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnAnyadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nickname = text_nickname.getText().toString();
                String nombre = text_nombre.getText().toString();;
                String apellido = text_apellido.getText().toString();
                String direccion = text_direccion.getText().toString();
                String mail = text_mail.getText().toString();

                if (!TextUtils.isEmpty(nickname)){
                    if (!TextUtils.isEmpty(nombre)) {
                        if (!TextUtils.isEmpty(apellido)) {
                            if (!TextUtils.isEmpty(direccion)) {
                                if (!TextUtils.isEmpty(mail)) {

                                    Usuarios u = new Usuarios(nickname, nombre, apellido, direccion, mail);
                                    String clave = bbdd.push().getKey();
                                    bbdd.child(clave).setValue(u);


                                    Toast.makeText(MainActivity.this, "Usuario añadido", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(MainActivity.this, "Debes introducir un mail", Toast.LENGTH_SHORT).show();
                                }
                            } else{
                                Toast.makeText(MainActivity.this, "Debes introducir una direccion", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Debes introducir un apellido", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this, "Debes introducir un nombre", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Debes introducir un nickname", Toast.LENGTH_SHORT).show();
                }
            }
        });

       btnMostrar.setOnClickListener(new View.OnClickListener() {

         public void onClick(View view) {

               Query q = bbdd.orderByChild(getString(R.string.campo_apellido)).equalTo("Alma");

               q.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       int cont=0;
                       for (DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                           cont++;
                           Toast.makeText(MainActivity.this, "He encontrado" + cont, Toast.LENGTH_SHORT).show();

                       }
                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });
            }
        });

       btnModificar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String nickname = text_nickname.getText().toString();

               if (!TextUtils.isEmpty(nickname)){
                   Query q=bbdd.orderByChild(getString(R.string.campo_nickname)).equalTo(nickname);

                   q.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {
                           for (DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                               String clave = datasnapshot.getKey();
                               bbdd.child(clave).child(getString(R.string.campo_nombre)).setValue(text_nombre.getText().toString());
                               bbdd.child(clave).child(getString(R.string.campo_apellido)).setValue(text_apellido.getText().toString());
                               bbdd.child(clave).child(getString(R.string.campo_direccion)).setValue(text_direccion.getText().toString());
                               bbdd.child(clave).child(getString(R.string.campo_mail)).setValue(text_mail.getText().toString());


                           }
                       }

                       @Override
                       public void onCancelled(DatabaseError databaseError) {

                       }
                   });

                   Toast.makeText(MainActivity.this, "Los datos del usuario " + nickname + " se ha modificado con éxito", Toast.LENGTH_SHORT).show();
               }
               else{
                   Toast.makeText(MainActivity.this, "Debes de introducir un nickname", Toast.LENGTH_SHORT).show();
               }
           }
       });
    }
}
