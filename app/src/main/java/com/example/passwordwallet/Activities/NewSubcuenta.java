package com.example.passwordwallet.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.passwordwallet.BD.BDSubcuentas;
import com.example.passwordwallet.BD.DBCustomer;
import com.example.passwordwallet.R;

public class NewSubcuenta extends AppCompatActivity {
    EditText edtName,edtEmail,edtContraseña,edtFecha;

    //para el género
    String gender;

    //administrador de la base de datos
    BDSubcuentas admin = new BDSubcuentas(this, "cuenta_secund", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_subcuenta);
        //vincular las vistas
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtContraseña=findViewById(R.id.edtContraseña);
        edtFecha=findViewById(R.id.edtFecha);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_clean:
                clean();
                break;
            case R.id.action_back:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void clean() {
        edtName.setText("");
        edtEmail.setText("");
        edtContraseña.setText("");
        edtFecha.setText("");
        edtName.requestFocus();

        Toast.makeText(this, getString(R.string.msg_clean), Toast.LENGTH_SHORT).show();
    }
    //guardar
    public void saveNew(View view){
        try {
            //objeto administrador de la bd
            SQLiteDatabase db = admin.getWritableDatabase();

            //para guardar
            ContentValues data = new ContentValues();

            if(edtName.getText().toString().isEmpty() || edtEmail.getText().toString().isEmpty()
                    || edtContraseña.getText().toString().isEmpty()|| edtFecha.getText().toString().isEmpty() ) {
                //pedir todos los datos
                Toast.makeText(this, getString(R.string.msg_data), Toast.LENGTH_SHORT).show();
            }else{
                //recolectar los datos
                data.put("cuentaprincipal_id", edtName.getText().toString());
                data.put("email", edtEmail.getText().toString());
                data.put("password", edtContraseña.getText().toString());
                data.put("fecha", edtFecha.getText().toString());

                //insertar
                db.insert("cuenta_secund", null, data);
                //cerrar la conexión
                db.close();

                Toast.makeText(this, getString(R.string.msg_save),
                        Toast.LENGTH_LONG).show();
            }
        }catch (Exception ex){
            Toast.makeText(this, getString(R.string.msg_error) + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

}
