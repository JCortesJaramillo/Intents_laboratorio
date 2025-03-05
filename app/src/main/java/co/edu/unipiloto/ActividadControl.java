package co.edu.unipiloto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActividadControl extends AppCompatActivity {

    private TextView Historial;
    private EditText Respuesta;
    private Button Responder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_control);

        Historial = findViewById(R.id.HistorialMensajes);
        Respuesta = findViewById(R.id.Respuesta);
        Responder = findViewById(R.id.Contestar);

        SharedPreferences sharedPreferences = getSharedPreferences("Historial", MODE_PRIVATE);
        String chatHistory = sharedPreferences.getString("historial", "");
        Historial.setText(chatHistory);

        Intent intent = getIntent();
        String messageFromOwner = intent.getStringExtra("mensaje");

        if (messageFromOwner != null && !chatHistory.contains("Propietario: " + messageFromOwner)) {
            Historial.append("\nPropietario: " + messageFromOwner);
        }

        Responder.setOnClickListener(v -> {
            String responseMessage = Respuesta.getText().toString();
            if (!responseMessage.isEmpty()) {
                String updatedHistory = chatHistory + "\nCuidador: " + responseMessage;

                Historial.setText(updatedHistory);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("historial", updatedHistory);
                editor.apply();

                Intent intentBack = new Intent(ActividadControl.this, ActividadPrincipal.class);
                intentBack.putExtra("mensajeRespuesta", responseMessage);
                startActivity(intentBack);
            } else {
                Toast.makeText(ActividadControl.this, "Esperando respuesta...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
