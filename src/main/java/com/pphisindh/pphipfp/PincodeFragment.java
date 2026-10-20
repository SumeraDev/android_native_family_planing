package com.pphisindh.pphipfp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class PincodeFragment extends Fragment implements View.OnClickListener{

    EditText etPincode;
    Button btnLogin;
    Button btnCancel;
    TextView tvMsg;
    Logindb logindb;
    public PincodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pincode, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        try{
            etPincode=(EditText)getActivity().findViewById(R.id.etPincode);
            btnLogin=(Button)getActivity().findViewById(R.id.btnLogin);
            btnCancel=(Button)getActivity().findViewById(R.id.btnCancel);
            tvMsg=(TextView) getActivity().findViewById(R.id.tvMsg);
            btnLogin.setOnClickListener(this);
            btnCancel.setOnClickListener(this);
            logindb=new Logindb(getActivity());

        }catch (Exception ex){

        }
    }
    @Override
    public void onClick(View v) {
        try{
            switch (v.getId()){
                case R.id.btnLogin:{
                    if(logindb.veryfyPincode(etPincode.getText()+"")=="isVerified"){
                        Intent i = new Intent(getActivity(), MainActivity.class);
                        startActivity(i);
                        getActivity().finish();
                    }else{
                        tvMsg.setText("Invalid Pincode.....!");
                    }
                    break;
                }
                case R.id.btnCancel:{
                    getActivity().finish();
                    break;
                }
            }
        }catch (Exception ex){
            tvMsg.setText("Error in Login Fragment"+ex.getMessage());
        }



    }
}
