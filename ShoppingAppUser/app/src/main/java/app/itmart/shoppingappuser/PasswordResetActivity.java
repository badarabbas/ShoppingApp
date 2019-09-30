package app.itmart.shoppingappuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordResetActivity extends AppCompatActivity {

    EditText mEmailET;
    Button mResetPasswordBTN,mCreateBTN;
    FirebaseAuth mAuth;
    String mEmailETSTR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        mEmailET = findViewById(R.id.EmailET);
        mResetPasswordBTN = findViewById(R.id.ResetPasswordBTN);
        mCreateBTN = findViewById(R.id.CreateBTN);
        mAuth = FirebaseAuth.getInstance();


        mResetPasswordBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEmailETSTR = mEmailET.getText().toString();
                if (mEmailETSTR.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(mResetPasswordBTN, "Please Enter Email", Snackbar.LENGTH_LONG);
                    snackbar.setAction("Action", null).show();
                } else {

                    mAuth.sendPasswordResetEmail(mEmailETSTR)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(PasswordResetActivity.this, "Password Reset Email sent", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getBaseContext(), LoginActivity.class));
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PasswordResetActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        });

        mCreateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),SignUpActivity.class));
            }
        });
    }
}
