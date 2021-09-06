package info.efficacious.centralmodelschool.webApi;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit;
    //XCELL Links
//    http://192.168.1.150/EfficaciousDemoAPI/
//    http://192.168.1.150/EfficaciousDemoAPI/image/06_05_2019_13_21_13_56.jpg

    // prod
    //private static final String  BASE_URL = "http://e-smarts.net/CMSAPI/api/";
    //public static final String   Image_URL = "http://e-smarts.net/CentralModel/CentralModelServices/UploadImages/";
    //UAT/prod REAL LINK
    public static final String   Image_error_URL = "http://e-smarts.net/CMSAPI/image/";

    //public static final String Image_error_URL = "http://122.170.4.112/CMSWebApi/image/";

    //    UAT Links CMSWebApi
    private static final String BASE_URL = "http://e-smarts.co.in/CMSWEBAPIUAT/api/";
    public static final String Image_URL = "http://e-smarts.co.in/CMSWEBAPIUAT/image/";
    public static final String resultUrl="http://www.e-smarts.co.in/CMSResultUAT/result.aspx?";
    public static final String readmisiionUrl="http://e-smarts.co.in/cmsuat/frmReAdmissionForm.aspx?";

    //PRODUCTION LINK
//    private static final String  BASE_URL = "http://e-smarts.net/CMSAPI/api/";
//    public static final String   Image_URL = "http://e-smarts.net/CentralModel/CentralModelServices/UploadImages/";
//    public static final String resultUrl="http://e-smarts.net/CMSResult/Result.aspx?";
//    public static final String readmisiionUrl="http://e-smarts.net/centralmodel/frmReAdmissionForm.aspx?";


    //public static final String Image_URL = "http://e-smarts.net/CMSAPI/image/";
    //    UAT Links CMSWebApi

    //    UAT Links CMSWebApi
    //public static final String resultUrl="http://e-smarts.net/CMSResult/Result.aspx?";


    // UAT Links CMSWebApi for Re-addmission

    // Production Links For Re-admission
    //public static final String readmisiionUrl="http://e-smarts.net/centralmodel/frmReAdmissionForm.aspx?";

    //private static final String BASE_URL = "http://e-smarts.com/CMSAPI/api/";
//    http://122.170.4.112/gurukulartidemo/
//    http://e-smarts.com/CMSAPI/api/z
//
//    public static final String Image_URL = " http://e-smarts.net/CMSAPI/image/";

//    http://e-smarts.net/CMSAPI/image/04_09_2019_19_25_08_91.jpg

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getUnsafeOkHttpClient())
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}