package com.pucmgcoreu.contatoadd;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class PessoaViewModel extends AndroidViewModel {
    private PessoaRepository mRepository;
    private LiveData<List<Pessoa>> mAllRequests;

    public PessoaViewModel(Application application) {
        super(application);
        mRepository = new PessoaRepository(application);
        mAllRequests = mRepository.getAllRequest();
    }

    LiveData<List<Pessoa>> getmAllRequests() {
        return mAllRequests;
    }

    List<Pessoa> getAll(){
        return mRepository.getAll();
    }

    public void insert(Pessoa request) {
        mRepository.insert(request);
    }

    public void delete(Pessoa request) {
        mRepository.delete(request);
    }
}