package app.itmart.shoppingapp.AdminActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import app.itmart.shoppingapp.CommonActivities.LoginActivity;
import app.itmart.shoppingapp.R;

public class AddProductsActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference mRef;
    EditText mProductNameET,mProductDescET,mProductPriceET;
    String mProductNameETSTR,mProductDescETSTR,mProductPriceETSTR;
    Spinner mCategoriesSPIN;
    Button mAddProductBTN;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> spinnerList;
    ValueEventListener valueListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);

        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("Categories");

        mProductNameET = findViewById(R.id.ProductNameET);
        mProductDescET = findViewById(R.id.ProductDescET);
        mProductPriceET = findViewById(R.id.ProductPriceET);
        mCategoriesSPIN = findViewById(R.id.CategoriesSPIN);
        mAddProductBTN = findViewById(R.id.AddProductBTN);

        spinnerList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(AddProductsActivity.this,
                android.R.layout.simple_spinner_dropdown_item,spinnerList);
        mCategoriesSPIN.setAdapter(arrayAdapter);
        getCategories();

        Toolbar admintool = findViewById(R.id.AdminToolbar);
        setSupportActionBar(admintool);

        mAddProductBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStrings();

                if (mProductNameETSTR.isEmpty()){
                    Snackbar snackbar = Snackbar.make(mAddProductBTN, "Please Enter Name", Snackbar.LENGTH_LONG);
                    snackbar.setAction("Action", null).show();
                }
                else if (mProductDescETSTR.isEmpty()){
                    Snackbar snackbar = Snackbar.make(mAddProductBTN, "Please Enter Product Description", Snackbar.LENGTH_LONG);
                    snackbar.setAction("Action", null).show();
                }
                else if (mProductPriceETSTR.isEmpty()){
                    Snackbar snackbar = Snackbar.make(mAddProductBTN, "Please Enter Price", Snackbar.LENGTH_LONG);
                    snackbar.setAction("Action", null).show();
                }
            }
        });
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




    public void getStrings(){
        mProductNameETSTR = mProductNameET.getText().toString();
        mProductDescETSTR = mProductDescET.getText().toString();
        mProductPriceETSTR = mProductPriceET.getText().toString();
    }



    public void getCategories(){
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item:dataSnapshot.getChildren()){
                    spinnerList.add(item.child("catname").getValue().toString());
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
