package com.example.myapplication.Adapter.viewpager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Comment;
import com.example.myapplication.Model.UserData;
import com.example.myapplication.R;


import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{
    private List<Comment> commentList;
    private List<UserData> userDataList;

    public CommentAdapter(List<Comment> commentList, List<UserData> userDataList) {
        this.commentList = commentList;
        this.userDataList = userDataList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.txtCommentContent.setText(comment.getDesc());
        holder.txttime.setText(comment.getTime());

        // Tìm username tương ứng với userId trong danh sách userDataList
        String userId = comment.getId_user();
        String username = findUsernameByUserId(userId);
        holder.txtid.setText(userId);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView txtCommentContent, txtid, txttime;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            txtid = itemView.findViewById(R.id.txtid);
            txtCommentContent = itemView.findViewById(R.id.txtCommentContent);
            txttime = itemView.findViewById(R.id.txttime);
        }
    }

    public void updateCommentList(List<Comment> newComments) {
        commentList.clear();
        commentList.addAll(newComments);
        notifyDataSetChanged();
    }

    // Phương thức để tìm username theo userId
    private String findUsernameByUserId(String userId) {
        if (userDataList != null) {
            for (UserData userData : userDataList) {
                if (userData.getId().equals(userId)) {
                    return userData.getUsername();
                }
            }
        }
        return "Người dùng không xác định"; // Trả về giá trị mặc định nếu không tìm thấy
    }
}
