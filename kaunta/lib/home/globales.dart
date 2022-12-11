import 'package:get/get.dart';

class Globales {
  static final Globales _globalesInstance = Globales._internal();

  factory Globales() {
    return _globalesInstance;
  }
  Globales._internal();

  RxInt pagina = 0.obs;
  String apiUrl = "http://192.168.1.145:7777";
  bool tokenValido = false;
  bool conectado = false;
  RxBool verGrupos = true.obs;
  int grupo = -1;
}
