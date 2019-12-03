package com.techno.takhdim.NewDesign.Constant;

public class BaseClass {
    public String BaseUrl="https://www.takhdim-admin.com/index.php/webservice/";
    public static BaseClass get() {
        return new BaseClass();
    }
    public String ServiceRequest(){
        return BaseUrl.concat("request_nearby_provider");
    }
    public String UploadServiceImage(){
        return BaseUrl.concat("upload_service_image");
    }
}
