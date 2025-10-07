package com.example.confianzamicro.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.confianzamicro.R;
import com.example.confianzamicro.auth.SessionManager;
import com.example.confianzamicro.domain.Advisor;
import com.example.confianzamicro.ui.menu.AdvisorMenuActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUser, edtPass;
    private TextView txtMsg;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_login);

        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        txtMsg = findViewById(R.id.txtMsg);

        findViewById(R.id.btnLogin).setOnClickListener(v -> {
            String u = edtUser.getText().toString().trim();
            String p = edtPass.getText().toString();

            if(u.isEmpty() || p.isEmpty()){
                txtMsg.setText("Complete usuario y contraseña");
                return;
            }

            if(u.equals("A001") && p.equals("1234")){
                SessionManager.get().login(new Advisor(u, "Asesor Confianza"));
                // ⭐⭐ LÍNEA CORREGIDA ⭐⭐
                startActivity(new Intent(LoginActivity.this, AdvisorMenuActivity.class));
                finish();
            } else {
                txtMsg.setText("Credenciales inválidas");
            }
        });
    }
}