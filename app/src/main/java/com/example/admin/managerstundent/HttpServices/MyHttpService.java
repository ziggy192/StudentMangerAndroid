package com.example.admin.managerstundent.HttpServices;

import com.example.admin.managerstundent.DTO.AccountDTO;
import com.example.admin.managerstundent.DTO.SlotRequestPostDTO;
import com.example.admin.managerstundent.Entity.ClassDetail;
import com.example.admin.managerstundent.Entity.SlotRequestedModel;
import com.example.admin.managerstundent.Entity.Student;
import com.example.admin.managerstundent.Entity.Subject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MyHttpService {
    @GET("students/{id}")
    Call<Student> getStudentById(@Path("id") int id);

    @PUT("students")
    Call<Student> putStudentById( @Body Student student);

    @GET("slotRequesteds/{id}")
    Call<List<SlotRequestedModel>> getSlotRequestedByStudentId(@Path("id") int id);

    @POST("slotRequesteds/{id}")
    Call<List<SlotRequestedModel>> postSlotRequested(@Path("id") int studentId, @Body SlotRequestPostDTO postDTO);

    @GET("classDetails/{studentId}")
    Call<List<ClassDetail>> getClassDetailsByStudentId(@Path("studentId") int studentId);


    @GET("subjects")
    Call<List<Subject>> getAllSubjects();

    @GET("subjects/{subjectId}/classDetails")
    Call<List<ClassDetail>> getClassDetailsBySubjectId(@Path("subjectId") int subjectId);

    @POST("accounts")
    Call<Student> postLogin(@Body AccountDTO accountDTO);




    //for testing
    @GET("users/{user}/repos")
    Call<List<ResponseBody>> listRepos(@Path("user") String user);

}
