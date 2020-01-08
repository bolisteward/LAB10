package com.example.lab10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String serverIP = "remotemysql.com";
    private String port = "3306";
    private String userMySQL = "i8i4zh6ZJb";
    private String pwdMySQL = "YFWFa4tP0D";
    private String database = "i8i4zh6ZJb";
    private String[] datosConexion = null;
    private TextView consulta;
    private EditText edtConsulta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        consulta = findViewById(R.id.txtTexto);
        edtConsulta = findViewById(R.id.edtConsulta);
    }

    public void mostrarResultados(View view) {
        String[] resultadoSQL = null;
        try {
            datosConexion = new String[]{
                    serverIP,
                    port,
                    database,
                    userMySQL,
                    pwdMySQL,
                    edtConsulta.getText().toString()
            };
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver).newInstance();
            resultadoSQL = new AsyncQuery().execute(datosConexion).get();
            Toast.makeText(MainActivity.this, "Conexión Establecida", Toast.LENGTH_LONG).show();

            System.out.println(resultadoSQL);

            String resultadoConsulta = resultadoSQL[0];
            String numFilas = resultadoSQL[1];
            String numColumnas = resultadoSQL[2];
            consulta.setText(resultadoConsulta + "\nNúmero de filas devueltas: " +
                    numFilas + "\nNúmero de columnas devueltas: " + numColumnas);
        } catch (Exception ex) {
            Toast.makeText(this, "Error al obtener resultados de la consulta Transact-SQL: "
                    + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
