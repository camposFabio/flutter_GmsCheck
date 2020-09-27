import 'dart:async';

import 'package:flutter/services.dart';

class Gmscheck {
  static const MethodChannel _channel =
      const MethodChannel('app.jffc/gmscheck');

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

  static Future<String> getStoreName() async {
    String result = await _channel.invokeMethod(
      'getStoreName',
    );
    return result;
  }
}
