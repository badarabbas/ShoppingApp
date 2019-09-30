package app.itmart.shoppingappuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.itmart.shoppingappuser.UserModels.CategoriesModel;

public class UserDashboardActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference mRef;
    RecyclerView mCatRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("Categories");
        mCatRecyclerView = findViewById(R.id.CatRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mCatRecyclerView.setLayoutManager(layoutManager);

        Toolbar admintool = findViewById(R.id.Toolbar);
        setSupportActionBar(admintool);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.signout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {


            case R.id.SignOut:
                mAuth.signOut();
                finish();
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }



    //Setting Adapter to get Categories Recycler


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<CategoriesModel> options = new FirebaseRecyclerOptions.Builder<CategoriesModel>()
                .setQuery(mRef, CategoriesModel.class)
                .build();


        FirebaseRecyclerAdapter<CategoriesModel,CategoryViewHolder> catadapter =
                new FirebaseRecyclerAdapter<CategoriesModel, CategoryViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, final int position , @NonNull CategoriesModel categoriesModel) {


                        categoryViewHolder.mCatName_Recyc_ItemTV.setText(categoriesModel.getCatname());
                        Glide.with(getBaseContext()).load(categoriesModel.getCatimageurl()).into(categoryViewHolder.mCatImage_Recyc_ItemIV);

                        categoryViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String category_id =getRef(position).getKey();
                                Intent toproducts = new Intent(UserDashboardActivity.this,CategoryProductsActivity.class);
                                toproducts.putExtra("category_id",category_id);
                                startActivity(toproducts);

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_recycler_item, parent, false);
                        UserDashboardActivity.CategoryViewHolder categoryViewHolder = new UserDashboardActivity.CategoryViewHolder(view);
                        return categoryViewHolder;
                    }
                };
        mCatRecyclerView.setAdapter(catadapter);
        catadapter.startListening();
    }



    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        public ImageView mCatImage_Recyc_ItemIV;
        public TextView mCatName_Recyc_ItemTV;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            mCatImage_Recyc_ItemIV = itemView.findViewById(R.id.CatImage_Recyc_ItemIV);
            mCatName_Recyc_ItemTV = itemView.findViewById(R.id.CatName_Recyc_ItemTV);

        }
    }
}
