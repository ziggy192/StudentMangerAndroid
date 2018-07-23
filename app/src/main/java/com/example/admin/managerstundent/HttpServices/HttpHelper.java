package com.example.admin.managerstundent.HttpServices;

import android.util.Log;

import com.example.admin.managerstundent.Constant.Constant;
import com.example.admin.managerstundent.DTO.SlotRequestPostDTO;
import com.example.admin.managerstundent.Entity.ClassDetail;
import com.example.admin.managerstundent.Entity.SlotRequestedModel;
import com.example.admin.managerstundent.Entity.Student;

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
                EventBus.getDefault().post(new GetSlotRequestedResponseEvent(true, slotRequestedModelList));
            }

            @Override
            public void onFailure(Call<List<SlotRequestedModel>> call, Throwable t) {
                Log.d(TAG, String.format("getSlotRequestByStudentId: Failure:"));
                EventBus.getDefault().post(new GetSlotRequestedResponseEvent(false, null));
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
                EventBus.getDefault().post(new GetClassDetailsListResponseEvent(true, classDetailList));

            }

            @Override
            public void onFailure(Call<List<ClassDetail>> call, Throwable t) {
                Log.d(TAG, String.format("getClassDetailsByStudentId: Failure:"));
                EventBus.getDefault().post(new GetClassDetailsListResponseEvent(false, null));
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
                EventBus.getDefault().post(new PostSlotRequestResponseEvent(true, slotRequestedModels));

            }

            @Override
            public void onFailure(Call<List<SlotRequestedModel>> call, Throwable t) {
                Log.d(TAG, String.format("postSlotRequest: Failure:"));
                EventBus.getDefault().post(new PostSlotRequestResponseEvent(false, null));
                t.printStackTrace();
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
    public static class  GetClassDetailsListResponseEvent{
        private boolean isSuccess;
        private List<ClassDetail> classDetailList;

        public GetClassDetailsListResponseEvent(boolean isSuccess, List<ClassDetail> classDetailList) {
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
