package com.pucmgcoreu.contatoadd;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PessoaDAO {
    @Query("SELECT * FROM pessoa")
    List<Pessoa> getAll();

    @Query("SELECT * from pessoa ORDER BY nome ASC")
    LiveData<List<Pessoa> > getAllRequest();

    @Query("SELECT * FROM pessoa WHERE nome LIKE :name LIMIT 1")
    Pessoa findByName(String name);

    @Insert
    void insertAll(Pessoa... pessoas);

    @Insert
    void insert(Pessoa pessoa);

    @Delete
    void delete(Pessoa pessoa);

    @Query("DELETE FROM pessoa")
    void deleteAll();
}