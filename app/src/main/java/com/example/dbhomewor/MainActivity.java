package com.example.dbhomewor;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        addShoppingList("New List", System.currentTimeMillis(), "Test description");

        getAllShoppingLists();
    }

    // Метод для добавления нового списка покупок
    private void addShoppingList(String name, long date, String description) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("date", date);
        values.put("description", description);

        Uri newUri = getContentResolver().insert(Uri.parse("content://com.example.shoppinglist/lists"), values);
        if (newUri != null) {
            Toast.makeText(this, "Список добавлен!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Ошибка добавления списка", Toast.LENGTH_SHORT).show();
        }
    }

    // Метод для получения всех списков покупок и отображения их в TextView
    private void getAllShoppingLists() {
        Cursor cursor = getContentResolver().query(
                Uri.parse("content://com.example.shoppinglist/lists"),
                null, null, null, null);

        if (cursor != null) {
            StringBuilder result = new StringBuilder();
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                long date = cursor.getLong(cursor.getColumnIndexOrThrow("date"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));

                result.append("Name: ").append(name).append("\n");
                result.append("Date: ").append(date).append("\n");
                result.append("Description: ").append(description).append("\n\n");
            }
            cursor.close();

            textView.setText(result.toString());
        } else {
            textView.setText("Нет данных для отображения.");
        }
    }
}