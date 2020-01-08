package com.example.lab10;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AsyncQuery extends AsyncTask<String[],Void,String[]> {
    private Connection conexionMySQL;
    private Statement st = null;
    private ResultSet rs = null;


    @Override
    protected String[] doInBackground(String[]... datos) {


        String sql = datos[0][5];
        String resultadoSQL = "";
        String[] totalResultadoSQL = null;
        int numColumnas = 0;
        int numFilas = 0;
        String SERVIDOR = datos[0][0];
        String PUERTO = datos[0][1];
        String BD = datos[0][2];
        String USUARIO = datos[0][3];
        String PASSWORD = datos[0][4];

        System.out.println(PASSWORD);

        try {
            conexionMySQL = DriverManager.getConnection("jdbc:mysql://" + SERVIDOR + ":" + PUERTO + "/" + BD,
                    USUARIO, PASSWORD);

            System.out.println(conexionMySQL);
            st = conexionMySQL.createStatement();

            System.out.println(st);
            rs = st.executeQuery(sql);
            System.out.println(rs);
            rs.last();
            numFilas = rs.getRow();
            if (numFilas == 0) {
                resultadoSQL = "No se ha producido ningún resultado. Revise la consulta realizada.\n";
            } else {
                for (int i = 1; i <= numColumnas; i++) {
                    if (i > 1) resultadoSQL += ",";
                    resultadoSQL += rs.getMetaData().getColumnName(i);
                }
                resultadoSQL += "\n";
                rs.beforeFirst();
                while (rs.next()) {
                    numColumnas = rs.getMetaData().getColumnCount();
                    for (int i = 1; i <= numColumnas; i++) {
                        if (i > 1) resultadoSQL += ",";
                        resultadoSQL += rs.getString(i);
                    }
                    resultadoSQL += "\n";
                }
            }
            totalResultadoSQL = new String[]{resultadoSQL, String.valueOf(numFilas), String.valueOf(numColumnas)};

        } catch (SQLException ex) {
            Log.d("Error de conexion", ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                st.close();
                conexionMySQL.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return totalResultadoSQL;
    }


}