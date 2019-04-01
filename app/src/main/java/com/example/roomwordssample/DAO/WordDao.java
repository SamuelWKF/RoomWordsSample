package com.example.roomwordssample.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.roomwordssample.Models.Word;

import java.util.List;

@Dao
public interface WordDao {

    // Insert a new word record
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    // Delete a single word record
    @Delete
    void deleteWord(Word word);

    // Delete all word records
    @Query("DELETE FROM word_table")
    void deleteAll();

    @Query("SELECT * from word_table LIMIT 1")
    Word[] getAnyWord();

    // Get all word records with live update
    @Query("SELECT * from word_table ORDER BY word ASC")
    LiveData<List<Word>> getAllWords();
}
