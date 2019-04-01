package com.example.roomwordssample.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.roomwordssample.DAO.WordDao;
import com.example.roomwordssample.Database.WordRoomDatabase;
import com.example.roomwordssample.Models.Word;

import java.util.List;

public class WordRepository {
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    // Constructor
    public WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);

        // Set word DAO and get all words
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
    }

    // Get all words data (Live update)
    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    // Insert data
    public void insert (Word word) {
        new InsertAsyncTask(mWordDao).execute(word);
    }

    // Delete all words
    public void deleteAll()  {
        new DeleteAllWordsAsyncTask(mWordDao).execute();
    }

    // Delete a single word record
    public void deleteWord(Word word)  {
        new DeleteWordAsyncTask(mWordDao).execute(word);
    }

    /**
     * INNER CLASSES for AsyncTasks
     * - InsertAsyncTask
     * - DeleteAllWordsAsyncTask
     * - DeleteWordAsyncTask
     */
    // Inner class that performs insert a word Async task
    private static class InsertAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao mAsyncTaskDao;

        // Constructor
        InsertAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    // Inner class that performs delete all words Async task
    private static class DeleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void> {
        private WordDao mAsyncTaskDao;

        DeleteAllWordsAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    // Inner class that performs delete a single word Async task
    private static class DeleteWordAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao mAsyncTaskDao;

        DeleteWordAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.deleteWord(params[0]);
            return null;
        }
    }
}
