package app.itmart.shoppingappuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import app.itmart.shoppingappuser.UserModels.ProductsModel;

public class CategoryProductsActivity extends AppCompatActivity {
String get_category_id,get_cat_name;
RecyclerView mProductRecyclerView;
DatabaseReference mCatRef;
FirebaseAuth mAuth;
Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_products);

        mCatRef = FirebaseDatabase.getInstance().getReference().child("Categories");
        mAuth = FirebaseAuth.getInstance();

        mProductRecyclerView = findViewById(R.id.ProductRecyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        mProductRecyclerView.setLayoutManager(layoutManager);

        get_category_id = getIntent().getExtras().get("category_id").toString();




        Toolbar admintool = findViewById(R.id.Toolbar);
        setSupportActionBar(admintool);






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.signout_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.SignOut:
                mAuth.signOut();
                finish();
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void getCatname(){
        mCatRef.child(get_category_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                get_cat_name = dataSnapshot.child("catname").getValue().toString();

                query = FirebaseDatabase.getInstance().getReference("Products")
                        .orderByChild("prodcat").equalTo(get_cat_name);
                Recycler();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }




    //Setting Adapter to get Products Recycler

    public void Recycler(){
        FirebaseRecyclerOptions<ProductsModel> options = new FirebaseRecyclerOptions.Builder<ProductsModel>()
                .setQuery(query,ProductsModel.class)
                .build();

        FirebaseRecyclerAdapter<ProductsModel,ProductViewHolder> productadapter =
                new FirebaseRecyclerAdapter<ProductsModel, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, final int position, @NonNull ProductsModel productsModel) {

                        productViewHolder.mProduct_Cat_Recyc_ItemTV.setText(productsModel.getProdcat());

                        productViewHolder.mProduct_Price_Recyc_ItemTV.setText("PKR. "+productsModel.getProdprice());
                        Glide.with(getBaseContext()).load(productsModel.getProdimageurl()).into(productViewHolder.mProduct_Image_Recyc_ItemIV);


                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_recycler_item,parent,false);
                        CategoryProductsActivity.ProductViewHolder productViewHolder = new CategoryProductsActivity.ProductViewHolder(view);

                        return productViewHolder;

                    }
                };
        mProductRecyclerView.setAdapter(productadapter);
        productadapter.startListening();
    }

    @Override
    protected void onStart() {
        super.onStart();

        getCatname();


    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        public ImageView mProduct_Image_Recyc_ItemIV;
        public TextView mProduct_Cat_Recyc_ItemTV,mProduct_Price_Recyc_ItemTV;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            mProduct_Image_Recyc_ItemIV = itemView.findViewById(R.id.Product_Image_Recyc_ItemIV);
            mProduct_Cat_Recyc_ItemTV = itemView.findViewById(R.id.Product_Cat_Recyc_ItemTV);
            mProduct_Price_Recyc_ItemTV = itemView.findViewById(R.id.Product_Price_Recyc_ItemTV);


        }
    }

}
