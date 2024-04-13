package com.example.onthi;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onthi.Modal.Response;
import com.example.onthi.Modal.XeMay;
import com.example.onthi.adapter.SanPhamAdapter;
import com.example.onthi.services.ApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;


public class Home extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 10;
    List<XeMay> list = new ArrayList<>();
    FloatingActionButton fltadd;
    ImageView imagePiker;
    private File file;

    public RecyclerView rcvSV ;
    public SanPhamAdapter adapter;

    Button btnUp, btnDown, btnSearch;
    EditText txtsearch;


    private File createFileFormUri (Uri path, String name) {
        File _file = new File(Home.this.getCacheDir(), name + ".png");
        try {
            InputStream in = Home.this.getContentResolver().openInputStream(path);
            OutputStream out = new FileOutputStream(_file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) >0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
            Log.d("123123", "createFileFormUri: " +_file);
            return _file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fltadd = findViewById(R.id.fltadd);
        rcvSV = findViewById(R.id.rcvSV);

        loadData();
        getView();

        fltadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(Home.this,new XeMay(),1,list);
            }
        });

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortStudent(-1);
            }
        });
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortStudent(1);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = txtsearch.getText().toString().trim();
                searchStudent(key);
            }
        });
    }

    private void getView(){
        btnDown = findViewById(R.id.btnDown);
        btnUp = findViewById(R.id.btnUp);
        btnSearch = findViewById(R.id.btnSearch);
        txtsearch = findViewById(R.id.txtsearch);
    }


    public void showDialog (Context context, XeMay xeMay, Integer type, List<XeMay> list){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        LayoutInflater inflater= ((Activity) context).getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_item,null);
        builder.setView(view);
        Dialog dialog=builder.create();
        dialog.show();

        EditText edtTen = view.findViewById(R.id.edtTen);
        EditText edtGia = view.findViewById(R.id.edtGia);
        EditText edtSoLuong = view.findViewById(R.id.edtSoLuong);
        EditText edtMoTa = view.findViewById(R.id.edtmoTa);
        imagePiker = view.findViewById(R.id.imgAvatarSV);
        Button btnChonAnh =view.findViewById(R.id.btnChonAnh);
        Button btnSave =view.findViewById(R.id.btnSave);

        if (type == 0){
            edtTen.setText(xeMay.getTen_xe_ph42469());
            edtGia.setText(xeMay.getMau_sac_ph42469());
            edtSoLuong.setText(xeMay.getGia_ban_ph42469()+"");
            edtMoTa.setText(xeMay.getMo_ta_ph42469());
            Glide.with(view).load(xeMay.getHinh_anh_ph42469()).into(imagePiker);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = edtTen.getText().toString();
                String gia = edtGia.getText().toString();
                String soLuong = edtSoLuong.getText().toString();
                String moTa = edtMoTa.getText().toString();


                if (ten.isEmpty() || gia.isEmpty()|| soLuong.isEmpty()){
                    Toast.makeText(context, "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                }else if(!iSo(soLuong)){

                    Toast.makeText(context, "Giá phải là số", Toast.LENGTH_SHORT).show();
                }
                    else {



                        RequestBody rTen = RequestBody.create(MediaType.parse("multipart/form-data"), ten);
                        RequestBody rGia = RequestBody.create(MediaType.parse("multipart/form-data"), gia);
                        RequestBody rSoLuong = RequestBody.create(MediaType.parse("multipart/form-data"), soLuong);
                        RequestBody rMoTa = RequestBody.create(MediaType.parse("multipart/form-data"), moTa);
                        MultipartBody.Part muPart;
                        if (file != null){
                            RequestBody rAvatar = RequestBody.create(MediaType.parse("image/*"),file);
                            muPart = MultipartBody.Part.createFormData("image",file.getName(),rAvatar);
                        }else {
                            muPart = null;
                        }

                        Call<Response<XeMay>> call = ApiService.apiService.addStudent(rTen,rGia,rSoLuong,rMoTa,muPart);
                        if(type == 0){
                            call = ApiService.apiService.updateStudent(xeMay.get_id(),rTen,rGia,rSoLuong,rMoTa,muPart);
                        }
                        call.enqueue(new Callback<Response<XeMay>>() {
                            @Override
                            public void onResponse(Call<Response<XeMay>> call, retrofit2.Response<Response<XeMay>> response) {
                                if (response.isSuccessful()){
                                    if(response.body().getStatus() == 200){
                                        Toast.makeText(Home.this,"Success", Toast.LENGTH_SHORT).show();
                                        loadData();
                                        dialog.dismiss();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Response<XeMay>> call, Throwable t) {
                                Toast.makeText(Home.this, "Fail"+t, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                    }

            }
        });

        btnChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
    }


    public void chiTiet (Context context, XeMay xeMay, Integer type, List<XeMay> list){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        LayoutInflater inflater= ((Activity) context).getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_chi_tiet,null);
        builder.setView(view);
        Dialog dialog=builder.create();
        dialog.show();

        TextView edtTen = view.findViewById(R.id.txtTenCt);
        TextView edtGia = view.findViewById(R.id.edtGia);
        TextView edtSoLuong = view.findViewById(R.id.edtSoLuong);
        TextView edtMoTa = view.findViewById(R.id.edtmoTa);
        imagePiker = view.findViewById(R.id.imgAvatarSV);
        Button btnSave =view.findViewById(R.id.btnSave);

        if (type == 0){
            edtTen.setText(xeMay.getTen_xe_ph42469());
            edtGia.setText(xeMay.getMau_sac_ph42469());
            edtSoLuong.setText(xeMay.getGia_ban_ph42469()+"");
            edtMoTa.setText(xeMay.getMo_ta_ph42469());
            Glide.with(view).load(xeMay.getHinh_anh_ph42469()).into(imagePiker);
        }
    btnSave.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog.dismiss();
        }
    });

    }


    public void loadData (){

        Call<Response<List<XeMay>>> call = ApiService.apiService.getData();

        call.enqueue(new Callback<Response<List<XeMay>>>() {
            @Override
            public void onResponse(Call<Response<List<XeMay>>> call, retrofit2.Response<Response<List<XeMay>>> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus() == 200){
                        List<XeMay> ds = response.body().getData();
                        Log.d("List","a : "+response.body().getData());
                        getData(ds);
                    }
                }
            }

            @Override
            public void onFailure(Call<Response<List<XeMay>>> call, Throwable t) {
                Log.d("List","a : "+ t);
            }
        });
    }

    public void getData (List<XeMay> list){
        adapter = new SanPhamAdapter(Home.this, list);
        rcvSV.setLayoutManager(new LinearLayoutManager(Home.this));
        rcvSV.setAdapter(adapter);
    }

    public void searchStudent(String key){
        Call<Response<List<XeMay>>> call = ApiService.apiService.searchStudent(key);
        call.enqueue(new Callback<Response<List<XeMay>>>() {
            @Override
            public void onResponse(Call<Response<List<XeMay>>> call, retrofit2.Response<Response<List<XeMay>>> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus() == 200){
                        List<XeMay> ds = response.body().getData();
                        getData(ds);
                    }
                }
            }

            @Override
            public void onFailure(Call<Response<List<XeMay>>> call, Throwable t) {
                Log.d("Search","Lỗi : "+ t);
            }
        });
    }

    public void sortStudent(Integer type){
        Call<Response<List<XeMay>>> call = ApiService.apiService.sortStudent(type);
        call.enqueue(new Callback<Response<List<XeMay>>>() {
            @Override
            public void onResponse(Call<Response<List<XeMay>>> call, retrofit2.Response<Response<List<XeMay>>> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus() == 200){
                        List<XeMay> ds = response.body().getData();
                        getData(ds);
                    }
                }
            }

            @Override
            public void onFailure(Call<Response<List<XeMay>>> call, Throwable t) {

            }
        });
    }

    private void chooseImage() {
        Log.d("123123", "chooseAvatar: " +123123);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        getImage.launch(intent);
    }
    ActivityResultLauncher<Intent> getImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == Activity.RESULT_OK) {
                        Intent data = o.getData();
                        Uri imageUri = data.getData();

                        Log.d("RegisterActivity", imageUri.toString());

                        Log.d("123123", "onActivityResult: "+data);

                        file = createFileFormUri(imageUri, "image");

                        Glide.with(imagePiker)
                                .load(imageUri)
                                .centerCrop()
                                .into(imagePiker);

                    }
                }
            });
    public boolean iSo ( String so){
        return so.matches("\\d+");
    }
}