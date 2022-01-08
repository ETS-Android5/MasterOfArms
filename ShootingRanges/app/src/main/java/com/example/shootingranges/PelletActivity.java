package com.example.shootingranges;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import com.example.shootingranges.Adapter.PelletAdapter;

import java.lang.reflect.Field;

public class PelletActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button buttonIncrease,buttonDecrease;
    int minteger = 1;

    TextView qty;
    Spinner spin;
    int index;
    String[] pellets={"Select","R10","RWS","Superpoint","Diabolo Baisc","Hyper Max","High Split","Hollow Point"};

    Button closeBtn;
    PopupWindow popup;
    RelativeLayout rl;

    String email,phone,quantity;
    String brand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pellet);
        setTitle("Pellet Booking Screen");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

        Intent intent=getIntent();
        email=intent.getStringExtra("Email");
        phone=intent.getStringExtra("Phone");

        qty=(TextView)findViewById(R.id.txtVwQty);
        spin =(Spinner)findViewById(R.id.spinner);




        /*try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spin);

            // Set popupWindow height to 500px
            popupWindow.setHeight(450);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }*/

        spin.setOnItemSelectedListener(this);


        PelletAdapter pelletAdapter =new PelletAdapter(getApplicationContext(),pellets);
        spin.setAdapter(pelletAdapter);

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
      index=i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void increaseInteger(View view) {
        buttonIncrease=(Button)findViewById(R.id.btnIncrease);
        minteger = minteger + 1;

        if(minteger>50)
        {
            buttonIncrease.setEnabled(false);

        }

        else
        {
            display(minteger);
        }
    }

    public void decreaseInteger(View view) {
        buttonDecrease=(Button)findViewById(R.id.btnDecrease);
        minteger = minteger - 1;

        if(minteger<1)
        {
            minteger=1;

        }

        else {
            display(minteger);
        }
    }

    private void display(int number) {
        TextView displayInteger = (TextView) findViewById(R.id.txtVwQty);
        displayInteger.setText("  " + number);
        quantity=displayInteger.getText().toString();
    }

    public void submit(View view)
    {
        brand=pellets[index];
        rl = (RelativeLayout) findViewById(R.id.relative1);
        if(index!=0)
        {
            if(checkPermission(Manifest.permission.SEND_SMS))
            { SmsManager smsManager = SmsManager.getDefault();

                Intent intent = new Intent(PelletActivity.this,MainMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                smsManager.sendTextMessage("9930947174",null,"Order Details\nEmail Id : "+email+"\nBrand : "+brand+"\nQuantity : "+quantity,null,null);
                startActivity(intent);
                finish();
            }

            else
            {
                Toast.makeText(this,"Please allow permissions to send sms",Toast.LENGTH_SHORT).show();
            }
        }
        else

        {
            TextView error=(TextView) findViewById(R.id.txtVwError);
            error.setError("Please select one brand");
        }
    }



    public boolean checkPermission(String permission)
    {
        int check= ContextCompat.checkSelfPermission(this,permission);
        return (check== PackageManager.PERMISSION_GRANTED);

    }


}
