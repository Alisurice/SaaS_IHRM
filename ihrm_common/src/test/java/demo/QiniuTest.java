package demo;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;


public class QiniuTest {

    public static void main(String[] args) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "GgXeIrzbbpKCRqwNCXrO-o3RsPZQBrXLKB_QK181";
        String secretKey = "0d61sPB8tXqogADRFkIKrwnvWu4aUr68OEPATTES";
        String bucket = "alisurice";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "C:\\Users\\93240\\IdeaProjects\\My_IHRM\\ihrm\\ihrm_parent\\ihrm_common\\src\\main\\resources\\0076LQCyly1gt2gystu0lj30u0190n23.jpg";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "test";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket,key);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }
}
