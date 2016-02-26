package im.mongo.core.storage;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import im.mongo.MongoIM;

/**
 * Created by zhonganyun on 15/8/4.
 */
public abstract class StorageService extends SQLiteOpenHelper {


    private final static int version = 1;

    protected final static String db = "storage";


    public StorageService() {
        super(MongoIM.sharedInstance().getContext(), db, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MessageStorageService.TABLE.CREATE_SQL);
        db.execSQL(ConversationStorageService.TABLE.CREATE_SQL);
    }
}
