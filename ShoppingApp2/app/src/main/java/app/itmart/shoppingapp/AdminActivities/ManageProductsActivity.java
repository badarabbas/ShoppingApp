package app.itmart.shoppingapp.AdminActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import app.itmart.shoppingapp.R;

public class ManageProductsActivity extends AppCompatActivity {
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_products);

        mAuth = FirebaseAuth.getInstance();
        Toolbar admintool = findViewById(R.id.AdminToolbar);
        setSupportActionBar(admintool);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu,menu);
        inflater.inflate(R.menu.add_product_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.AddProduct:

                startActivity(new Intent(getBaseContext(),AddProductsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
