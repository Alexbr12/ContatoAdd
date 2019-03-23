package com.pucmgcoreu.contatoadd;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.gson.Gson;

import java.net.URLEncoder;

public class ContatoActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "REPLY";

    public static final int RESULT = 10;
    private TextInputEditText nome;
    private TextInputEditText telefoneFixo;
    private TextInputEditText telefoneCelular;
    private TextInputEditText email;

    private  Intent replyIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        setTitle("Adicionar novo contato");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nome = findViewById(R.id.nome);
        telefoneFixo = findViewById(R.id.telefone_fixo);
        telefoneCelular = findViewById(R.id.telefone_celular);
        email = findViewById(R.id.email);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:{
                super.onBackPressed();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void salvar(View view){
        replyIntent = new Intent();

        try{
            Pessoa p = new Pessoa();
            {
                p.setNome(nome.getText().toString());
                p.setTelefoneCelular(telefoneCelular.getText().toString());
                p.setTelefoneFixo(telefoneFixo.getText().toString());
                p.setEmail(email.getText().toString());
            }

            Intent intent = new Intent(Intent.ACTION_INSERT);
            intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

            intent.putExtra(ContactsContract.Intents.Insert.NAME, p.getNome());
            intent.putExtra(ContactsContract.Intents.Insert.PHONE, p.getTelefoneFixo());
            intent.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE, p.getTelefoneCelular());
            intent.putExtra(ContactsContract.Intents.Insert.EMAIL, p.getEmail());

            sendWhatsApp(p.getTelefoneCelular());

            sendEmail();

            Gson gson = new Gson();
            String data = gson.toJson(p);
            replyIntent.putExtra(EXTRA_REPLY, data);

            this.startActivityForResult(intent, RESULT);


        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }
    }

    protected void sendWhatsApp(String tel) {
        PackageManager packageManager = getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);

        try {
            String url = "https://api.whatsapp.com/send?phone="+ "55"+ tel +"&text=" + URLEncoder.encode("Funcionando", "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        //finish();
    }

    protected void sendEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "teste"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "body");
        //emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text

        startActivity(Intent.createChooser(emailIntent, "Chooser Title"));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT) {
            setResult(RESULT_OK, replyIntent);
           // finish();

        }
    }
}
