import 'package:get/get.dart';
import 'package:http/http.dart' as http;

class ApiCall {
  int codigo = 0;

  static final ApiCall _apiInstace = ApiCall._internal();

  factory ApiCall() {
    return _apiInstace;
  }
  ApiCall._internal();

  Future<int> testConnection() async {
    Uri url = Uri.parse("https://restcountries.com/v3.1/al");

    var ans = await http.get(url);

    int ret = ans.statusCode;

    codigo = ret;

    return ret;
  }
}
