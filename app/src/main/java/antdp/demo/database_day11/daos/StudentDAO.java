package antdp.demo.database_day11.daos;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import antdp.demo.database_day11.dtos.StudentDTO;

public class StudentDAO {

    public List<StudentDTO> loadDataFromRaw(InputStream is) throws Exception{
        List<StudentDTO> listStudent = new ArrayList<>();
        BufferedReader br = null;
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String s = "";
            StudentDTO dto = null;
            while ((s = br.readLine()) != null){
                String[] tmp = s.split("-");
                dto = new StudentDTO(tmp[0], tmp[1], Float.parseFloat(tmp[2]));
                listStudent.add(dto);
            }
        } finally {
            try {
                if (br != null){
                    br.close();
                }
                if(isr != null){
                    isr.close();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return listStudent;
    }

    public void SaveToInternal(FileOutputStream fos, List<StudentDTO> list )throws Exception{
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        try{
            String result = "";
            for (StudentDTO dto: list) {
                result += dto.toString() + "\n";
            }
            osw.write(result);
            osw.flush();
        }
        finally {
            try {
                if(osw != null){
                    osw.close();
                }
                if (fos != null){
                    fos.close();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public List<StudentDTO> loadDataFromInternal(FileInputStream fis) throws Exception{
        List<StudentDTO> listStudent = new ArrayList<>();
        BufferedReader br = null;
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            String s = "";
            StudentDTO dto = null;
            while ((s = br.readLine()) != null){
                String[] tmp = s.split("-");
                dto = new StudentDTO(tmp[0], tmp[1], Float.parseFloat(tmp[2]));
                listStudent.add(dto);
            }
        } finally {
            try {
                if (br != null){
                    br.close();
                }
                if(isr != null){
                    isr.close();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return listStudent;
    }

    public void saveFromInternalTOExternal(List<StudentDTO> list) throws Exception{
        try{
            File sdCard = Environment.getExternalStorageDirectory();
            String realPath = sdCard.getAbsolutePath();
            File directory = new File(realPath + "/MyFiles");
            if(!directory.exists()){
                directory.mkdir();
            }
            File file = new File(directory, "antdp.txt");
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            String result = "";
            for (StudentDTO dto :
                    list) {
                result += dto.toString() + "\n";
            }
            osw.write(result);
            osw.flush();
        }finally {

        }
    }

    public List<StudentDTO> loadFromExternal() throws Exception{
        List<StudentDTO> listStudent = new ArrayList<>();
        try{
            File sdCard = Environment.getExternalStorageDirectory();
            String realPath = sdCard.getAbsolutePath();
            File directory = new File(realPath + "/MyFiles");
            File file = new File(directory, "antdp.txt");
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String s = "";
            StudentDTO dto = null;
            while ((s = br.readLine()) != null){
                String[] tmp = s.split("-");
                dto = new StudentDTO(tmp[0], tmp[1], Float.parseFloat(tmp[2]));
                listStudent.add(dto);
            }
        }
        finally {

        }
        return listStudent;
    }
}
