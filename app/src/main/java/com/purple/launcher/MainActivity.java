package com.purple.launcher;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String[] LAUNCHER_PACKAGES = {
        "git.artdeell.mojo",
        "net.kdt.pojavlaunch"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText nickInput = findViewById(R.id.nickInput);
        Button playBtn = findViewById(R.id.playBtn);

        SharedPreferences prefs = getSharedPreferences("LauncherData", MODE_PRIVATE);
        nickInput.setText(prefs.getString("nickname", ""));

        playBtn.setOnClickListener(v -> {
            String nick = nickInput.getText().toString().trim();
            if (nick.isEmpty()) {
                Toast.makeText(this, "Введите никнейм!", Toast.LENGTH_SHORT).show();
                return;
            }
            prefs.edit().putString("nickname", nick).apply();

            Intent launchIntent = null;
            for (String pkg : LAUNCHER_PACKAGES) {
                launchIntent = getPackageManager().getLaunchIntentForPackage(pkg);
                if (launchIntent != null) break;
            }

            if (launchIntent != null) {
                Toast.makeText(this, "Запуск для " + nick + "...", Toast.LENGTH_SHORT).show();
                startActivity(launchIntent);
            } else {
                Toast.makeText(this, "Установленный лаунчер игры не найден!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
