import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:kaunta/model/crear_contador.dart';
import 'package:kaunta/model/crear_grupo.dart';
import 'package:kaunta/model/edit_contador.dart';
import 'package:kaunta/model/register.dart';
import 'package:kaunta/utils/shared_preferences.dart';

class ApiCall {
  int codigo = 0;
  String apiUrl = "http://192.168.1.147:7777";
  bool tokenValido = false;
  bool conectado = false;

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
      conectado = true;
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

      conectado = true;
    }

    return ans.statusCode;
  }

  Future<String> me() async {
    String ret = "";
    String token =
        await SharedPreferencesEditor().getSharedPreferences("token", "String");
    var ans = await http.get(Uri.parse("$apiUrl/user/me?token=$token"));

    if (ans.statusCode == 20) {
      ret = ans.body;
    }

    return ret;
  }

  //grupos
  Future<String> getGrupos(bool activo) async {
    String ret = "";
    String token = await SharedPreferencesEditor()
        .getSharedPreferences("token", "String") as String;
    var ans =
        await http.get(Uri.parse("$apiUrl/group/all/$activo?token=$token"));

    if (ans.statusCode == 200) {
      ret = ans.body;
    }
    return ret;
  }

  Future<int> createGroup(CrearGrupo grupo) async {
    String token =
        await SharedPreferencesEditor().getSharedPreferences("token", "String");

    var ans = await http.post(
      Uri.parse("$apiUrl/group/save?token=$token"),
      body: jsonEncode(grupo),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
    );

    return ans.statusCode;
  }

  Future<int> deleteGroup(int id) async {
    String token = SharedPreferencesEditor()
        .getSharedPreferences("token", "String") as String;

    var ans =
        await http.delete(Uri.parse("$apiUrl/group/delete/$id?token=$token"));

    return ans.statusCode;
  }

  Future<int> restoreGroup(int id) async {
    String token = SharedPreferencesEditor()
        .getSharedPreferences("token", "String") as String;

    var ans =
        await http.post(Uri.parse("$apiUrl/group/restore/$id?token=$token"));

    return ans.statusCode;
  }

  //contadores
  Future<int> saveCounter(EditContador contador) async {
    String token = await SharedPreferencesEditor()
        .getSharedPreferences("token", "String") as String;

    var ans = await http.put(
      Uri.parse("$apiUrl/counter/edit?token=$token"),
      body: jsonEncode(contador),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
    );

    return ans.statusCode;
  }

  Future<int> createContador(CrearContador contador) async {
    String token =
        await SharedPreferencesEditor().getSharedPreferences("token", "String");

    var ans = await http.post(
      Uri.parse("$apiUrl/group/save?token=$token"),
    );

    return ans.statusCode;
  }
}
