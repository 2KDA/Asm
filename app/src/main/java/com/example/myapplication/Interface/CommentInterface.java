package com.example.myapplication.Interface;

import com.example.myapplication.Model.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CommentInterface {
    @GET("comment") // Thay "comment" bằng đường dẫn API lấy danh sách comment theo id truyện
    Call<List<Comment>> getCommentsByComicId(@Query("id_comics") String comicId);
    // Định nghĩa phương thức POST để thêm bình luận mới
    @FormUrlEncoded
    @POST("comment/post") // Điền đúng đường dẫn API để thêm bình luận
    Call<Comment> addComment(
            @Field("id_comics") String comicId,
            @Field("id_user") String userId,
            @Field("desc") String commentContent
    );
}
