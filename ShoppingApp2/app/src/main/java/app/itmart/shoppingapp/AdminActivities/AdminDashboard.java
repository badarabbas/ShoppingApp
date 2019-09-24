package app.itmart.shoppingapp.AdminActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import app.itmart.shoppingapp.CommonActivities.LoginActivity;
import app.itmart.shoppingapp.R;

public class AdminDashboard extends AppCompatActivity {
    FirebaseAuth mAuth;
    CardView mManageCatCV,mManageProductsCV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        mAuth = FirebaseAuth.getInstance();
        mManageCatCV = findViewById(R.id.ManageCatCV);
        mManageProductsCV = findViewById(R.id.ManageProductsCV);



        mManageCatCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), ManageCategoriesActivity.class));
            }
        });
        mManageProductsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), ManageProductsActivity.class));
            }
        });

        Toolbar admintool = findViewById(R.id.AdminToolbar);
        setSupportActionBar(admintool);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);
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

            default:return super.onOptionsItemSelected(item);

        }

    }

}
