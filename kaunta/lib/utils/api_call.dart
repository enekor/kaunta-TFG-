import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:kaunta/model/register.dart';
import 'package:kaunta/utils/shared_preferences.dart';

class ApiCall {
  int codigo = 0;
  String apiUrl = "http://192.168.1.147:7777";
  bool tokenValido = false;

  String usuarioLogin = "";
  String passLogin = "";
  String passReg = "";
  String usuarioReg = "";

  static final ApiCall _apiInstace = ApiCall._internal();

  factory ApiCall() {
    return _apiInstace;
  }
  ApiCall._internal();

  Future<int> testConnection() async {
    var ans = await http.get(Uri.parse("$apiUrl/test"));

    return ans.statusCode;
  }

  Future<int> login() async {
    var ans = await http.get(
        Uri.parse("$apiUrl/login?username=$usuarioLogin&password=$passLogin"));

    if (ans.statusCode == 200) {
      SharedPreferencesEditor()
          .postSharedPreferences("token", ans.body, "String");
    }

    return ans.statusCode;
  }

  Future<int> register() async {
    Register reg = Register(user: usuarioReg, password: passReg);

    var ans = await http.post(
      Uri.parse("$apiUrl/register"),
      body: jsonEncode(reg),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
    );

    if (ans.statusCode == 200) {
      SharedPreferencesEditor()
          .postSharedPreferences("token", ans.body, "String");
    }

    return ans.statusCode;
  }
}
