package info.efficacious.centralmodelschool.Interface;


import info.efficacious.centralmodelschool.entity.AssignBookDetailLibPojo;
import info.efficacious.centralmodelschool.entity.AttendanceDetail;
import info.efficacious.centralmodelschool.entity.AttendanceDetailPojo;
import info.efficacious.centralmodelschool.entity.BookDetailLibPojo;
import info.efficacious.centralmodelschool.entity.CategoryDetailLibPojo;
import info.efficacious.centralmodelschool.entity.ChatDetail;
import info.efficacious.centralmodelschool.entity.ChatDetailsPojo;
import info.efficacious.centralmodelschool.entity.DashboardDetailsPojo;
import info.efficacious.centralmodelschool.entity.DeptDetailPojo;
import info.efficacious.centralmodelschool.entity.EventDetail;
import info.efficacious.centralmodelschool.entity.EventDetailPojo;
import info.efficacious.centralmodelschool.entity.LeaveDetail;
import info.efficacious.centralmodelschool.entity.LeaveDetailPojo;
import info.efficacious.centralmodelschool.entity.LibraryDetailPojo;
import info.efficacious.centralmodelschool.entity.LoginDetail;
import info.efficacious.centralmodelschool.entity.LoginDetailsPojo;
import info.efficacious.centralmodelschool.entity.Message;
import info.efficacious.centralmodelschool.entity.MessagePojo;
import info.efficacious.centralmodelschool.entity.NoticeboardDetail;
import info.efficacious.centralmodelschool.entity.OnlineClassDetailPojo;
import info.efficacious.centralmodelschool.entity.OnlineClassDetailsPojo;
import info.efficacious.centralmodelschool.entity.OnlineClassTimetablePojo;
import info.efficacious.centralmodelschool.entity.ProfileDetail;
import info.efficacious.centralmodelschool.entity.ProfileDetailsPojo;
import info.efficacious.centralmodelschool.entity.SchoolDetailsPojo;
import info.efficacious.centralmodelschool.entity.StandardDetail;
import info.efficacious.centralmodelschool.entity.StandardDetailsPojo;
import info.efficacious.centralmodelschool.entity.StudentStandardwiseDetailPojo;
import info.efficacious.centralmodelschool.entity.MarkAttendence;
import info.efficacious.centralmodelschool.entity.SubjectDetailLibPojo;
import info.efficacious.centralmodelschool.entity.SyllabusDetailPojo;
import info.efficacious.centralmodelschool.entity.TeacherDetail;
import info.efficacious.centralmodelschool.entity.TeacherDetailPojo;
import info.efficacious.centralmodelschool.entity.TeacherLibDetailPojo;
import info.efficacious.centralmodelschool.entity.TimeTableDetailPojo;
import io.reactivex.Observable;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface DataService {
    //Login
    @GET("Login")
    Observable<SchoolDetailsPojo> getSchoolDetails(@Query("command") String command);

    @GET("Login")
    Observable<LoginDetailsPojo> getLoginDetails(@Query("command") String command,
                                                 @Query("intUserType_id") String intUserType_id,
                                                 @Query("vchUser_name") String vchUser_name,
                                                     @Query("vchPassword") String vchPassword,
                                                 @Query("intAcademic_id") String intAcademic_id,
                                                 @Query("intSchool_id") String intSchool_id);

    //Dashbaord
    @GET("Dashboard")
    Observable<DashboardDetailsPojo> getDashboardDetails(@Query("command") String command,
                                                         @Query("intAcademic_id") String intAcademic_id,
                                                         @Query("intSchool_id") String intSchool_id);

    @GET("Message")
    Observable<MessagePojo> getMessage(@Query("command")String command,
                                       @Query("intSchool_id") String intSchool_id,
                                       @Query("intStandard_id") String intStandard_id,
                                       @Query("intAcademic_id") String intAcademic_id
                                    );

    @GET("OnlineClassTimetable")
    Observable<OnlineClassTimetablePojo> getOnlineClassTimetableS(@Query("command") String command,
                                                                  @Query("intAcademic_id") String intAcademic_id,
                                                                  @Query("intSchool_id") String intSchool_id,
                                                                  @Query("intStandard_id") String intStandard_id,
                                                                  @Query("dtLecture_date") String dtLecture_date);



    @GET("OnlineClassTimetable")
    Observable<OnlineClassTimetablePojo> getOnlineClassTimetable(@Query("command") String command,
                                                                 @Query("intTeacher_id") String intTeacher_id,
                                                                 @Query("intAcademic_id") String intAcademic_id,
                                                                 @Query("intSchool_id") String intSchool_id,
                                                                 @Query("dtLecture_date") String dtLecture_date);

    @GET("OnlineClassTimetable")
    Observable<OnlineClassTimetablePojo> getOnlineClassTimetable(@Query("command") String command,
                                                                 @Query("intAcademic_id") String intAcademic_id,
                                                                 @Query("intSchool_id") String intSchool_id,
                                                                 @Query("dtLecture_date") String dtLecture_date);


    @GET("Dashboard")
    Observable<DashboardDetailsPojo> getDashboardDetail(@Query("command") String command,
                                                        @Query("intstanderd_id") String intstanderd_id,
                                                         @Query("intSchool_id") String intSchool_id);

    @GET("Dashboard")
    Observable<DashboardDetailsPojo> getDashboardDetailStudent(@Query("command") String command,
                                                        @Query("intAcademic_id") String intAcademic_id,
                                                        @Query("intstanderd_id") String intstanderd_id,
                                                        @Query("intSchool_id") String intSchool_id);


    @GET("Dashboard")
    Observable<DashboardDetailsPojo> getDashboardDetail(@Query("command") String command,
                                                        @Query("intAcademic_id") String intAcademic_id,
                                                        @Query("intDivision_id") String intDivision_id,
                                                        @Query("intstanderd_id") String intstanderd_id);


    @GET("Dashboard")
    Observable<DashboardDetailsPojo> getDashboardDetails(@Query("command") String command,
                                                         @Query("intAcademic_id") String intAcademic_id);

    //Standard
    @GET("Standard")
        Observable<StandardDetailsPojo> getStandardDetails(@Query("command") String command,
                                                       @Query("intSchool_id") String intSchool_id,
                                                       @Query("intAcademic_id") String intAcademic_id,
                                                       @Query("intStandard_id") String intStandard_id,
                                                       @Query("intDivision_id") String intDivision_id,
                                                       @Query("intTeacher_id") String intTeacher_id,
                                                       @Query("vchType") String vchType);

    //Student StandardWise details
    @GET("StudentStandardWise")
    Observable<StudentStandardwiseDetailPojo> getStudentStandardWiseDetails(@Query("command") String command,
                                                                            @Query("intSchool_id") String intSchool_id,
                                                                            @Query("intAcademic_id") String intAcademic_id,
                                                                            @Query("intStandard_id") String intStandard_id,
                                                                            @Query("intDivision_id") String intDivision_id
    );
    @GET("Attendance")
    Observable<AttendanceDetailPojo> getAttendanceDetails(@Query("command") String command,
                                                          @Query("intschool_id") String intschool_id,
                                                          @Query("intUserType_id") String intUserType_id,
                                                          @Query("intstanderd_id") String intstanderd_id,
                                                          @Query("intdivision_id") String intdivision_id,
                                                          @Query("intAcademic_id") String intAcademic_id,
                                                          @Query("dtDate") String dtDate,
                                                          @Query("status") String status,
                                                          @Query("intUser_id") String intUser_id);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("Attendance")
    Observable<ResponseBody> MarkAttendence(@Query("command") String command,
                                            @Body MarkAttendence attendanceDetail);

    @GET("Attendance")
    Observable<AttendanceDetailPojo> getAttendancedetails(@Query("command") String command,
                                                          @Query("intschool_id") String intschool_id,
                                                          @Query("intAcademic_id") String intAcademic_id,
                                                          @Query("intUser_id") String intUser_id);

    @GET("Profile")
    Observable<ProfileDetailsPojo> getProfiledetails(@Query("command") String command,
                                                     @Query("intschool_id") String intschool_id,
                                                     @Query("intAcademic_id") String intAcademic_id,
                                                     @Query("intUser_id") String intUser_id);

    @GET("TeacherDetail")
    Observable<TeacherDetailPojo> getTeacherDetails(@Query("command") String command,
                                                    @Query("intSchool_id") String intSchool_id);

    @GET("SyllabusDetail")
    Observable<SyllabusDetailPojo> getSyllabusDetails(@Query("command") String command,
                                                      @Query("intSchool_id") String intSchool_id,
                                                      @Query("intSubject_id") String intSubject_id,
                                                      @Query("intSTD_id") String intSTD_id);

    @GET("TimeTable")
    Observable<TimeTableDetailPojo> getTimeTableDetails(@Query("command") String command,
                                                        @Query("intSchool_id") String intSchool_id,
                                                        @Query("Day") String Day,
                                                        @Query("intAcademic_id") String intAcademic_id,
                                                        @Query("intStandard_id") String intStandard_id,
                                                        @Query("intTeacher_id") String intTeacher_id,
                                                        @Query("intDivision_id") String intDivision_id);
    @GET("Library")
    Observable<AssignBookDetailLibPojo> getLibraryAsiignBookDetailsStudent(@Query("command") String command,
                                                                           @Query("intstandard_id") String intstandard_id,
                                                                           @Query("intSchool_id") String intSchool_id,
                                                                           @Query("intStudent_id") String intStudent_id,
                                                                           @Query("dtAssigned_Date") String dtAssigned_Date,
                                                                           @Query("dtReturn_date") String dtReturn_date,
                                                                           @Query("intBookLanguage_id") String intBookLanguage_id);
    @GET("Library")
    Observable<CategoryDetailLibPojo> getLibraryCategoryDetails(@Query("command") String command,
                                                                @Query("intSchool_id") String intSchool_id);

    @GET("Library")
    Observable<BookDetailLibPojo> getLibraryBookDetails(@Query("command") String command,
                                                        @Query("intSchool_id") String intSchool_id,
                                                        @Query("intBookLanguage_id") String intBookLanguage_id,
                                                        @Query("intStandard_id") String intStandard_id,
                                                        @Query("intCategory_id") String intCategory_id);


    @GET("Library")
    Observable<LibraryDetailPojo> getLibraryDetails(@Query("command") String command,
                                                    @Query("intSchool_id") String intSchool_id,
                                                    @Query("intStandard_id") String intStandard_id,
                                                    @Query("intStudent_id") String intStudent_id);
    @GET("Library")
    Observable<SubjectDetailLibPojo> getLibraryDetails(@Query("command") String command,
                                                       @Query("intSchool_id") String intSchool_id,
                                                       @Query("intStandard_id") String intStandard_id);
    @GET("Library")
    Observable<SubjectDetailLibPojo> getLibraryDetailsTeacher(@Query("command") String command,
                                                              @Query("intSchool_id") String intSchool_id);
    @GET("OnlineClassTimetable")
    Observable<DeptDetailPojo> getDepartment(@Query("command") String command,
                                             @Query("intSchool_id") String intSchool_id,
                                             @Query("intusertype_id") String intusertype_id);

    @GET("Library")
    Observable<TeacherLibDetailPojo> getLibraryAsiignBookDetailsTeacher(@Query("command") String command,
                                                                        @Query("intSchool_id") String intSchool_id,
                                                                        @Query("intTeacher_id") String intTeacher_id,
                                                                        @Query("intDepartment_id") String intDepartment_id,
                                                                        @Query("intBookLanguage_id") String intBookLanguage_id,
                                                                        @Query("dtAssigned_Date") String dtAssigned_Date,
                                                                        @Query("dtReturn_date") String dtReturn_date);
    @GET("Library")
    Observable<AssignBookDetailLibPojo> getLibraryAsiignBookDetails(@Query("command") String command,
                                                                    @Query("intSchool_id") String intSchool_id,
                                                                    @Query("intStudent_id") String intStudent_id,
                                                                    @Query("intstandard_id") String intStandard_id,
                                                                    @Query("intBookLanguage_id") String ntBookLanguage_id,
                                                                    @Query("dtAssigned_Date") String dtAssigned_Date,
                                                                    @Query("dtReturn_date") String dtReturn_date);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("Standard")
    Observable<ResponseBody> UpdateDairyHomework(@Query("command") String command,
                                                 @Body StandardDetail standardDetail);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @PUT("Standard")
    Observable<ResponseBody> InsertDairyHomework(@Query("command") String command,
                                                 @Body StandardDetail standardDetail);

    @GET("Leave")
    Observable<LeaveDetailPojo> getLeaveDetailDetails(@Query("command") String command,
                                                      @Query("intAcademic_id") String intAcademic_id,
                                                      @Query("intUserType_id") String intUserType_id,
                                                      @Query("intUser_id") String intUser_id,
                                                      @Query("intSchool_id") String intSchool_id,
                                                      @Query("intTeacher_id") String intTeacher_id);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("Leave")
    Observable<ResponseBody> updateLeaveDetail(@Query("command") String command,
                                               @Body LeaveDetail leaveDetail);
    @POST("FileUpload")
    Observable<ResponseBody> delete(
            @Query("id") String id,
            @Query("command") String command,
            @Query("intSchool_id") String intSchool_id
    );
    @GET("Event")
    Observable<EventDetailPojo> getEventDetails(@Query("command") String command,
                                                @Query("vchStandard_id") String vchStandard_id,
                                                @Query("intAcademic_id") String intAcademic_id,
                                                @Query("intSchool_id") String intSchool_id,
                                                @Query("intUser_id") String intUser_id
    );

    @GET("OnlineClassSchedule")
    Observable<OnlineClassDetailPojo> getOnlineClassDetails(@Query("command") String command,
                                                            @Query("intAcademic_id") String intAcademic_id,
                                                            @Query("intSchool_id") String intSchool_id,
                                                            @Query("intStandard_id") String intStandard_id,
                                                            @Query("dtLecture_date") String dtLecture_date);
    @GET("OnlineClassSchedule")
    Observable<OnlineClassDetailPojo> getOnlineClassDetails(@Query("command") String command,
                                                            @Query("intAcademic_id") String intAcademic_id,
                                                            @Query("intSchool_id") String intSchool_id,
                                                            @Query("dtLecture_date") String dtLecture_date);
    @GET("OnlineClassSchedule")
    Observable<OnlineClassDetailPojo> getOnlineClassDetailsT(@Query("command") String command,
                                                             @Query("intAcademic_id") String intAcademic_id,
                                                             @Query("intSchool_id") String intSchool_id,
                                                             @Query("intTeacher_id") String intTeacher_id,
                                                             @Query("dtLecture_date") String dtLecture_date);
															 
    @GET("OnlineClassTimetable")
    Observable<OnlineClassDetailsPojo> getOnlineTTdetailById(@Query("command") String command,
                                                             @Query("intAcademic_id") String intAcademic_id,
                                                             @Query("intSchool_id") String intSchool_id,
                                                             @Query("intOnlinelecture_id") String intOnlinelecture_id);



    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("Event")
    Observable<ResponseBody> InsertEvent(@Query("command") String command,
                                         @Body EventDetail eventDetail);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("Noticeboard")
    Observable<ResponseBody> InsertNotice(@Query("command") String command,
                                          @Body NoticeboardDetail noticeboardDetail);

    @Multipart
    @POST("FileUpload")
    Observable<ResponseBody> upload(
            @Part MultipartBody.Part file,
            @Query("extension") String extension,
            @Query("EventDescription") String EventDescription
    );

    @Multipart
    @POST("FileUpload")
    Observable<ResponseBody> upload(
            @Part MultipartBody.Part file,
            @Query("extension") String extension
    );
    @Multipart
    @POST("Gallery/Gallery")
    Observable<ResponseBody> uploadNotice(
            @Part MultipartBody.Part file,
            @Query("intUserType_id")String intUserType_id,
            @Query("intStandard_id")String intStandard_id,
            @Query("intDepartment_id")String intDepartment_id,
            @Query("intTeacher_id")String intTeacher_id,
            @Query("dtIssue_date")String dtIssue_date,
            @Query("dtEnd_date")String dtEnd_date,
            @Query("vchSubject")String vchSubject,
            @Query("vchNotice")String vchNotice,
            @Query("intInserted_by")String intInserted_by,
            @Query("InsertIP")String InsertIP,
            @Query("intSchool_id")String intSchool_id);

    @Multipart
    @POST("Profile")
    Observable<ResponseBody> uploadProfile(
            @Part MultipartBody.Part file,
            @Query("vchProfile") String vchProfile,
            @Query("command") String command,
            @Query("intschool_id") String intschool_id,
            @Query("intAcademic_id") String intAcademic_id,
            @Query("intUser_id") String intUser_id
    );

    @GET("Chat")
    Observable<ChatDetailsPojo> getChatUserDetails(@Query("command") String command,
                                                   @Query("intSchool_id") String intSchool_id,
                                                   @Query("intUserid") String intUserid,
                                                   @Query("intStandard_id") String intStandard_id,
                                                   @Query("intDivision_id") String intDivision_id,
                                                   @Query("intAcademic_id") String intAcademic_id);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("Chat")
    Observable<ResponseBody> SendChatMessage(@Query("command") String command,
                                             @Body ChatDetail chatDetail);

    @POST("Login")
    Observable<ResponseBody> FCMTokenUpdate(@Query("command") String command,
                                            @Body LoginDetail loginDetail);

}
