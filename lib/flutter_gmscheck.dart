import 'dart:async';

import 'package:flutter/services.dart';

class FlutterGmscheck {
  static const MethodChannel _channel =
      const MethodChannel('app.jffc/flutter_gmscheck');

  static Future<bool> checkHms() async {
    bool result = await _channel.invokeMethod(
      'checkHms',
    );
    return result;
  }

  static Future<bool> checkGms() async {
    bool result = await _channel.invokeMethod(
      'checkGms',
    );
    return result;
  }

  static Future<bool> getsStoreName() async {
    bool result = await _channel.invokeMethod(
      'getsStoreName',
    );
    return result;
  }
}
