package com.example.onthi.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onthi.Home;
import com.example.onthi.Modal.Response;
import com.example.onthi.Modal.XeMay;
import com.example.onthi.R;
import com.example.onthi.services.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.viewHolder> {
    private final Context context;
    private List<XeMay> list;
    Home home;


    public SanPhamAdapter(Context context, List<XeMay> list) {
        this.context = context;
        this.list = list;
        home = (Home) context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item, parent, false);
        return new viewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        if (position >= 0 && position <= list.size()) {
            XeMay sv = list.get(position);

            holder.txtTen.setText("Tên: " + sv.getTen_xe_ph42469());
            holder.txtGia.setText("Màu sác: " + sv.getMau_sac_ph42469());
            holder.txtSoLuong.setText("Giá bán: " + sv.getGia_ban_ph42469());

            Glide.with(holder.itemView.getContext())
                    .load(sv.getHinh_anh_ph42469())
                    .into(holder.imgAvatar);

            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String idStudent = sv.get_id();

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Cảnh báo");
                    builder.setMessage("Bạn có muốn xóa không?");
                    builder.setNegativeButton("No", null);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Call<Response<XeMay>> call = ApiService.apiService.deleteStudent(sv.get_id());
                            call.enqueue(new Callback<Response<XeMay>>() {
                                @Override
                                public void onResponse(Call<Response<XeMay>> call, retrofit2.Response<Response<XeMay>> response) {
                                    if (response.isSuccessful()){
                                        if (response.body().getStatus() == 200){
                                            list.remove(position);
                                            notifyDataSetChanged();
                                            Toast.makeText(context, response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<Response<XeMay>> call, Throwable t) {
                                    Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                    builder.show();
                }
            });

            holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    home.showDialog(context, sv, 0, list);
                }
            });


            holder.txtChiTiet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    home.chiTiet(context, sv, 0, list);
                }
            });



        }
    }


    @Override
    public int getItemCount() {
        if (list.size() > 0) {
            return list.size();
        }
        return 0;
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView txtTen, txtGia, txtSoLuong,txtMota,txtChiTiet,btnUpdate, btnDelete;
        ImageView imgAvatar;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txtTen);
            txtGia = itemView.findViewById(R.id.txtGia);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
            txtMota = itemView.findViewById(R.id.txtMota);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            txtChiTiet = itemView.findViewById(R.id.txtChiTiet);
        }
    }


}
