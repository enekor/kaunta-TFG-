import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:kaunta/model/modelo.dart';
import 'package:kaunta/model/register.dart';
import 'package:kaunta/paginas/listado/listado.dart';
import 'package:kaunta/utils/shared_preferences.dart';

class ApiCall {
  int codigo = 0;
  String apiUrl = "http://192.168.235.78:7777";
  bool tokenValido = false;

  static final ApiCall _apiInstace = ApiCall._internal();

  factory ApiCall() {
    return _apiInstace;
  }
  ApiCall._internal();

  Future<int> testConnection() async {
    Uri url = Uri.parse("$apiUrl/test");

    var ans = await http.get(url);

    codigo = ans.statusCode;

    return codigo;
  }

  Future<void> login(String user, String password) async {
    var url = Uri.parse("$apiUrl/login?username=$user&password=$password");

    var ans = await http.get(url);

    codigo = ans.statusCode;

    if (codigo == 200) {
      SharedPreferencesEditor()
          .postSharedPreferences("token", ans.body, "String");
    }
  }

  Future<void> register(String usuario, String password) async {
    var url = Uri.parse("$apiUrl/register");

    Register reg = Register(user: usuario, password: password);

    var ans = await http.post(
      url,
      body: jsonEncode(reg),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
    );

    codigo = ans.statusCode;

    if (codigo == 200) {
      SharedPreferencesEditor()
          .postSharedPreferences("token", ans.body, "String");
    }
  }

  Future<bool> me() async {
    bool ret;
    try {
      String token = await SharedPreferencesEditor()
          .getSharedPreferences("token", "String");

      var url = Uri.parse("$apiUrl/user/me?token=$token");

      var ans = await http.get(url);

      codigo = ans.statusCode;

      Listado().usuario = User.fromJson(jsonDecode(ans.body));

      ret = codigo == 200;
    } catch (e) {
      ret = false;
    }

    return ret;
  }
}
