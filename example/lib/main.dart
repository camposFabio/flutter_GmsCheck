import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:gmscheck/gmscheck.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  bool _gmsCheck = false;
  bool _hmsCheck = false;
  String _store = 'UNKNOW';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    bool gmsCheck;
    bool hmsCheck;
    String store;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      gmsCheck = await Gmscheck.checkGms();
    } on PlatformException {
      gmsCheck = false;
    }
    try {
      hmsCheck = await Gmscheck.checkHms();
    } on PlatformException {
      hmsCheck = false;
    }
    try {
      store = await Gmscheck.getStoreName();
    } on PlatformException {
      store = 'UNKNOW';
    }
    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _gmsCheck = gmsCheck;
      _hmsCheck = hmsCheck;
      _store = store;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('Store: $_store\nGMS: $_gmsCheck\nHMS: $_hmsCheck\n'),
        ),
      ),
    );
  }
}
