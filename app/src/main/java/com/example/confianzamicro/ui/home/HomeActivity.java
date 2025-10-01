package com.example.confianzamicro.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.confianzamicro.R;
import com.example.confianzamicro.auth.SessionManager;
import com.example.confianzamicro.domain.Advisor;

public class HomeActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle b){
        super.onCreate(b); setContentView(R.layout.activity_home);
        Advisor a = SessionManager.get().current();
        ((TextView)findViewById(R.id.textWelcome))
                .setText(a!=null ? "Bienvenido, "+a.getFullName()+" ("+a.getCode()+")" : "Sin sesión");
    }
}
