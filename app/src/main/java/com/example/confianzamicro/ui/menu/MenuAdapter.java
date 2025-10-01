package com.example.confianzamicro.ui.menu;

import android.content.*;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.confianzamicro.R;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.VH> {
    private final List<MenuOption> data;
    private final Context ctx;

    public MenuAdapter(Context ctx, List<MenuOption> data){
        this.ctx = ctx;
        this.data = data;
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title;
        VH(View v){
            super(v);
            icon = v.findViewById(R.id.imgIcon);
            title = v.findViewById(R.id.txtTitle);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup p, int vt){
        return new VH(LayoutInflater.from(p.getContext())
                .inflate(R.layout.item_menu, p, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int i){
        MenuOption m = data.get(i);
        h.icon.setImageResource(m.iconRes);
        h.title.setText(m.title);
        h.itemView.setOnClickListener(v ->
                ctx.startActivity(new Intent(ctx, m.target))
        );
    }

    @Override
    public int getItemCount(){ return data.size(); }
}