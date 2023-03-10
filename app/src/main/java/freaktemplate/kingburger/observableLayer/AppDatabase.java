package freaktemplate.kingburger.observableLayer;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Cart.class,CartItemTopping.class,CartFav.class,Order.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "cartDb").allowMainThreadQueries()
                      .build();
        }
        return INSTANCE;


    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract CartDao cartDao();
}
