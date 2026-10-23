package uk.co.tasmpro.personal;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.PrintManager;
import android.content.Context;
import android.content.Intent;
import android.content.ClipData;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Base64;
import android.webkit.*;
import android.widget.Toast;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.OutputStream;

public class MainActivity extends Activity {
  private WebView web;
  private ValueCallback<Uri[]> fileCallback;
  private Uri cameraUri;
  private static final int PICK=101;
  @Override public void onCreate(Bundle saved){
    super.onCreate(saved);
    web=new WebView(this);setContentView(web);
    WebSettings s=web.getSettings();s.setJavaScriptEnabled(true);s.setDomStorageEnabled(true);
    s.setAllowFileAccess(true);s.setAllowContentAccess(true);
    s.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
    web.addJavascriptInterface(new Bridge(),"TASMAndroid");
    web.setWebViewClient(new WebViewClient(){
      @Override public boolean shouldOverrideUrlLoading(WebView view,WebResourceRequest req){
        Uri uri=req.getUrl();String scheme=uri.getScheme();
        if("file".equals(scheme)||"about".equals(scheme)||"blob".equals(scheme))return false;
        try{startActivity(new Intent("mailto".equals(scheme)?Intent.ACTION_SENDTO:Intent.ACTION_VIEW,uri));}
        catch(Exception e){toast("No app available to open this link");}return true;
      }
    });
    web.setWebChromeClient(new WebChromeClient(){
      @Override public boolean onShowFileChooser(WebView v,ValueCallback<Uri[]> callback,FileChooserParams params){
        if(fileCallback!=null)fileCallback.onReceiveValue(null);fileCallback=callback;
        try{
          Intent gallery=new Intent(Intent.ACTION_GET_CONTENT);gallery.setType("*/*");gallery.addCategory(Intent.CATEGORY_OPENABLE);
          gallery.putExtra(Intent.EXTRA_MIME_TYPES,new String[]{"image/*","application/json"});
          File dir=new File(getCacheDir(),"camera");dir.mkdirs();
          File f=File.createTempFile("photo_",".jpg",dir);
          cameraUri=FileProvider.getUriForFile(MainActivity.this,getPackageName()+".fileprovider",f);
          Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);camera.putExtra(MediaStore.EXTRA_OUTPUT,cameraUri);
          camera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
          if(params.isCaptureEnabled())startActivityForResult(camera,PICK);
          else{Intent chooser=Intent.createChooser(gallery,"Choose photo or file");chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS,new Intent[]{camera});startActivityForResult(chooser,PICK);}
        }catch(Exception e){fileCallback.onReceiveValue(null);fileCallback=null;toast("Unable to open camera or files");}
        return true;
      }
    });
    web.setDownloadListener((url,userAgent,contentDisposition,mimeType,contentLength)->{
      if(!url.startsWith("https://"))return;
      try{DownloadManager.Request r=new DownloadManager.Request(Uri.parse(url));r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"TASMpro-download");((DownloadManager)getSystemService(DOWNLOAD_SERVICE)).enqueue(r);
      }catch(Exception e){toast("Download failed");}
    });
    web.loadUrl("file:///android_asset/index.html");
  }
  private void toast(String s){runOnUiThread(()->Toast.makeText(this,s,Toast.LENGTH_LONG).show());}
  @Override protected void onActivityResult(int req,int result,Intent data){
    super.onActivityResult(req,result,data);if(req!=PICK||fileCallback==null)return;
    Uri[] resultUris=null;
    if(result==RESULT_OK){if(data!=null&&data.getClipData()!=null){ClipData clip=data.getClipData();resultUris=new Uri[clip.getItemCount()];for(int i=0;i<clip.getItemCount();i++)resultUris[i]=clip.getItemAt(i).getUri();}
      else if(data!=null&&data.getData()!=null)resultUris=new Uri[]{data.getData()};else if(cameraUri!=null)resultUris=new Uri[]{cameraUri};}
    fileCallback.onReceiveValue(resultUris);fileCallback=null;cameraUri=null;
  }
  class Bridge{
    @JavascriptInterface public void printPage(){runOnUiThread(()->{PrintManager pm=(PrintManager)getSystemService(Context.PRINT_SERVICE);pm.print("TASMpro report",web.createPrintDocumentAdapter("TASMpro"),null);});}
    @JavascriptInterface public void saveBase64(String filename,String mime,String base64){
      new Thread(()->{try{
        String safe=filename.replaceAll("[^a-zA-Z0-9._-]","_");if(safe.isEmpty())safe="TASMpro-file.pdf";
        byte[] bytes=Base64.decode(base64,Base64.DEFAULT);
        ContentValues v=new ContentValues();v.put(MediaStore.MediaColumns.DISPLAY_NAME,safe);v.put(MediaStore.MediaColumns.MIME_TYPE,mime);v.put(MediaStore.MediaColumns.RELATIVE_PATH,Environment.DIRECTORY_DOWNLOADS+"/TASMpro");
        Uri uri=getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI,v);if(uri==null)throw new Exception("Cannot create file");
        try(OutputStream os=getContentResolver().openOutputStream(uri)){if(os==null)throw new Exception("Cannot open file");os.write(bytes);}
        toast("Saved to Downloads/TASMpro: "+safe);
      }catch(Exception e){toast("Could not save file: "+e.getMessage());}}).start();
    }
  }
}
