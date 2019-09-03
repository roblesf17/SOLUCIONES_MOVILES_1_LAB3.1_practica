package com.example.lectorbarras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    public static String texto_resultado__temporal = "" ;

    public TextView txt_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        configurarLector();
    }

    private void configurarLector()
    {
        final ImageButton imageButton = (ImageButton)findViewById(R.id.img_button);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new IntentIntegrator(MainActivity.this).initiateScan();
            }
        });

    }

    private void actualiarUITextViews(String resultadoScaneo, String formatoResultado)
    {
        //forma directa de agregar texto al txt
        ((TextView)findViewById(R.id.tvFormat)).setText(formatoResultado);
        ((TextView)findViewById(R.id.tvresult)).setText(resultadoScaneo);

    }



    private void manipularResultado(IntentResult intentResult){

        if(intentResult != null){
            actualiarUITextViews(intentResult.getContents(),intentResult.getFormatName());
        }else{
            Toast.makeText(getApplicationContext(),"No se leyó nada",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    //request identifica la peticicion
    //resultado identifica si la peticion fue relaizado de forma correcta
    //intencion alamcena el resultado propio de la actividad
    public void onActivityResult(int requestCode, int resultCode, Intent
            intent){
        final IntentResult intentResult =
                IntentIntegrator.parseActivityResult(requestCode,resultCode,intent);
        manipularResultado(intentResult);
    }

    public void onClicCopiar(View v)
    {
        int evento_temporal = 0 ;

        txt_1 = (TextView) findViewById(R.id.tvresult);
        texto_resultado__temporal = txt_1.getText().toString();


        if (!(texto_resultado__temporal.equals("")))
        {

            try
            {
                String string = texto_resultado__temporal;
                String[] parts = string.split("://");
                String part1 = parts[0]; // 123
                String part2 = parts[1]; // 654321

                evento_temporal = 1 ;

                Context context = getApplicationContext();
                String ejecutando_comando = texto_resultado__temporal;
                CharSequence text = ejecutando_comando ;
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context , text , duration);
                toast.show();

                Uri uri = Uri.parse(""+texto_resultado__temporal+"");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }

            catch (Exception e)
            {
                Context context = getApplicationContext();
                String ejecutando_comando = "No es url , copiar texto";
                CharSequence text = ejecutando_comando ;
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context , text , duration);
                toast.show();


                ClipboardManager myClipboard = myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData myClip;

                String texto_clip = texto_resultado__temporal;
                myClip = ClipData.newPlainText("text", texto_clip);
                myClipboard.setPrimaryClip(myClip);

            }


        }


    }


}
