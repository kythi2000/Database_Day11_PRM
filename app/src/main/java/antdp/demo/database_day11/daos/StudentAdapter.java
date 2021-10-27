package antdp.demo.database_day11.daos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import antdp.demo.database_day11.R;
import antdp.demo.database_day11.dtos.StudentDTO;

public class StudentAdapter extends BaseAdapter {
    private List<StudentDTO> listStudent;

    public void setListStudent(List<StudentDTO> listStudent){
        this.listStudent = listStudent;
    }

    @Override
    public int getCount() {
        return listStudent.size();
    }

    @Override
    public Object getItem(int position) {
        return listStudent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item, parent,false);

        }
        TextView txtID = convertView.findViewById(R.id.txtID);
        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtMark = convertView.findViewById(R.id.txtMark);
        StudentDTO dto = listStudent.get(position);
        txtID.setText(dto.getId());
        txtName.setText(dto.getName());
        txtMark.setText(dto.getMark() + "");
        return convertView;
    }
}
