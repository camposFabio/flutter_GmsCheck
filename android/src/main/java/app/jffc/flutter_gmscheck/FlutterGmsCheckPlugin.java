package app.jffc.flutter_gmscheck;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.content.pm.PackageInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;


import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;


import com.google.android.gms.common.GoogleApiAvailability;

import com.huawei.hms.api.HuaweiApiAvailability;



public class FlutterGmsCheckPlugin implements FlutterPlugin, MethodCallHandler,ActivityAware {

  private MethodChannel channel;
  private Context context;
  private Activity activity;



  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(),
        "app.jffc/flutter_gmscheck");
    context = flutterPluginBinding.getApplicationContext();
    channel.setMethodCallHandler(this);
  }

  
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel =
        new MethodChannel(registrar.messenger(), "app.jffc/flutter_gmscheck");

    channel.setMethodCallHandler(new FlutterGmsCheckPlugin());
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {

    switch (call.method) {
      case "checkHms":
        result.success(checkHms());
        break;
      case "checkGms":
        result.success(checkHms());
        break;
      case "getsStoreName":
        result.success(getStoreName());
        break;
      
      default:
        result.notImplemented();
    }

  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

   // Activity
   @Override
   public void onAttachedToActivity(ActivityPluginBinding binding) {
  
     activity = binding.getActivity();
   }
 
   @Override
   public void onDetachedFromActivityForConfigChanges() {
    
   }
 
   @Override
   public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {
     
   }
 
   @Override
   public void onDetachedFromActivity() {
     
   }
 

  protected String getStoreName() {
    String storeName = "UNKNOW";

    final PackageManager pm =  activity.getPackageManager(); // context.getPackageManager(); 
    
//    InstallSourceInfo  installSourceInfo = pm.getInstallSourceInfo(context.getPackageName());

    final String installer = pm.getInstallerPackageName(context.getPackageName());
    
    if (installer == null || installer.trim().isEmpty()) {
      List<PackageInfo> installedPackages = pm.getInstalledPackages(1); //PackageManager.GET_ACTIVITIES
      for (PackageInfo p : installedPackages) {
          if (p.packageName.contains("SAMSUNG")) {
              storeName = "SAMSUNG";
              break;
          }
      }
    } else if (installer.contains("VENDING")) {
      storeName = "GOOGLE";        
    } else if (installer.contains("AMAZON")) {
      storeName = "AMAZON";        
    } else if (installer.contains("SAMSUNG")) {
      storeName = "SAMSUNG";
    } else if (installer.contains("HUAWEI")) {
      storeName = "HUAWEI";
    } else if (installer.contains("XIAOMI")) {
      storeName = "XIAOMI";
    
    }

    return storeName;
  }


  protected  Boolean checkHms() {
    Boolean isAvailable = false;

    if (context != null) {
        int result = HuaweiApiAvailability.getInstance().isHuaweiMobileServicesAvailable(context);
        isAvailable = (result == 0);
    }

    return isAvailable;
  }

  protected Boolean checkGms() {
    Boolean isAvailable = false;  
    if (context != null) {
        int result = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        isAvailable = (result == 0);
    }
    return isAvailable;
  }
}




  


