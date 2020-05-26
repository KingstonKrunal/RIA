package com.krunal_shah.relationshipinformationapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.krunal_shah.relationshipinformationapp.R;

public class FragmentOne extends android.app.Fragment {
    EditText inputEmail, inputPassword, inputUsername, inputMobileNo, inputFullname;
    Button Register;
    String Email, Password, userName, fullname, mobileNo;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    Boolean EditTextStatus, checkPassword;
    ImageButton showPassword;
    FirebaseUser currentUser;
    boolean forEmail, forPass, forUsername, forFullname, forMobileNo;
    Context context;

    public FragmentOne() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        inputEmail = (EditText) view.findViewById(R.id.register_email);
        inputPassword = (EditText) view.findViewById(R.id.register_password);
        inputUsername = (EditText) view.findViewById(R.id.register_username);
        Register = (Button) view.findViewById(R.id.btnRegister);
        showPassword = (ImageButton) view.findViewById(R.id.showPassword);
        inputFullname = view.findViewById(R.id.register_fullname);
        inputMobileNo = view.findViewById(R.id.register_mobileNo);

        context = getActivity();

        Register.setEnabled(false);
        Register.getBackground().setAlpha(128);

        inputUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String txtUsername = inputUsername.getText().toString();

                if (txtUsername.isEmpty()) {
                    forUsername = false;
                    Register.setEnabled(false);
                    Register.getBackground().setAlpha(128);
                } else {
                    forUsername = true;
                }

                if (forEmail && forPass && forUsername && forFullname && forMobileNo) {
                    Register.setEnabled(true);
                    Register.getBackground().setAlpha(255);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        inputMobileNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String txtMobileNo = inputMobileNo.getText().toString();
                if (txtMobileNo.isEmpty()) {
                    forMobileNo = false;
                    Register.setEnabled(false);
                    Register.getBackground().setAlpha(128);
                } else {
                    forMobileNo = true;
                }

                if (forEmail && forPass && forUsername && forFullname && forMobileNo) {
                    Register.setEnabled(true);
                    Register.getBackground().setAlpha(255);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        inputFullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String txtFullname = inputFullname.getText().toString();
                if (txtFullname.isEmpty()) {
                    forFullname = false;
                    Register.setEnabled(false);
                    Register.getBackground().setAlpha(128);
                } else {
                    forFullname = true;
                }

                if (forEmail && forPass && forUsername && forFullname && forMobileNo) {
                    Register.setEnabled(true);
                    Register.getBackground().setAlpha(255);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String txtEmail = inputEmail.getText().toString();

                if (txtEmail.isEmpty()) {
                    forEmail = false;
                    Register.setEnabled(false);
                    Register.getBackground().setAlpha(128);
                } else {
                    forEmail = true;
                }

                if (forEmail && forPass && forUsername && forFullname && forMobileNo) {
                    Register.setEnabled(true);
                    Register.getBackground().setAlpha(255);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        inputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String txtPass = inputPassword.getText().toString();

                if (txtPass.isEmpty()) {
                    forPass = false;
                    Register.setEnabled(false);
                    Register.getBackground().setAlpha(128);
                } else {
                    forPass = true;
                }

                if (forEmail && forPass && forUsername && forFullname && forMobileNo) {
                    Register.setEnabled(true);
                    Register.getBackground().setAlpha(255);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        //get Firebase auth Instance
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(context);

        //////////////// Show Password(Visibility) /////////////////////
        showPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        showPassword.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                        inputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        return true;

                    case MotionEvent.ACTION_UP:
                        inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        showPassword.setImageResource(R.drawable.ic_visibility_black_24dp);
                        return true;
                }
                return false;
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //calling method to check EditText is empty or not
                isValid();

                //if EditText is true
                if (EditTextStatus) {
                    if (checkPassword) {
                        UserRegistrationFunction();
                    }
                } else {
                    Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    public void UserRegistrationFunction() {
        //showing progress dialogue at user registration time
        progressDialog.setMessage("Please Wait, We are Registering your data on Server");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    currentUser = firebaseAuth.getCurrentUser();

                    /////////// start Controller Activity //////////////////////////
                    FragmentTwo f2 = new FragmentTwo();
                    Bundle bundle = new Bundle();
                    bundle.putString("Email", Email);
                    bundle.putString("Username", userName);
                    bundle.putString("Fullname", fullname);
                    bundle.putString("MobileNo", mobileNo);
                    f2.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, f2).commit();
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        Toast.makeText(context, "Weak Password", Toast.LENGTH_LONG).show();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        Toast.makeText(context, "Invalid Data", Toast.LENGTH_LONG).show();
                    } catch (FirebaseAuthUserCollisionException e) {
                        Toast.makeText(context, "User exist", Toast.LENGTH_LONG).show();
                    } catch (FirebaseNetworkException e) {
                        Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {

                    }
                }
                progressDialog.dismiss();
            }
        });
    }

    public void isValid() {
        Email = inputEmail.getText().toString().trim();
        Password = inputPassword.getText().toString().trim();
        userName = inputUsername.getText().toString().trim();
        fullname = inputFullname.getText().toString().trim();
        mobileNo = inputMobileNo.getText().toString().trim();

        if (TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password) || TextUtils.isEmpty(userName)
                || TextUtils.isEmpty(fullname) || TextUtils.isEmpty(mobileNo)) {
            EditTextStatus = false;
        } else {
            EditTextStatus = true;
        }

        if (!TextUtils.isEmpty(Password)) {
            if (Password.length() <= 7) {
                checkPassword = false;
                Toast.makeText(context, "Password too short, enter minimum 8 character", Toast.LENGTH_LONG).show();
            } else {
                checkPassword = true;
            }
        }
    }
}