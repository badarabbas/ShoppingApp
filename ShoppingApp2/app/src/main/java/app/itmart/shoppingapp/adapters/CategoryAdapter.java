package app.itmart.shoppingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.itmart.shoppingapp.AdminActivities.AdminModels.CategoriesModel;
import app.itmart.shoppingapp.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    List<CategoriesModel> categoriesModels;
    private LayoutInflater mLayoutInflater;

    public CategoryAdapter(Context context, List<CategoriesModel> categoriesModels) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.categoriesModels = categoriesModels;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mLayoutInflater.inflate(R.layout.cat_recycler_item,parent,false);

        return new CategoryAdapter.CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        CategoriesModel categoriesModel = categoriesModels.get(position);

        holder.Cat_Recyc_ItemTV.setText(categoriesModel.getCatname());

    }

    @Override
    public int getItemCount() {
        return categoriesModels.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView Cat_Recyc_ItemTV;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            Cat_Recyc_ItemTV = itemView.findViewById(R.id.Cat_Recyc_ItemTV);
        }
    }
}
