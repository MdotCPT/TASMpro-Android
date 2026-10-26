package uk.co.tasmpro.personal;

import android.app.Activity;
import android.app.DownloadManager;
import android.print.PrintManager;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PageRange;
import android.os.ParcelFileDescriptor;
import android.os.CancellationSignal;
import java.io.FileOutputStream;
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
import android.widget.FrameLayout;
import android.view.ViewGroup;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.OutputStream;

public class MainActivity extends Activity {
  private WebView web;
  private FrameLayout webRoot;
  private ValueCallback<Uri[]> fileCallback;
  private Uri cameraUri;
  private WebView activePrintWebView;
  private static final int PICK=101;
  @Override public void onCreate(Bundle saved){
    super.onCreate(saved);
    webRoot=new FrameLayout(this);web=new WebView(this);webRoot.addView(web,new FrameLayout.LayoutParams(-1,-1));setContentView(webRoot);
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
          if(params.isCaptureEnabled()){
            File dir=new File(getCacheDir(),"camera");dir.mkdirs();
            File f=File.createTempFile("photo_",".jpg",dir);
            cameraUri=FileProvider.getUriForFile(MainActivity.this,getPackageName()+".fileprovider",f);
            Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            camera.putExtra(MediaStore.EXTRA_OUTPUT,cameraUri);
            camera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivityForResult(camera,PICK);
          }else{
            cameraUri=null;
            Intent gallery=new Intent(Intent.ACTION_OPEN_DOCUMENT);
            gallery.addCategory(Intent.CATEGORY_OPENABLE);
            gallery.setType("image/*");
            gallery.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(gallery,PICK);
          }
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
    @JavascriptInterface public void printHtml(String html,boolean requisition){
      runOnUiThread(()->{
        try{
          if(html==null||html.trim().isEmpty()){toast("No report content to print");return;}
          toast("Preparing Android print preview…");
          String css="@page{size:A4;margin:14mm}*{box-sizing:border-box}html,body{background:#fff!important;color:#111!important;font:12px Arial,sans-serif;margin:0;padding:0}body *{text-shadow:none!important;box-shadow:none!important}h1{font-size:23px;color:#152f47!important}h2{font-size:17px;color:#152f47!important}p,small,span,div,strong,td,th{color:#111!important}table{border-collapse:collapse;width:100%}td,th{border-bottom:1px solid #bbb;padding:7px;text-align:left}.print-project{break-before:page}.print-project:first-of-type{break-before:auto}.print-item{display:flex;gap:12px;padding:12px 0;border-bottom:1px solid #ddd;break-inside:avoid}.print-item .description{flex:1}.print-item img{width:38%;max-height:95mm;object-fit:contain}.print-item.photo-small img{width:23%}.print-item.photo-large img{width:57%}.print-head{display:flex;justify-content:space-between}.print-logo{max-width:55mm;max-height:22mm}.meta{border-bottom:2px solid #183e60;padding-bottom:10px}.report-cover{display:flex!important;position:relative;width:182mm;min-height:265mm;page-break-after:always;break-after:page;overflow:hidden;background:#fff!important;color:#142b40!important;padding:20mm 14mm;align-items:flex-end}.report-cover:before{content:\"\";position:absolute;inset:0;background-image:var(--cover-image);background-size:contain;background-position:center 18mm;background-repeat:no-repeat;opacity:.95}.report-cover .cover-content{position:relative;z-index:1;background:#ffffffee;padding:9mm;width:100%}.report-cover .cover-content h1{font-size:30px!important}.report-cover .cover-logo{max-width:65mm;max-height:24mm;object-fit:contain}.report-cover .cover-kicker{font-size:11px;letter-spacing:1.5px;color:#546879!important}.report-cover .cover-subtitle,.report-cover .cover-company,.report-cover .cover-meta{color:#142b40!important}.print-project{break-before:auto!important}.print-item{break-inside:avoid;page-break-inside:avoid}.tick{display:inline-block;width:14px;height:14px;border:1px solid #111;margin-right:6px}footer{margin-top:22px}img{max-width:100%}";
          String doc="<!doctype html><html><head><meta name='viewport' content='width=device-width,initial-scale=1'><style>"+css+"</style></head><body>"+html+"</body></html>";
          WebView printable=new WebView(MainActivity.this);
          if(activePrintWebView!=null){webRoot.removeView(activePrintWebView);activePrintWebView.destroy();}
          activePrintWebView=printable;
          FrameLayout.LayoutParams printParams=new FrameLayout.LayoutParams(1,1);
          webRoot.addView(printable,0,printParams);
          printable.getSettings().setJavaScriptEnabled(false);
          printable.setWebViewClient(new WebViewClient(){@Override public void onPageFinished(WebView v,String url){
            v.postDelayed(()->{try{PrintManager pm=(PrintManager)getSystemService(Context.PRINT_SERVICE);pm.print(requisition?"TASMpro requisition":"TASMpro snag report",v.createPrintDocumentAdapter("TASMpro"),new PrintAttributes.Builder().setMediaSize(PrintAttributes.MediaSize.ISO_A4).setMinMargins(PrintAttributes.Margins.NO_MARGINS).build());}catch(Exception ex){toast("Print failed: "+ex.getMessage());}},650);
          }});
          printable.loadDataWithBaseURL("https://appassets.androidplatform.net/",doc,"text/html","UTF-8",null);
        }catch(Exception e){toast("Could not prepare report: "+e.getMessage());}
      });
    }
    @JavascriptInterface public void printPage(){runOnUiThread(()->web.evaluateJavascript("(function(){var r=document.body.classList.contains('req-unified-print');var e=document.getElementById(r?'reqUnifiedPrint':'printSheet');if(e&&e.innerHTML)TASMAndroid.printHtml(e.innerHTML,r);else alert('Report is empty');})()",null));}
    @JavascriptInterface public void emailPdf(String filename,String base64,String recipient,String subject,String body){
      new Thread(()->{try{
        byte[] bytes=Base64.decode(base64,Base64.DEFAULT);
        if(bytes.length<8||bytes[0]!=37||bytes[1]!=80||bytes[2]!=68||bytes[3]!=70)throw new Exception("Generated PDF is invalid");
        File folder=new File(getCacheDir(),"reports");if(!folder.exists()&&!folder.mkdirs())throw new Exception("Cannot create report directory");
        String safe=filename.replaceAll("[^a-zA-Z0-9._-]","_");if(!safe.endsWith(".pdf"))safe+=".pdf";
        File output=new File(folder,safe);
        try(FileOutputStream stream=new FileOutputStream(output)){stream.write(bytes);}
        Uri attachment=FileProvider.getUriForFile(MainActivity.this,getPackageName()+".fileprovider",output);
        runOnUiThread(()->{try{
          Intent email=new Intent(Intent.ACTION_SEND);
          email.setType("application/pdf");
          if(recipient!=null&&!recipient.trim().isEmpty())email.putExtra(Intent.EXTRA_EMAIL,new String[]{recipient});
          email.putExtra(Intent.EXTRA_SUBJECT,subject);email.putExtra(Intent.EXTRA_TEXT,body);
          email.putExtra(Intent.EXTRA_STREAM,attachment);
          email.setClipData(ClipData.newUri(getContentResolver(),"TASMpro report",attachment));
          email.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
          startActivity(Intent.createChooser(email,"Email TASMpro report"));
        }catch(Exception ex){toast("Cannot open email app: "+ex.getMessage());}});
      }catch(Exception ex){toast("PDF email failed: "+ex.getMessage());}}).start();
    }
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
