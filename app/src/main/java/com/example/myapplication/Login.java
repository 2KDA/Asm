package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Model.UserData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Login extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button btn_dangky;
    private Button btn_dangnhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.username = findViewById(R.id.ed_userName);
        this.password = findViewById(R.id.ed_passWord);
        this.btn_dangky = findViewById(R.id.btn_dangky);
        this.btn_dangnhap = findViewById(R.id.btn_dangnhap);

        btn_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });

        btn_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login("http://10.0.2.2:3000/api/users/login");
            }
        });

    }

    private void login(String link) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        String urlLink = link;

        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlLink);
                    //mã kết nối
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    //THiết lập phương thức POST , mặc định sẽ là GET
                    http.setRequestMethod("POST");
                    //Tạo đối tượng dữ liệu gửi lên server
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("userName", username.getText().toString());
                    jsonObject.put("passWord", password.getText().toString());


                    http.setRequestProperty("Content-Type", "application/json");
                    //Tạo đối tượng out dữ liệu ra khỏi ứng dụng để gửi lên server
                    OutputStream outputStream = http.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                    bufferedWriter.append(jsonObject.toString());
                    //Xóa bộ đệm
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    //Nhận lại dữ liệu phản hồi
                    int responseCode = http.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Xử lý dữ liệu phản hồi từ server
                        InputStream inputStream = http.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            response.append(line);
                        }
                        bufferedReader.close();
                        inputStream.close();

                        // Giải mã dữ liệu JSON phản hồi từ server để lấy thông tin người dùng
                        JSONObject responseJson = new JSONObject(response.toString());
                        String userId = responseJson.optString("userId");
                        String returnedUsername = responseJson.optString("userName");
                        String returnedPassword = responseJson.optString("passWord");

                        // Tạo đối tượng UserData để truyền sang màn hình Home
                        UserData userData = new UserData(returnedUsername, returnedPassword, userId);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Login.this, "Đăng nhập thành công " + userId, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, Home.class);
                                intent.putExtra("userData", userData);
                                startActivity(intent);
                                finish(); // Đóng màn hình đăng nhập
                            }
                        });
                    } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Login.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Login.this, "Vui lòng điền đầy đủ thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Login.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}