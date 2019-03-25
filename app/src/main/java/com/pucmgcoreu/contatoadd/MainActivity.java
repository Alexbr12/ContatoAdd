package com.pucmgcoreu.contatoadd;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;

import java.util.List;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public static final int NEW_ACTIVITY_REQUEST_CODE = 1;
    private PessoaViewModel viewModel;
    private RecyclerView recyclerView;
    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);


        setTitle("Lista de contatos");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContatoActivity.class);
                startActivityForResult(intent, NEW_ACTIVITY_REQUEST_CODE);
            }
        });
        viewModel = ViewModelProviders.of(this).get(PessoaViewModel.class);

       // List<Pessoa> lista = AppDatabase.getDatabase(this).pessoaDAO().getAll();

        Task task = new Task(this);
        task.execute();

        recyclerView = findViewById(R.id.recyclerview);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_teste);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Gson gson = new Gson();
            String strObj = data.getStringExtra(ContatoActivity.EXTRA_REPLY);
            Pessoa obj = gson.fromJson(strObj, Pessoa.class);

            viewModel.insert(obj);
        }else {
//            Toast.makeText( ###Resolver depois#########
//                    getApplicationContext(),
//                    "Não salvo",
//                    Toast.LENGTH_SHORT).show();
        }

 //       MainActivity callbackManager = new MainActivity();
 //       callbackManager.onActivityResult(requestCode, resultCode, data); //###Resolver depois#########
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifes t.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.login) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_teste);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    

    class Task extends AsyncTask<Pessoa, Void, List<Pessoa>> {
        private Context context;

        Task(Context context){
            this.context = context;
        }

        @Override
        protected List<Pessoa> doInBackground(final Pessoa... params) {
            return AppDatabase.getDatabase(context).pessoaDAO().getAll();
        }

        protected void onPostExecute(List<Pessoa> list){
            final PessoaAdapter adapter = new PessoaAdapter(context, viewModel, list);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            viewModel.getmAllRequests().observe((LifecycleOwner) context, new Observer<List<Pessoa>>() {
                @Override
                public void onChanged(@Nullable final List<Pessoa> words) {
                    adapter.setWords(words);
                }
            });
        }
    }
}
