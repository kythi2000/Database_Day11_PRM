package antdp.demo.database_day11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import antdp.demo.database_day11.daos.StudentDAO;
import antdp.demo.database_day11.dtos.StudentDTO;

public class DetailActivity extends AppCompatActivity {

    private EditText edtID, edtName, edtMark;
    private String action;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        edtID = findViewById(R.id.edtID);
        edtName = findViewById(R.id.edtName);
        edtMark = findViewById(R.id.edtMark);
        Intent intent = getIntent();
        action = intent.getStringExtra("action");
        if (action.equals("update")){
            StudentDTO dto = (StudentDTO) intent.getSerializableExtra("dto");
            edtID.setText(dto.getId());
            edtName.setText(dto.getName());
            edtMark.setText(dto.getMark() + "");
        }
    }

    public void clickToSave(View view) {
        String id = edtID.getText().toString();
        String name = edtName.getText().toString();
        float mark = Float.parseFloat(edtMark.getText().toString());

        try {
            StudentDAO dao = new StudentDAO();
            FileInputStream fis = openFileInput("antdp.txt");
            List<StudentDTO> listStudent = dao.loadDataFromInternal(fis);
            if(action.equals("create")){
                StudentDTO dto = new StudentDTO(id, name ,mark);
                listStudent.add(dto);
            }else if(action.equals("update")){
                for (StudentDTO dto: listStudent) {
                    if(dto.getId().equals(id)){
                        dto.setName(name);
                        dto.setMark(mark);
                        break;
                    }
                }
            }
            FileOutputStream fos = openFileOutput("antdp.txt", MODE_PRIVATE);
            dao.SaveToInternal(fos, listStudent);
            Toast.makeText(this, "Save Internal Success", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}