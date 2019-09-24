package app.itmart.shoppingapp.AdminActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import app.itmart.shoppingapp.AdminActivities.AdminModels.CategoriesModel;
import app.itmart.shoppingapp.CommonActivities.LoginActivity;
import app.itmart.shoppingapp.R;
import app.itmart.shoppingapp.adapters.CategoryAdapter;

public class ManageCategoriesActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference mRef;
    RecyclerView mCatRecyclerView;
    CategoryAdapter categoryAdapter;
    List<CategoriesModel> categoriesModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_categories);


        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("Categories");
        mCatRecyclerView = findViewById(R.id.CatRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mCatRecyclerView.setLayoutManager(layoutManager);


        categoriesModelList = new ArrayList<>();

        Toolbar admintool = findViewById(R.id.AdminToolbar);
        setSupportActionBar(admintool);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_catedory_menu, menu);
        inflater.inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.AddCat:

                startActivity(new Intent(getBaseContext(), AddCategoryActivity.class));
                return true;

            case R.id.SignOut:
                mAuth.signOut();
                finish();
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }


}
