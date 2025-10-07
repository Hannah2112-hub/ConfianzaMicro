package com.example.confianzamicro.ui.menu;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.confianzamicro.R;
import com.example.confianzamicro.ui.clients.ClientsActivity;
import com.example.confianzamicro.ui.tasks.TasksActivity;
import java.util.*;

public class AdvisorMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_advisor_menu);

        List<MenuOption> items = Arrays.asList(
                new MenuOption("Clientes", R.mipmap.ic_launcher, ClientsActivity.class),
                new MenuOption("Tareas", R.mipmap.ic_launcher, TasksActivity.class),
                new MenuOption("Cobranza", R.mipmap.ic_launcher, TasksActivity.class),
                new MenuOption("Solicitudes", R.mipmap.ic_launcher, TasksActivity.class),
                new MenuOption("Reportes", R.mipmap.ic_launcher, TasksActivity.class),
                new MenuOption("Configuración", R.mipmap.ic_launcher, TasksActivity.class)
        );

        RecyclerView rv = findViewById(R.id.rvMenu);
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        rv.setAdapter(new MenuAdapter(this, items));
    }
}