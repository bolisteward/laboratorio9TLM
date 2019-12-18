package com.example.laboratorio9tlm;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText edt_codigo, edt_descripcion, edt_precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_codigo = findViewById(R.id.edt_code);
        edt_descripcion = findViewById(R.id.edt_descripcion);
        edt_precio = findViewById(R.id.edt_precio);
    }

    public void ingresar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String cod = edt_codigo.getText().toString();
        String descri = edt_descripcion.getText().toString();
        String pre = edt_precio.getText().toString();
        bd.execSQL("insert into articulos (codigo,descripcion,precio) values (" + cod + ",'" + descri + "'," + pre + ")");
        bd.close();
        edt_codigo.setText("");
        edt_descripcion.setText("");
        edt_precio.setText("");
        Toast.makeText(this, "Se cargaron los datos del artículo",
                Toast.LENGTH_SHORT).show();
    }

    public void consultaporcodigo(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        String cod = edt_codigo.getText().toString();
        Cursor fila = bd.rawQuery(
                "select descripcion,precio from articulos where codigo=" + cod, null);
        if (fila.moveToFirst()) {
            edt_descripcion.setText(fila.getString(0));
            edt_precio.setText(fila.getString(1));
        } else
            Toast.makeText(this, "No existe un artículo con dicho código",
                    Toast.LENGTH_SHORT).show();
        bd.close();
    }

    public void consultapordescripcion(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        String descri = edt_descripcion.getText().toString();
        Cursor fila = bd.rawQuery(
                "select codigo,precio from articulos where descripcion like '" + descri + "'", null);
        if (fila.moveToFirst()) {
            edt_codigo.setText(fila.getString(0));
            edt_precio.setText(fila.getString(1));
        } else
            Toast.makeText(this, "No existe un artículo con dicha descripción",
                    Toast.LENGTH_SHORT).show();
        bd.close();
    }

    public void eliminar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String cod = edt_codigo.getText().toString();
        bd.execSQL("delete from articulos where codigo = " + cod);
        bd.close();
        edt_codigo.setText("");
        edt_descripcion.setText("");
        edt_precio.setText("");
        Toast.makeText(this, "Se borró el artículo con dicho código",
                Toast.LENGTH_SHORT).show();
    }

    public void modificacion(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String cod = edt_codigo.getText().toString();
        String descri = edt_descripcion.getText().toString();
        String pre = edt_precio.getText().toString();
        bd.execSQL("update articulos set codigo=" + cod + ",descripcion='" + descri + "',precio=" + pre + " where codigo=" + cod);
        bd.close();
        Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT)
                .show();
    }
}

