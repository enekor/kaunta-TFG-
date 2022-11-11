import 'dart:math';

import 'package:get/get.dart';
import 'package:kaunta/model/modelo.dart';

class Listado {
  static final Listado _listadoInstance = Listado._internal();

  factory Listado() {
    return _listadoInstance;
  }
  Listado._internal();

  RxBool leido = false.obs;

  Rx<User> usuario = User(
    id: Random().nextInt(200),
    grupos: <Grupo>[].obs,
    name: "Nombre".obs,
  ).obs;
  int gActual = -1;
  Contador cActual = Contador();
}
