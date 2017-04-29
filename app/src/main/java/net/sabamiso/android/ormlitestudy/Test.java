package net.sabamiso.android.ormlitestudy;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "test")
public class Test {
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";

    @DatabaseField(columnName = COLUMN_ID, generatedId = true, allowGeneratedIdInsert = true)
    private int id;

    @DatabaseField(columnName = COLUMN_NAME, canBeNull = false)
    private String name;

    @DatabaseField(columnName = COLUMN_EMAIL, canBeNull = true)
    private String email;

    public Test() {
    }

    public Test(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String toString() {
        return String.format("{id:%d, %s, %s}", id, name, email);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}