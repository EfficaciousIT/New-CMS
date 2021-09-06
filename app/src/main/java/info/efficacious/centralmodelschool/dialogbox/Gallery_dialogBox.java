package info.efficacious.centralmodelschool.dialogbox;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import info.efficacious.centralmodelschool.MultiImages.activities.MainImages;
import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.common.ConnectionDetector;

public class Gallery_dialogBox extends AppCompatActivity {
    EditText EventDescriptin_et;
    Button btnsave, btnCancel;
    public static String EventDescriptin;
    ConnectionDetector cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.gallery_dialogbox);
        cd = new ConnectionDetector(getApplicationContext());
        EventDescriptin_et = (EditText) findViewById(R.id.editText288);
        btnsave = (Button) findViewById(R.id.btnsave);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    finish();
                } catch (Exception ex) {

                }
            }
        });
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EventDescriptin=EventDescriptin_et.getText().toString();
                    if (!EventDescriptin_et.getText().toString().contentEquals("")) {
                        ActivityCompat.requestPermissions(Gallery_dialogBox.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
                        ActivityCompat.requestPermissions(Gallery_dialogBox.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
                        if (!cd.isConnectingToInternet()) {

                            AlertDialog.Builder alert = new AlertDialog.Builder(Gallery_dialogBox.this);
                            alert.setMessage("No InternetConnection");
                            alert.setPositiveButton("OK", null);
                            alert.show();

                        } else {
                            Intent intent = new Intent(Gallery_dialogBox.this, MainImages.class);
                            intent.putExtra("EventDescriptin",EventDescriptin);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        if (TextUtils.isEmpty(EventDescriptin_et.getText().toString())) {
                            EventDescriptin_et.setError("Enter Event Description");
                        }
                    }
                } catch (Exception ex) {

                }

            }
        });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}

