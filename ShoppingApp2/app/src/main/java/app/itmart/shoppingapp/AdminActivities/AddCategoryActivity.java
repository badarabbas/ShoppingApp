package app.itmart.shoppingapp.AdminActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import app.itmart.shoppingapp.AdminActivities.AdminModels.CategoriesModel;
import app.itmart.shoppingapp.CommonActivities.LoginActivity;
import app.itmart.shoppingapp.R;

import static android.view.View.VISIBLE;

public class AddCategoryActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText mAddCatET;
    Button mAddCatBTN,mSelectImageBTN;
    String mAddCatETSTR,mCatImageUrl;

    ImageView mSelectedImageIV;
    DatabaseReference mRef;
    StorageReference mStorageRef;
    StorageReference folderStorageRef;
    Uri SelectedImageUri;
    private static final int RC_PHOTO_PICKER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("Categories");
        mStorageRef = FirebaseStorage.getInstance().getReference("CategoriesPics");


        mAddCatBTN = findViewById(R.id.AddCatBTN);
        mAddCatET = findViewById(R.id.AddCatET);
        mSelectImageBTN = findViewById(R.id.SelectImageBTN);
        mSelectedImageIV = findViewById(R.id.SelectedImageIV);

        Toolbar admintool = findViewById(R.id.AdminToolbar);
        setSupportActionBar(admintool);

        mSelectImageBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             getCategoryPicture();
            }
        });



        mAddCatBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               mAddCatETSTR = mAddCatET.getText().toString();
               if (mAddCatETSTR.isEmpty()){
                   Snackbar snackbar = Snackbar.make(mAddCatBTN, "Please Enter Category Name", Snackbar.LENGTH_LONG);
                   snackbar.setAction("Action", null).show();
               }
               else{

                   folderStorageRef = mStorageRef.child(SelectedImageUri.getLastPathSegment());
                   folderStorageRef.putFile(SelectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                           folderStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                               @Override
                               public void onSuccess(Uri uri) {
                                   mCatImageUrl = uri.toString();
                                   Toast.makeText(AddCategoryActivity.this, ""+mCatImageUrl, Toast.LENGTH_SHORT).show();
                                   CategoriesModel model = new CategoriesModel(mAddCatETSTR,mCatImageUrl);
                                   mRef.push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                       @Override
                                       public void onSuccess(Void aVoid) {
                                           Snackbar snackbar = Snackbar.make(mAddCatBTN, "Category Added", Snackbar.LENGTH_LONG);
                                           snackbar.setAction("Action", null).show();
                                           finish();
                                           startActivity(new Intent(getBaseContext(),ManageCategoriesActivity.class));
                                       }
                                   }).addOnFailureListener(new OnFailureListener() {
                                       @Override
                                       public void onFailure(@NonNull Exception e) {
                                           Toast.makeText(AddCategoryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                       }
                                   });



                               }
                           }).addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {
                                   Toast.makeText(AddCategoryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                               }
                           });

                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(AddCategoryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   });




               }//Else Closing
            }//onclick method closing
        });//onclick listener closing


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

    public void getCategoryPicture() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            SelectedImageUri = data.getData();
            Toast.makeText(this, ""+SelectedImageUri, Toast.LENGTH_SHORT).show();
            mSelectedImageIV.setImageURI(SelectedImageUri);
            mSelectedImageIV.setVisibility(VISIBLE);


    }


}

