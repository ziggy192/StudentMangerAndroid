package com.example.admin.managerstundent.HttpServices;

import android.util.Log;

import com.example.admin.managerstundent.Constant.Constant;
import com.example.admin.managerstundent.DTO.AccountDTO;
import com.example.admin.managerstundent.DTO.SlotRequestPostDTO;
import com.example.admin.managerstundent.Entity.ClassDetail;
import com.example.admin.managerstundent.Entity.SlotRequestedModel;
import com.example.admin.managerstundent.Entity.Student;
import com.example.admin.managerstundent.Entity.Subject;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpHelper {
    private static final String TAG = HttpHelper.class.toString();

    private static HttpHelper instance;

    private MyHttpService myHttpService;

    private HttpHelper(MyHttpService myHttpService) {
        this.myHttpService = myHttpService;
    }

    public static void initInstance() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.d(TAG, "initInstance: BaseApiUrl="+Constant.BASE_API_URL);

        MyHttpService myHttpService = retrofit.create(MyHttpService.class);
        instance = new HttpHelper(myHttpService);
    }

    public static HttpHelper getIntance() {
        if (instance == null) {
            initInstance();
        }
        return instance;
    }

    public void startGetStudentProfile(final int studentId) {
        Call<Student> call = myHttpService.getStudentById(studentId);

        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                Log.d(TAG, String.format("onResponse: requestbody=%s", call.request().body()));
                Student student = response.body();
                Log.d(TAG, String.format("onGetStudentProfile: Success: student=%s", student));
                EventBus.getDefault().post(new GetStudentProfileResponseEvent(true, student));
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Log.d(TAG, String.format("onGetStudentProfile: Failure:"));
                EventBus.getDefault().post(new GetStudentProfileResponseEvent(false, null));
                t.printStackTrace();

            }
        });
    }

    public void putStudentProfile(Student studentProfile) {
        myHttpService.putStudentById(studentProfile).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                Student student = response.body();
                Log.d(TAG, String.format("PutStudentProfile: Success: student=%s", student));
                EventBus.getDefault().post(new PutStudentProfileResponseEvent(true, student));

            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Log.d(TAG, String.format("PutStudentProfile: Failure:"));
                EventBus.getDefault().post(new PutStudentProfileResponseEvent(false, null));
                t.printStackTrace();
            }
        });
    }

    public void getSlotRequestByStudentId(int studentId) {
        myHttpService.getSlotRequestedByStudentId(studentId).enqueue(new Callback<List<SlotRequestedModel>>() {
            @Override
            public void onResponse(Call<List<SlotRequestedModel>> call, Response<List<SlotRequestedModel>> response) {
                List<SlotRequestedModel> slotRequestedModelList = response.body();
                Log.d(TAG, String.format("getSlotRequestByStudentId: Success: slotReqeustList=%s", slotRequestedModelList));
                EventBus.getDefault().postSticky(new GetSlotRequestedResponseEvent(true, slotRequestedModelList));
            }

            @Override
            public void onFailure(Call<List<SlotRequestedModel>> call, Throwable t) {
                Log.d(TAG, String.format("getSlotRequestByStudentId: Failure:"));
                EventBus.getDefault().postSticky(new GetSlotRequestedResponseEvent(false, null));
                t.printStackTrace();
            }
        });

    }

    public void getClassDetailsByStudentId(int studentId) {
        myHttpService.getClassDetailsByStudentId(studentId).enqueue(new Callback<List<ClassDetail>>() {
            @Override
            public void onResponse(Call<List<ClassDetail>> call, Response<List<ClassDetail>> response) {
                List<ClassDetail> classDetailList = response.body();
                Log.d(TAG, String.format("getClassDetailsByStudentId: Sucess:classdetails=%s", classDetailList));
                EventBus.getDefault().postSticky(new GetClassDetailsListStudentIdResponseEvent(true, classDetailList));

            }

            @Override
            public void onFailure(Call<List<ClassDetail>> call, Throwable t) {
                Log.d(TAG, String.format("getClassDetailsByStudentId: Failure:"));
                EventBus.getDefault().postSticky(new GetClassDetailsListStudentIdResponseEvent(false, null));
                t.printStackTrace();
            }
        });
    }

    public void postSlotRequest(int studentId, SlotRequestPostDTO postDTO) {
        myHttpService.postSlotRequested(studentId,postDTO).enqueue(new Callback<List<SlotRequestedModel>>() {
            @Override
            public void onResponse(Call<List<SlotRequestedModel>> call, Response<List<SlotRequestedModel>> response) {
                List<SlotRequestedModel> slotRequestedModels = response.body();
                Log.d(TAG, String.format("postSlotRequest: Sucess:slotRequestedModels=%s", slotRequestedModels));
                EventBus.getDefault().postSticky(new PostSlotRequestResponseEvent(true, slotRequestedModels));

            }

            @Override
            public void onFailure(Call<List<SlotRequestedModel>> call, Throwable t) {
                Log.d(TAG, String.format("postSlotRequest: Failure:"));
                EventBus.getDefault().postSticky(new PostSlotRequestResponseEvent(false, null));
                t.printStackTrace();
            }
        });
    }


    public void getAllSubjects() {
        myHttpService.getAllSubjects().enqueue(new Callback<List<Subject>>() {
            @Override
            public void onResponse(Call<List<Subject>> call, Response<List<Subject>> response) {
                List<Subject> subjectList = response.body();
                Log.d(TAG, String.format("getAllSubjects: Success, subjectsLIst=%s",subjectList ));
                EventBus.getDefault().postSticky(new GetAllSubjectResponseEvent(true, subjectList));
            }

            @Override
            public void onFailure(Call<List<Subject>> call, Throwable t) {
                Log.d(TAG, String.format("ongetAllSubjects: Error"));
                t.printStackTrace();
                EventBus.getDefault().postSticky(new GetAllSubjectResponseEvent(false, null));

            }
        });
    }

    public void getClassDettailsListBySubjectId(int subjectId) {
        myHttpService.getClassDetailsBySubjectId(subjectId).enqueue(new Callback<List<ClassDetail>>() {
            @Override
            public void onResponse(Call<List<ClassDetail>> call, Response<List<ClassDetail>> response) {
                List<ClassDetail> classDetailList = response.body();
                Log.d(TAG, String.format("getClassDetailsBySubjectId: Success, classDetails=%s",classDetailList ));
                EventBus.getDefault().postSticky(new GetClassDetailsBySubjectIdResponseEvent(true, classDetailList));

            }

            @Override
            public void onFailure(Call<List<ClassDetail>> call, Throwable t) {
                Log.d(TAG, String.format("getClassDettailsListBySubjectId: Error"));
                t.printStackTrace();
                EventBus.getDefault().postSticky(new GetClassDetailsBySubjectIdResponseEvent(false, null));

            }
        });

    }

    public void postLogin(AccountDTO accountDTO) {
        myHttpService.postLogin(accountDTO).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                Student student = response.body();
                Log.d(TAG, String.format("postLogin: Success, student=%s", student));
                EventBus.getDefault().post(new PostLoginEvent(true,student));
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Log.d(TAG, "postLogin: Failure");
                t.printStackTrace();

                EventBus.getDefault().post(new PostLoginEvent(false, null));
            }
        });
    }
    public void testRetrofit() {
        myHttpService.listRepos("octocat").enqueue(new Callback<List<ResponseBody>>() {
            @Override
            public void onResponse(Call<List<ResponseBody>> call, Response<List<ResponseBody>> response) {
                Log.d(TAG, String.format("onResponse: resonseBody=%s", response.body()));
            }

            @Override
            public void onFailure(Call<List<ResponseBody>> call, Throwable t) {
                Log.d(TAG, String.format("onResponse: Error"));
                t.printStackTrace();

            }
        });
    }


    public static class PostLoginEvent{
        private boolean isSuccess;
        private Student student;

        public PostLoginEvent(boolean isSuccess, Student student) {
            this.isSuccess = isSuccess;
            this.student = student;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public Student getStudent() {
            return student;
        }
    }

    public static class GetClassDetailsBySubjectIdResponseEvent{
        private boolean isSuccess;
        private List<ClassDetail> classDetails;

        public GetClassDetailsBySubjectIdResponseEvent(boolean isSuccess, List<ClassDetail> classDetails) {
            this.isSuccess = isSuccess;
            this.classDetails = classDetails;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public void setSuccess(boolean success) {
            isSuccess = success;
        }

        public List<ClassDetail> getClassDetails() {
            return classDetails;
        }

        public void setClassDetails(List<ClassDetail> classDetails) {
            this.classDetails = classDetails;
        }
    }

    public static class GetAllSubjectResponseEvent{
        private boolean isSuccess;
        private List<Subject> subjectList;

        public GetAllSubjectResponseEvent(boolean isSuccess, List<Subject> subjectList) {
            this.isSuccess = isSuccess;
            this.subjectList = subjectList;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public void setSuccess(boolean success) {
            isSuccess = success;
        }

        public List<Subject> getSubjectList() {
            return subjectList;
        }

        public void setSubjectList(List<Subject> subjectList) {
            this.subjectList = subjectList;
        }
    }
    public static class  PostSlotRequestResponseEvent{
        //todo this event have no receiveer because SlotRequestListFragment auto refresh every time
        private boolean isSuccess;
        private List<SlotRequestedModel> slotRequestedModelList;

        public PostSlotRequestResponseEvent(boolean isSuccess, List<SlotRequestedModel> slotRequestedModelList) {
            this.isSuccess = isSuccess;
            this.slotRequestedModelList = slotRequestedModelList;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public void setSuccess(boolean success) {
            isSuccess = success;
        }

        public List<SlotRequestedModel> getSlotRequestedModelList() {
            return slotRequestedModelList;
        }

        public void setSlotRequestedModelList(List<SlotRequestedModel> slotRequestedModelList) {
            this.slotRequestedModelList = slotRequestedModelList;
        }
    }
    public static class GetClassDetailsListStudentIdResponseEvent {
        private boolean isSuccess;
        private List<ClassDetail> classDetailList;

        public GetClassDetailsListStudentIdResponseEvent(boolean isSuccess, List<ClassDetail> classDetailList) {
            this.isSuccess = isSuccess;
            this.classDetailList = classDetailList;
        }

        public List<ClassDetail> getClassDetailList() {
            return classDetailList;
        }

        public boolean isSuccess() {
            return isSuccess;
        }
    }
    public static class GetSlotRequestedResponseEvent {
        boolean isSuccess;
        List<SlotRequestedModel> slotRequestedModelList;

        public GetSlotRequestedResponseEvent(boolean isSuccess, List<SlotRequestedModel> slotRequestedModelList) {
            this.isSuccess = isSuccess;
            this.slotRequestedModelList = slotRequestedModelList;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public void setSuccess(boolean success) {
            isSuccess = success;
        }

        public List<SlotRequestedModel> getSlotRequestedModelList() {
            return slotRequestedModelList;
        }

        public void setSlotRequestedModelList(List<SlotRequestedModel> slotRequestedModelList) {
            this.slotRequestedModelList = slotRequestedModelList;
        }
    }
    public static class PutStudentProfileResponseEvent{
        boolean isSuccess;
        Student student;

        public PutStudentProfileResponseEvent(boolean isSuccess, Student student) {
            this.isSuccess = isSuccess;
            this.student = student;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public Student getStudent() {
            return student;
        }
    }
    public static class GetStudentProfileResponseEvent {
        boolean isSuccess;
        Student student;

        public GetStudentProfileResponseEvent(boolean isSuccess, Student student) {
            this.isSuccess = isSuccess;
            this.student = student;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public Student getStudent() {
            return student;
        }
    }
}
