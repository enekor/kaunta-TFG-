import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:kaunta/home/globales.dart';
import 'package:kaunta/model/crear_contador.dart';
import 'package:kaunta/model/crear_grupo.dart';
import 'package:kaunta/model/edit_contador.dart';
import 'package:kaunta/model/modelo.dart';
import 'package:kaunta/model/register.dart';
import 'package:kaunta/paginas/listado/listado.dart';
import 'package:kaunta/utils/shared_preferences.dart';

class ApiCall {
  int codigo = 0;

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
    var ans = await http.get(Uri.parse("${Globales().apiUrl}/test"));

    return ans.statusCode;
  }

  Future<int> login() async {
    var ans = await http.get(Uri.parse(
        "${Globales().apiUrl}/login?username=$usuarioLogin&password=$passLogin"));

    if (ans.statusCode == 200) {
      SharedPreferencesEditor()
          .postSharedPreferences("token", ans.body, "String");
      Globales().conectado = true;
    }

    return ans.statusCode;
  }

  Future<int> register() async {
    Register reg = Register(user: usuarioReg, password: passReg);
    String body = jsonEncode(reg);

    var ans = await http.post(
      Uri.parse("${Globales().apiUrl}/register"),
      body: body,
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8'
      },
    );

    if (ans.statusCode == 202) {
      SharedPreferencesEditor()
          .postSharedPreferences("token", ans.body, "String");

      Globales().conectado = true;
    }

    return ans.statusCode;
  }

  Future<String> me() async {
    String ret = "";
    String token =
        await SharedPreferencesEditor().getSharedPreferences("token", "String");
    var ans =
        await http.get(Uri.parse("${Globales().apiUrl}/user/me?token=$token"));

    if (ans.statusCode == 200) {
      ret = ans.body;
    }

    return ret;
  }

  //grupos
  Future<String> getGrupos(bool activo) async {
    String ret = "";
    String token = await SharedPreferencesEditor()
        .getSharedPreferences("token", "String") as String;
    var ans = await http
        .get(Uri.parse("${Globales().apiUrl}/group/all/$activo?token=$token"));

    if (ans.statusCode == 200) {
      ret = ans.body;
    }
    return ret;
  }

  Future<int> createGroup(CrearGrupo grupo) async {
    String token =
        await SharedPreferencesEditor().getSharedPreferences("token", "String");

    var ans = await http.post(
      Uri.parse("${Globales().apiUrl}/group/save?token=$token"),
      body: jsonEncode(grupo),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
    );

    return ans.statusCode;
  }

  Future<int> deleteGroup(int id) async {
    String token = await SharedPreferencesEditor()
        .getSharedPreferences("token", "String") as String;

    var ans = await http.delete(
        Uri.parse("${Globales().apiUrl}/group/delete/$id?token=$token"));

    return ans.statusCode;
  }

  Future<int> restoreGroup(int id) async {
    String token = await SharedPreferencesEditor()
        .getSharedPreferences("token", "String") as String;

    var ans = await http
        .post(Uri.parse("${Globales().apiUrl}/group/restore/$id?token=$token"));

    return ans.statusCode;
  }

  //contadores
  Future<int> saveCounter(EditContador contador) async {
    String token = await SharedPreferencesEditor()
        .getSharedPreferences("token", "String") as String;

    var ans = await http.put(
      Uri.parse("${Globales().apiUrl}/counter/edit?token=$token"),
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
        Uri.parse("${Globales().apiUrl}/counter/add?token=$token"),
        body: jsonEncode(contador),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
        });

    return ans.statusCode;
  }

  Future<int> getContadores(bool activo) async {
    String token = await SharedPreferencesEditor()
        .getSharedPreferences("token", "String") as String;
    int grupo = Listado().usuario.value.id!;

    var ans = await http.get(Uri.parse(
        "${Globales().apiUrl}/counter/all/$activo?token=$token&group=$grupo"));

    if (ans.statusCode == 200) {
      List<Contador> contadores = contadoresFromJson(jsonDecode(ans.body));
      Listado().usuario.value.grupos![Listado().gActual].counters!.value =
          contadores;
    }

    return ans.statusCode;
  }

  List<Contador> contadoresFromJson(Map<String, dynamic> json) {
    List<Contador> ret = <Contador>[];

    if (json.isNotEmpty && json["contadores"] != null) {
      json["contadores"].forEach((v) {
        ret.add(Contador.fromJson(v));
      });
    }

    return ret;
  }

  Future<int> deleteContador(int id) async {
    String token = await SharedPreferencesEditor()
        .getSharedPreferences("token", "String") as String;

    var ans = await http.delete(
        Uri.parse("${Globales().apiUrl}/counter/delete/$id?token=$token"));

    return ans.statusCode;
  }

  Future<int> restoreContador(int id) async {
    String token = await SharedPreferencesEditor()
        .getSharedPreferences("token", "String") as String;

    var ans = await http.post(
        Uri.parse("${Globales().apiUrl}/counter/restore/$id?token=$token"));

    return ans.statusCode;
  }
}
