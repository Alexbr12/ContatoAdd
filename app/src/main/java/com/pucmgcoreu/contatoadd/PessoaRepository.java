package com.pucmgcoreu.contatoadd;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class PessoaRepository {
    private PessoaDAO mRequestDao;
    private LiveData<List<Pessoa>> allRequest;

    public PessoaRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mRequestDao = db.pessoaDAO();
        allRequest = mRequestDao.getAllRequest();
    }

    LiveData<List<Pessoa>> getAllRequest() {
        return allRequest;
    }

    public List<Pessoa> getAll(){
        //new selectAsyncTask(mRequestDao).execute();
        return mRequestDao.getAll();
    }

    public void insert (Pessoa request) {
        new insertAsyncTask(mRequestDao).execute(request);
    }

    public void delete (Pessoa request) {
        new deleteAsyncTask(mRequestDao).execute(request);
    }

    private static class insertAsyncTask extends AsyncTask<Pessoa, Void, Void> {

        private PessoaDAO mAsyncTaskDao;

        insertAsyncTask(PessoaDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Pessoa... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Pessoa, Void, Void> {

        private PessoaDAO mAsyncTaskDao;

        deleteAsyncTask(PessoaDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Pessoa... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class selectAsyncTask extends AsyncTask<Pessoa, Void, Void> {

        private PessoaDAO mAsyncTaskDao;

        selectAsyncTask(PessoaDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Pessoa... params) {
            mAsyncTaskDao.getAll();
            return null;
        }
    }
}
