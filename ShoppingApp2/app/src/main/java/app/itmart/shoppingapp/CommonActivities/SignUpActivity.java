package app.itmart.shoppingapp.CommonActivities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.victor.loading.rotate.RotateLoading;

import app.itmart.shoppingapp.Models.UserModel;
import app.itmart.shoppingapp.R;


public class SignUpActivity extends AppCompatActivity {
    EditText mNameET,mEmailET,mPasswordET,mPhoneET;
    Button mSignUpBTN;
    String mNameETstr,mEmailETstr,mPasswordETstr,mPhoneETstr,mGenderRadiostr;
    FirebaseAuth mAuth;
    DatabaseReference mRef;
    RotateLoading mVictorProgress;
    RadioGroup mGenderRadio;
    RadioButton selectedgender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mNameET = findViewById(R.id.NameET);
        mEmailET = findViewById(R.id.EmailET);
        mPasswordET = findViewById(R.id.PasswordET);
        mPhoneET = findViewById(R.id.PhoneET);
        mGenderRadio = findViewById(R.id.GenderRadio);
        mSignUpBTN = findViewById(R.id.SignUpBTN);
        mAuth = FirebaseAuth.getInstance();
        mRef= FirebaseDatabase.getInstance().getReference("Users");

        mVictorProgress = findViewById(R.id.VictorProgress);
        mGenderRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id=mGenderRadio.getCheckedRadioButtonId();
                selectedgender=findViewById(id);
                mGenderRadiostr = selectedgender.getText().toString();
            }
        });


        mSignUpBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStrings();
                if (mNameETstr.isEmpty()){

                    Snackbar snackbar = Snackbar.make(mSignUpBTN, "Please Enter Name", Snackbar.LENGTH_LONG);
                    snackbar.setAction("Action", null).show();
                }
                else if (mEmailETstr.isEmpty()){
                    Snackbar snackbar = Snackbar.make(mSignUpBTN, "Please Enter Email", Snackbar.LENGTH_LONG);
                    snackbar.setAction("Action", null).show();
                }
                else if (mPasswordETstr.isEmpty()){
                    Snackbar snackbar = Snackbar.make(mSignUpBTN, "Please Enter Password", Snackbar.LENGTH_LONG);
                    snackbar.setAction("Action", null).show();
                }
                else if (mPhoneETstr.isEmpty()){
                    Snackbar snackbar = Snackbar.make(mSignUpBTN, "Please Enter Phone Number", Snackbar.LENGTH_LONG);
                    snackbar.setAction("Action", null).show();
                }
                else if (mPhoneETstr.length()<10 || mPhoneETstr.length()>13){
                    Snackbar snackbar = Snackbar.make(mSignUpBTN, "Please Enter Valid Number", Snackbar.LENGTH_LONG);
                    snackbar.setAction("Action", null).show();
                }
                else if (mGenderRadiostr.isEmpty()){
                    Snackbar snackbar = Snackbar.make(mSignUpBTN, "Please Select Gender", Snackbar.LENGTH_LONG);
                    snackbar.setAction("Action", null).show();
                }
                else {
                    mVictorProgress.setVisibility(View.VISIBLE);
                    mVictorProgress.start();
                    mSignUpBTN.setClickable(false);
                    mAuth.createUserWithEmailAndPassword(mEmailETstr,mPasswordETstr)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    mVictorProgress.setVisibility(View.GONE);
                                    mVictorProgress.stop();
                                    mSignUpBTN.setClickable(true);
                                    if (task.isSuccessful()) {
                                        String Userid = mAuth.getCurrentUser().getUid();
                                        UserModel model=new UserModel(mNameETstr,mPhoneETstr,mGenderRadiostr,"Admin");
                                        mRef.child(Userid).setValue(model);
                                        Snackbar snackbar = Snackbar.make(mSignUpBTN,"User Added",Snackbar.LENGTH_LONG);
                                        snackbar.setAction("Action", null).show();
                                        finish();
                                        startActivity(new Intent(getBaseContext(),LoginActivity.class));
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }//Else
            }//onclick method
        });//ONclick Listener

    }

    public void getStrings(){
        mNameETstr = mNameET.getText().toString();
        mEmailETstr = mEmailET.getText().toString();
        mPasswordETstr = mPasswordET.getText().toString();
        mPhoneETstr = mPhoneET.getText().toString();


    }
}
