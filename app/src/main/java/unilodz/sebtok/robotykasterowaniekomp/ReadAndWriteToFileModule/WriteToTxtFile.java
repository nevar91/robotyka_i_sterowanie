package unilodz.sebtok.robotykasterowaniekomp.ReadAndWriteToFileModule;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

import unilodz.sebtok.robotykasterowaniekomp.R;

public class WriteToTxtFile extends AppCompatActivity implements View.OnClickListener {

    private EditText insertedText;
    private Button saveToFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_to_txt_file);

        insertedText = (EditText) findViewById(R.id.insert_edit_text);
        saveToFile = (Button) findViewById(R.id.save_to_file_button);
        saveToFile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Context context = getApplicationContext();
        try{
            String data = insertedText.getText().toString();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("Output.txt", Context.MODE_PRIVATE));
            if(data != null && data != ""){
                outputStreamWriter.write(data);
                outputStreamWriter.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
