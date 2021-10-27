package antdp.demo.database_day11;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import antdp.demo.database_day11.daos.StudentAdapter;
import antdp.demo.database_day11.daos.StudentDAO;
import antdp.demo.database_day11.databinding.ActivityMainBinding;
import antdp.demo.database_day11.dtos.StudentDTO;

public class MainActivity extends AppCompatActivity {

    private TextView txtTitle;
    private ListView listViewStudent;
    private StudentAdapter adapter;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private static final int MY_WRITE_EXTERNAL_STORAGE = 6789;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){

        }else {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, MY_WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();


    }

    public void clickToLoadRAW(MenuItem item) {
        try {
            txtTitle = findViewById(R.id.txtTitle);
            listViewStudent = findViewById(R.id.listViewStudent);
            adapter = new StudentAdapter();
            StudentDAO dao = new StudentDAO();
            InputStream inputStream = getResources().openRawResource(R.raw.data);
            List<StudentDTO> listStudent = dao.loadDataFromRaw(inputStream);
            adapter.setListStudent(listStudent);
            listViewStudent.setAdapter(adapter);
            listViewStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    StudentDTO dto = (StudentDTO) listViewStudent.getItemAtPosition(position);
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("dto", dto);
                    intent.putExtra("action", "update");
                    startActivity(intent);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clickToCreateStudent(View view) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("action", "create");
        startActivity(intent);
    }

    public void clickToSaveDataFromRAWToInternal(MenuItem item) {
        try {
            StudentDAO dao = new StudentDAO();
            InputStream is = getResources().openRawResource(R.raw.data);
            List<StudentDTO> listStudent = dao.loadDataFromRaw(is);
            FileOutputStream fos = openFileOutput("antdp.txt", MODE_PRIVATE);
            dao.SaveToInternal(fos, listStudent);
            Toast.makeText(this, "Save to Internal Success", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clickToLoadFromInternal(MenuItem item) {
        try {
            txtTitle = findViewById(R.id.txtTitle);
            listViewStudent = findViewById(R.id.listViewStudent);
            adapter = new StudentAdapter();
            StudentDAO dao = new StudentDAO();

            txtTitle.setText("List Student from Internal");
            FileInputStream fis = openFileInput("antdp.txt");
            List<StudentDTO> listStudent = dao.loadDataFromInternal(fis);

            adapter.setListStudent(listStudent);
            listViewStudent.setAdapter(adapter);
            listViewStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    StudentDTO dto = (StudentDTO) listViewStudent.getItemAtPosition(position);
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("dto", dto);
                    intent.putExtra("action", "update");
                    startActivity(intent);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clickToSaveDataFromInternalToExternal(MenuItem item) {
        try {
            FileInputStream fis = openFileInput("antdp.txt");
            StudentDAO dao = new StudentDAO();
            List<StudentDTO> list = dao.loadDataFromInternal(fis);
            dao.saveFromInternalTOExternal(list);
            Toast.makeText(this, "Save to SD Card Success", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clickToLoadFromExternal(MenuItem item) {
        try {
            txtTitle = findViewById(R.id.txtTitle);
            listViewStudent = findViewById(R.id.listViewStudent);
            adapter = new StudentAdapter();
            StudentDAO dao = new StudentDAO();

            txtTitle.setText("List Student from SD Card");

            List<StudentDTO> listStudent = dao.loadFromExternal();

            adapter.setListStudent(listStudent);
            listViewStudent.setAdapter(adapter);
            listViewStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    StudentDTO dto = (StudentDTO) listViewStudent.getItemAtPosition(position);
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("dto", dto);
                    intent.putExtra("action", "update");
                    startActivity(intent);
                }
            });
        }catch (Exception e){

        }
    }

    public void clickToBackupDataToExternal(MenuItem item) {
        try {
            //des
            File sdCard = Environment.getExternalStorageDirectory(  );
            String realPath = sdCard.getAbsolutePath();
            String desDir = realPath + "/MyFiles";
            File directory = new File(desDir);  //external
            if(!directory.exists()){
                directory.mkdir();
            }

            //src
            String dataPath = "/data/data/" + getPackageName() + "/files";
            File dataDir = new File(dataPath); //internal

            File[] listFile = dataDir.listFiles();
            if(listFile != null){
                MyUtils utils = new MyUtils();
                for (int i = 0; i < listFile.length; i++) {
                    File f = listFile[i];
                    utils.copyFile(f.getAbsolutePath(), desDir + "/" + f.getName());
                }
                Toast.makeText(this, "Backup Success", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clickToRestoreDataFromExternal(MenuItem item) {
        try {
            //des
            File sdCard = Environment.getExternalStorageDirectory(  );
            String realPath = sdCard.getAbsolutePath();
            String desDir = realPath + "/MyFiles";
            File directory = new File(desDir);  //external


            //src
            String dataPath = "/data/data/" + getPackageName() + "/files";
            File dataDir = new File(dataPath); //internal

            File[] listFile = directory.listFiles(); //
            if(listFile != null){
                MyUtils utils = new MyUtils();
                for (int i = 0; i < listFile.length; i++) {
                    File f = listFile[i];
                    utils.copyFile(f.getAbsolutePath(), dataPath + "/" + f.getName());
                }
                Toast.makeText(this, "Restore Success", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clickToCallPreferenceActivity(MenuItem item) {
        Intent intent = new Intent(this , StudentPreferenceActivity.class);
        startActivity(intent);
    }
}