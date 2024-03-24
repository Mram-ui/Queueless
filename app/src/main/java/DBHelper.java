
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

    public class DBHelper extends SQLiteOpenHelper {

        public static final String DBNAME = "RestaurantBooking.db";

        // User table
        public static final String USER_TABLE = "users";
        public static final String COL_USER_ID = "user_id";
        public static final String COL_USERNAME = "username";
        public static final String COL_PASSWORD = "password";
        public static final String COL_EMAIL = "email";
        public static final String COL_PHONE_NUMBER = "phone_number";

        // Restaurant table
        public static final String RESTAURANT_TABLE = "restaurants";
        public static final String COL_RESTAURANT_ID = "restaurant_id";
        public static final String COL_RESTAURANT_NAME = "restaurant_name";
        public static final String COL_LOCATION = "location";
        public static final String COL_CUISINE_TYPE = "cuisine_type";
        public static final String COL_OPENING_HOURS = "opening_hours";
        public static final String COL_RESTAURANT_PHONE_NUMBER = "restaurant_phone_number";
        public static final String COL_CAPACITY = "capacity";
        public static final String COL_RATING = "rating"; // Added column for rating

        // Reservation table
        public static final String RESERVATION_TABLE = "reservations";
        public static final String COL_RESERVATION_ID = "reservation_id";
        public static final String COL_RESERVATION_USER_ID = "user_id";
        public static final String COL_RESERVATION_RESTAURANT_ID = "restaurant_id";
        public static final String COL_RESERVATION_DATE_TIME = "reservation_date_time";
        public static final String COL_NUMBER_OF_GUEST = "number_of_guest";
        public static final String COL_RESERVATION_STATUS = "status";

        // Transportation service table
        public static final String TRANSPORTATION_TABLE = "transportation_services";
        public static final String COL_TRANSPORTATION_ID = "service_id";
        public static final String COL_CAR_TYPE = "car_type";
        public static final String COL_PICKUP_LOCATION = "pickup_location";
        public static final String COL_SERVICE_DATE_TIME = "service_date_time";
        public static final String COL_RESERVATION_ID_TRANSPORT = "reservation_id";

        public DBHelper(Context context) {
            super(context, DBNAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            // Create user table
            String createUserTableQuery = "CREATE TABLE " + USER_TABLE + "("
                    + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_USERNAME + " TEXT, "
                    + COL_PASSWORD + " TEXT, "
                    + COL_EMAIL + " TEXT, "
                    + COL_PHONE_NUMBER + " TEXT)";
            sqLiteDatabase.execSQL(createUserTableQuery);

            // Create restaurant table
            String createRestaurantTableQuery = "CREATE TABLE " + RESTAURANT_TABLE + "("
                    + COL_RESTAURANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_RESTAURANT_NAME + " TEXT, "
                    + COL_LOCATION + " TEXT, "
                    + COL_CUISINE_TYPE + " TEXT, "
                    + COL_OPENING_HOURS + " TEXT, "
                    + COL_RESTAURANT_PHONE_NUMBER + " TEXT, "
                    + COL_CAPACITY + " INTEGER, "
                    + COL_RATING + " REAL)"; // Added column for rating
            sqLiteDatabase.execSQL(createRestaurantTableQuery);

            // Create reservation table
            String createReservationTableQuery = "CREATE TABLE " + RESERVATION_TABLE + "("
                    + COL_RESERVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_RESERVATION_USER_ID + " INTEGER, "
                    + COL_RESERVATION_RESTAURANT_ID + " INTEGER, "
                    + COL_RESERVATION_DATE_TIME + " TEXT, "
                    + COL_NUMBER_OF_GUEST + " INTEGER, "
                    + COL_RESERVATION_STATUS + " TEXT, "
                    + "FOREIGN KEY(" + COL_RESERVATION_USER_ID + ") REFERENCES " + USER_TABLE + "(" + COL_USER_ID + "), "
                    + "FOREIGN KEY(" + COL_RESERVATION_RESTAURANT_ID + ") REFERENCES " + RESTAURANT_TABLE + "(" + COL_RESTAURANT_ID + "))";
            sqLiteDatabase.execSQL(createReservationTableQuery);

            // Create transportation service table
            String createTransportationTableQuery = "CREATE TABLE " + TRANSPORTATION_TABLE + "("
                    + COL_TRANSPORTATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_CAR_TYPE + " TEXT, "
                    + COL_PICKUP_LOCATION + " TEXT, "
                    + COL_SERVICE_DATE_TIME + " TEXT, "
                    + COL_RESERVATION_ID_TRANSPORT + " INTEGER, "
                    + "FOREIGN KEY(" + COL_RESERVATION_ID_TRANSPORT + ") REFERENCES " + RESERVATION_TABLE + "(" + COL_RESERVATION_ID + "))";
            sqLiteDatabase.execSQL(createTransportationTableQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            // Drop older tables if existed
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RESTAURANT_TABLE);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RESERVATION_TABLE);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TRANSPORTATION_TABLE);

            // Create tables again
            onCreate(sqLiteDatabase);
        }
    }


