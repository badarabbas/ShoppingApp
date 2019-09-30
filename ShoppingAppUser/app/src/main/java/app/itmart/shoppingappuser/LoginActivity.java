package app.itmart.shoppingappuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.victor.loading.rotate.RotateLoading;

public class LoginActivity extends AppCompatActivity {

    Button mLoginBTN,mCreateBTN;
    EditText mEmailET,mPasswordET;
    String mEmailETstr,mPasswordETstr,type;
    TextView mForgotTV;
    FirebaseAuth mAuth;
    DatabaseReference mRef;
    RotateLoading mVictorProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mVictorProgress = findViewById(R.id.VictorProgress);

        mLoginBTN = findViewById(R.id.LoginBTN);
        mCreateBTN = findViewById(R.id.CreateBTN);
        mEmailET = findViewById(R.id.EmailET);
        mPasswordET = findViewById(R.id.PasswordET);
        mForgotTV = findViewById(R.id.ForgotTV);
        mAuth = FirebaseAuth.getInstance();
        mRef= FirebaseDatabase.getInstance().getReference("Users");
        type = "Admin";



        mLoginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStrings();


                if (mEmailETstr.isEmpty()){
                    Snackbar snackbar = Snackbar.make(mLoginBTN, "Please Enter Email", Snackbar.LENGTH_LONG);
                    snackbar.setAction("Action", null).show();
                }
                else if (mPasswordETstr.isEmpty()){
                    Snackbar snackbar = Snackbar.make(mLoginBTN, "Please Enter Password", Snackbar.LENGTH_LONG);
                    snackbar.setAction("Action", null).show();
                }
                else {
                    mVictorProgress.setVisibility(View.VISIBLE);
                    mVictorProgress.start();
                    mLoginBTN.setClickable(false);
                    mAuth.signInWithEmailAndPassword(mEmailETstr,mPasswordETstr)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    mVictorProgress.setVisibility(View.GONE);
                                    mVictorProgress.stop();
                                    mLoginBTN.setClickable(true);
                                    if (task.isSuccessful()) {
                                        //getting user type
                                        String uid = mAuth.getCurrentUser().getUid();
                                        mRef.child(uid).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                type = dataSnapshot.child("type").getValue().toString();
                                                if (type.equalsIgnoreCase("User")) {
                                                    startActivity(new Intent(getBaseContext(), UserDashboardActivity.class));


                                                }
                                                else {
                                                    Snackbar snackbar = Snackbar.make(mLoginBTN, "Please Enter valid Email and Password", Snackbar.LENGTH_LONG);
                                                    snackbar.setAction("Action", null).show();
                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });




                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }//Else
            }//onclick method
        });//Onclick listener
        mCreateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getBaseContext(),SignUpActivity.class));
            }
        });
        mForgotTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),PasswordResetActivity.class));
            }
        });
    }

    public void getStrings(){

        mEmailETstr = mEmailET.getText().toString();
        mPasswordETstr = mPasswordET.getText().toString();


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth=FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser()!=null){
            if (type.equalsIgnoreCase("User")) {
                startActivity(new Intent(getBaseContext(), UserDashboardActivity.class));


            }

        }

    }
}
