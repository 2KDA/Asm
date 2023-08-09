package com.example.myapplication.FRAGMENT;

import android.app.Notification;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.Adapter.viewpager2.CommentAdapter;
import com.example.myapplication.Interface.CommentInterface;
import com.example.myapplication.Model.Comics;
import com.example.myapplication.Model.Comment;
import com.example.myapplication.Model.UserData;
import com.example.myapplication.NotifyConfig;
import com.example.myapplication.R;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class fragment_ml extends Fragment {
    private Comics commic; // Thêm biến để lưu thông tin Commic
    private UserData userData;
    private List<UserData> userDataList;
    private View view; // Khai báo biến view ở đây
    private RecyclerView recyclerViewComments;
    private List<Comment> commentList;
    private CommentAdapter commentAdapter;
    static final String BASE_URL = "http://10.0.2.2:3000/api/";

    private Socket mSocket;

    {
        try {
            mSocket = IO.socket("http://10.0.2.2:3000");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    public static fragment_ml newInstance(Comics comic, UserData userData, List<UserData> userDataList) {
        fragment_ml fragment = new fragment_ml();
        Bundle args = new Bundle();
        args.putParcelable("COMIC_EXTRA", comic);
        args.putParcelable("USER_DATA_EXTRA", userData);
        args.putParcelableArrayList("USER_DATA_LIST_EXTRA", new ArrayList<>(userDataList));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            commic = getArguments().getParcelable("COMIC_EXTRA");
            userData = getArguments().getParcelable("USER_DATA_EXTRA");
            userDataList = getArguments().getParcelableArrayList("USER_DATA_LIST_EXTRA");
            // Tiếp tục xử lý thông tin `comic` và `userData` theo ý muốn
        }

        //Socket
        mSocket.connect();
        //lắng nghe sự kiện

        mSocket.on("new msg", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data_sv_send= (String) args[0];
                        Toast.makeText(getActivity(), "Server trả về "+data_sv_send, Toast.LENGTH_SHORT).show();
                        // hiển thị noify status
                        postNotify("Thông báo từ server" , data_sv_send);
                    }
                });
            }
        });

    }

    void postNotify(String title, String content){
        // Khởi tạo layout cho Notify
        Notification customNotification = new NotificationCompat.Builder(getActivity(), NotifyConfig.CHANEL_ID)
                .setSmallIcon(android.R.drawable.ic_delete)
                .setContentTitle( title )
                .setContentText(content)
                .setAutoCancel(true)

                .build();
        // Khởi tạo Manager để quản lý notify
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity());

        // Cần kiểm tra quyền trước khi hiển thị notify
        if (ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

            // Gọi hộp thoại hiển thị xin quyền người dùng
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 999999);
            Toast.makeText(getActivity(), "Chưa cấp quyền", Toast.LENGTH_SHORT).show();
            return; // thoát khỏi hàm nếu chưa được cấp quyền
        }
        // nếu đã cấp quyền rồi thì sẽ vượt qua lệnh if trên và đến đây thì hiển thị notify
        // mỗi khi hiển thị thông báo cần tạo 1 cái ID cho thông báo riêng
        int id_notiy = (int) new Date().getTime();// lấy chuỗi time là phù hợp
        //lệnh hiển thị notify
        notificationManagerCompat.notify(id_notiy , customNotification);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ml, container, false);

        recyclerViewComments = view.findViewById(R.id.recyclerViewComments);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(getActivity()));
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList,userDataList);
        recyclerViewComments.setAdapter(commentAdapter);
        if (commic != null) {

        }
        // TODO: Xử lý các sự kiện khi click vào các nút button (nếu cần)
        getCommentsByComicId(commic.get_id());  // Gọi API để lấy danh sách comment
        ImageView btnAddComment = view.findViewById(R.id.btncm);
        EditText editTextComment = view.findViewById(R.id.edtcm);
        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userData.getId() == null) {
                    // Hiển thị thông báo "Cần đăng nhập mới bình luận được"
                    Toast.makeText(getActivity(), "Cần đăng nhập mới bình luận được", Toast.LENGTH_SHORT).show();
                } else {
                    // Thêm bình luận theo id truyện
                    String commentContent = editTextComment.getText().toString().trim();
                    if (!commentContent.isEmpty()) {
                        // Gọi API để thêm bình luận mới
                        addComment(commic.get_id(), userData.getId(), commentContent);
                    }
                }
            }
        });

        return view;
    }

    private void getCommentsByComicId(String comicId) {
        // Tạo Retrofit và interface để gọi API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CommentInterface commentInterface = retrofit.create(CommentInterface.class);

        // Gọi API lấy danh sách comment theo ID truyện
        Call<List<Comment>> call = commentInterface.getCommentsByComicId(comicId);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    List<Comment> comments = response.body();
                    if (comments != null && !comments.isEmpty()) {
                        commentList.addAll(comments);
                        commentAdapter.notifyDataSetChanged();
                    } else {
                        // Hiển thị thông báo không có bình luận nào
                    }
                } else {
                    // Hiển thị thông báo lỗi
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                // Hiển thị thông báo lỗi
            }
        });
    }
    private void addComment(String id_comics, String id_user, String desc) {
        // Tạo Retrofit và interface để gọi API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CommentInterface commentInterface = retrofit.create(CommentInterface.class);

        // Gọi API để thêm bình luận mới
        Call<Comment> call = commentInterface.addComment(id_comics, id_user, desc);
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if (response.isSuccessful()) {
                    // Cập nhật danh sách bình luận sau khi thêm thành công
                    Comment newComment = response.body();
                    List<Comment> newComments = new ArrayList<>(commentList);
                    newComments.add(newComment);
                    commentAdapter.updateCommentList(newComments);
                    mSocket.emit("new msg","Binh luan thanh cong");
                } else {
                    // Hiển thị thông báo lỗi khi thêm bình luận không thành công
                    Toast.makeText(getActivity(), "Lỗi khi thêm bình luận", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                // Hiển thị thông báo lỗi khi gọi API thêm bình luận thất bại
                Toast.makeText(getActivity(), "Lỗi khi gọi API thêm bình luận", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
