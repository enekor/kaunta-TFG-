import 'package:shared_preferences/shared_preferences.dart';

class SharedPreferencesEditor {
  static final SharedPreferencesEditor _spInstace =
      SharedPreferencesEditor._internal();

  factory SharedPreferencesEditor() {
    return _spInstace;
  }
  SharedPreferencesEditor._internal();

  Future<void> postSharedPreferences(
      String clave, String valor, String tipo) async {
    final prefs = await SharedPreferences.getInstance();

    if (tipo == "String") {
      prefs.setString(clave, valor);
    } else if (tipo == "int") {
      prefs.setInt(clave, int.parse(valor));
    }
  }

  Future<dynamic> getSharedPreferences(String clave, String tipo) async {
    final prefs = await SharedPreferences.getInstance();
    dynamic ret;

    if (tipo == "String") {
      ret = prefs.getString(clave);
    } else if (tipo == "int") {
      ret = prefs.getInt(clave);
    }

    return ret;
  }
}
