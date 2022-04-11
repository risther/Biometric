package com.example.biometric;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.hardware.biometrics.BiometricManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tvMessage;
    Button btLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMessage=findViewById(R.id.tv_message);
        btLogin=findViewById(R.id.bt_login);

        androidx.biometric.BiometricManager manager = androidx.biometric.BiometricManager.from(MainActivity.this);

        switch (manager.canAuthenticate()){
            case BiometricManager.BIOMETRIC_SUCCESS:

                tvMessage.setText("You can sue biomtetr to login");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:


                tvMessage.setText("This devicen't have e biometri sensor");

                btLogin.setVisibility(View.GONE);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:

                tvMessage.setText("this devcie doen't have fingerprint saved,"+" please cheack you securyti setting");

               btLogin.setVisibility(View.GONE);
               break;
        }


        BiometricPrompt prompt = new BiometricPrompt(this,
                ContextCompat.getMainExecutor(this),
                new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),"loguin successful",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        BiometricPrompt.PromptInfo info = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometic")
                .setNegativeButtonText("cancel")
                .build();

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                prompt.authenticate(info);
            }
        });
    }
}